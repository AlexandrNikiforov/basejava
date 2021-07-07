package ru.javawebinar.basejava.storage.sqlstorage;

import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        LOG.info("Update " + r.getUuid());
        sqlHelper.executeAndGet("UPDATE resume SET full_name = ? WHERE uuid = ?",
                (ps -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    int affectedRows = ps.executeUpdate();
                    if (affectedRows == 0) throw new NotExistStorageException("Resume does not exist in the Storage");
                    return affectedRows;
                }));
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r.getUuid());
        sqlHelper.executeAndGet("INSERT INTO resume (uuid, full_name) VALUES (?, ?)  ",
                ps -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.executeUpdate();
                    return Optional.empty();
                });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.executeAndGet("SELECT * FROM resume r WHERE r.uuid =?",
                (PreparedStatement ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    checkIfResultSetIsEmpty(rs);
                    return createResume(uuid, rs);
                });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.executeAndGet("DELETE FROM resume r WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    int affectedRows = ps.executeUpdate();
                    if (affectedRows == 0) throw new NotExistStorageException("Resume does not exist in the Storage");
                    return affectedRows;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return sqlHelper.executeAndGet("SELECT * FROM resume r ORDER BY r.full_name",
                (PreparedStatement ps) -> {
                    List<Resume> resumesSorted = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    checkIfResultSetIsEmpty(rs);
                    do {
                        resumesSorted.add(
                                createResume(rs.getString("uuid"), rs));
                    }
                    while (rs.next());
                    return resumesSorted;
                });
    }

    @Override
    public int size() {
        LOG.info("Size");
        return sqlHelper.executeAndGet("SELECT COUNT(*) AS size FROM resume",
                (PreparedStatement ps) -> {
                    ResultSet rs = ps.executeQuery();
                    checkIfResultSetIsEmpty(rs);
                    return rs.getInt("size");
                });
    }

    private void checkIfResultSetIsEmpty(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            throw new NotExistStorageException("Storage is empty");
        }
    }

    private Resume createResume(String uuid, ResultSet rs) throws SQLException {
        return Resume.builder()
                .withUuid(uuid)
                .withFullName(rs.getString("full_name"))
                .build();
    }
}
