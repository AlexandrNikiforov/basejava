package ru.javawebinar.basejava.storage.sqlstorage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        sqlHelper.executeAndGet("UPDATE resume SET full_name = ? WHERE uuid = ?",
                (ps -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException("Resume does not exist in the Storage");
                    }
                    return null;
                }));
    }

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        LOG.info("Save " + uuid);
        sqlHelper.executeAndGet("INSERT INTO resume (uuid, full_name) VALUES (?, ?)  ",
                ps -> {
                    try {
                        ps.setString(1, uuid);
                        ps.setString(2, r.getFullName());
                        ps.execute();

                    } catch (SQLException e) {
                        String sqlState = e.getSQLState();
                        if (sqlState.equalsIgnoreCase("23505")) {
                            throw new ExistStorageException(uuid, "Resume exists in the storage", e);
                        }
                    }
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.executeAndGet("SELECT * FROM resume r WHERE r.uuid =?",
                (PreparedStatement ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException("Storage is empty");
                    }
                    return createResume(uuid, rs);
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
        return sqlHelper.executeAndGet("SELECT * FROM resume r ORDER BY r.full_name, r.uuid",
                (PreparedStatement ps) -> {
                    List<Resume> resumesSorted = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumesSorted.add(
                                createResume(rs.getString("uuid"), rs));
                    }

                    return resumesSorted;
                });
    }

    @Override
    public int size() {
        LOG.info("Size");
        return sqlHelper.executeAndGet("SELECT COUNT(*) AS size FROM resume",
                (PreparedStatement ps) -> {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    return rs.getInt("size");
                });
    }

    private Resume createResume(String uuid, ResultSet rs) throws SQLException {
        return Resume.builder()
                .withUuid(uuid)
                .withFullName(rs.getString("full_name"))
                .build();
    }
}
