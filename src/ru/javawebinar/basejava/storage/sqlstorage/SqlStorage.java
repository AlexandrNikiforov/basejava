package ru.javawebinar.basejava.storage.sqlstorage;

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
        sqlHelper.executeAndGet("UPDATE resume SET full_name = ? WHERE uuid = ?",
                (ps -> {
                    String uuid = r.getUuid();
                    LOG.info("Update " + uuid);
                    ps.setString(1, r.getFullName());
                    ps.setString(2, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid, "Resume does not exist in the Storage");
                    }
                    return null;
                }));
    }

    @Override
    public void save(Resume r) {
        sqlHelper.executeAndGet("INSERT INTO resume (uuid, full_name) VALUES (?, ?)  ",
                ps -> {
                    String uuid = r.getUuid();
                    LOG.info("Save " + uuid);
                    ps.setString(1, uuid);
                    ps.setString(2, r.getFullName());
                    ps.execute();
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
                        throw new NotExistStorageException(uuid);
                    }
                    return createResume(rs);
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
                ps -> {
                    List<Resume> resumesSorted = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumesSorted.add(
                                createResume(rs));
                    }
                    return resumesSorted;
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
}
