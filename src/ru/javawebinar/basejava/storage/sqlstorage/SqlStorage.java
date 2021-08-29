package ru.javawebinar.basejava.storage.sqlstorage;

import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactName;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionName;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.executeAndGet("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        LOG.info("Update " + uuid);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid, "Resume does not exist in the Storage");
                }
            }
            deleteAttributes(uuid, "DELETE FROM contact " +
                    "WHERE resume_uuid = ?", conn);
            deleteAttributes(uuid, "DELETE FROM section " +
                    "WHERE resume_uuid = ?", conn);
            insertContacts(r, conn);
            insertSections(r, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        LOG.info("Save " + uuid);
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) " +
                            "VALUES (?, ?)  ")) {
                        ps.setString(1, uuid);
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insertContacts(resume, conn);
                    insertSections(resume, conn);

                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = createResume(rs);
            }

            executePreparedStatementToGetResume(conn, "SELECT * FROM contact where resume_uuid=?",
                    uuid, (rs) -> addContact(rs, resume));

            executePreparedStatementToGetResume(conn, "SELECT * FROM section where resume_uuid=?",
                    uuid, (rs) -> addSection(rs, resume));

            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.executeAndGet("DELETE FROM resume r WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException("Resume does not exist in the Storage");
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumeByUuid = new LinkedHashMap<>();

            executePreparedStatementToGetAllSorted(conn, "SELECT * FROM resume r \n" +
                    "ORDER BY  full_name, uuid", (rs) -> {
                String uuid = rs.getString("uuid");
                Resume resume = createResume(rs);
                resumeByUuid.put(uuid, resume);
            });

            executePreparedStatementToGetAllSorted(conn, "SELECT * FROM contact", (rs) -> {
                Resume r = resumeByUuid.get(rs.getString("resume_uuid"));
                addContact(rs, r);
            });

            executePreparedStatementToGetAllSorted(conn, "SELECT * FROM section", (rs) -> {
                Resume r = resumeByUuid.get(rs.getString("resume_uuid"));
                addSection(rs, r);
            });

            return new ArrayList<>(resumeByUuid.values());
        });
    }

    private void executePreparedStatementToGetAllSorted(Connection conn, String query,
                                                        PreparedStatementExecutor statementExecutor) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                statementExecutor.execute(rs);
            }
        }
    }

    private void executePreparedStatementToGetResume(Connection conn,
                                                     String query,
                                                     String parameter,
                                                     PreparedStatementExecutor statementExecutor
    ) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, parameter);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                statementExecutor.execute(rs);
            }
        }
    }

    private interface PreparedStatementExecutor {
        void execute(ResultSet rs) throws SQLException;
    }

    @Override
    public int size() {
        LOG.info("Size");
        return sqlHelper.executeAndGet("SELECT COUNT(*) AS size FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    return rs.getInt("size");
                });
    }

    private Resume createResume(ResultSet rs) throws SQLException {
        return Resume.builder()
                .withUuid(rs.getString("uuid"))
                .withFullName(rs.getString("full_name"))
                .build();
    }

    private void insertContacts(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) " +
                "VALUES (?, ?, ?)  ")) {
            for (Map.Entry<ContactName, String> contact : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, contact.getKey().name());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, content) " +
                "VALUES (?, ?, ?)  ")) {
            for (Map.Entry<SectionName, Section> section : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, section.getKey().name());
                Section sectionValue = section.getValue();
                ps.setString(3, JsonParser.write(sectionValue, Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");

        if (value != null) {
            resume.addContact(ContactName.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String content = rs.getString("content");

        if (content != null) {
            SectionName type = SectionName.valueOf(rs.getString("type"));
            resume.addSection(type, JsonParser.read(content, Section.class));
        }
    }

    private void deleteAttributes(String uuid, String sqlQuery, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }
}
