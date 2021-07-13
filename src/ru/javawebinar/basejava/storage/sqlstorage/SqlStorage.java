package ru.javawebinar.basejava.storage.sqlstorage;

import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactName;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

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
            try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET  type = ?, value = ?\n" +
                    "WHERE resume_uuid = ?")) {
                setStringsFromContactsAndExecute(r, uuid, ps);
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        LOG.info("Save " + uuid);
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) " +
                            "VALUES (?, ?)  ")) {
                        ps.setString(1, uuid);
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO contact (resume_uuid, type, value) " +
                                    "VALUES (?, ?, ?)  ")) {
                        setStringsFromContactsAndExecute(r, uuid, ps);
                    }
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.executeAndGet(
                "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = createResume(rs);
                    do {
                        String value = rs.getString("value");
                        if (value != null) {
                            ContactName type = ContactName.valueOf(rs.getString("type"));
                            r.addContact(type, value);
                        }
                    } while (rs.next());
                    return r;
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
        return sqlHelper.executeAndGet("SELECT * FROM resume r \n" +
                        "LEFT JOIN contact c \n" +
                        "ON r.uuid = c.resume_uuid \n" +
                        "ORDER BY r.full_name, r.uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> resumeByUuid = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");


                        Resume r = resumeByUuid.get(uuid);
                        if (r == null) {
                            r = createResume(rs);
                            resumeByUuid.put(uuid, r);
                        }
                        String value = rs.getString("value");
                        if (value != null) {
                            ContactName type = ContactName.valueOf(rs.getString("type"));
                            r.addContact(type, value);
                        }
                    }
                    return new ArrayList<>(resumeByUuid.values());
                });
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

    private void setStringsFromContactsAndExecute(Resume r, String uuid, PreparedStatement ps) throws SQLException {
        for (Map.Entry<ContactName, String> contact : r.getContacts().entrySet()) {
            ps.setString(1, uuid);
            ps.setString(2, contact.getKey().name());
            ps.setString(3, contact.getValue());
            ps.addBatch();
        }
        ps.executeBatch();
    }
}
