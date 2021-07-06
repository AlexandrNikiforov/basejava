package ru.javawebinar.basejava.storage.sqlstorage;

import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.storage.Storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Add logging
//move duplicated code to sqlhelper class

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeSqlStatement(PreparedStatement::execute, "DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.executeDml((ps -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    return ps.executeUpdate();
                }),
                "UPDATE resume SET full_name = ? WHERE uuid = ?", r.getUuid());
    }

    @Override
    public void save(Resume r) {
        sqlHelper.executeSqlStatement((ps) -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.executeUpdate();
        }, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)  ", r);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeAndGet((PreparedStatement ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }

                    return Resume.builder()
                            .withUuid(uuid)
                            .withFullName(rs.getString("full_name"))
                            .build();
                }, "SELECT * FROM resume r WHERE r.uuid =?",
                uuid);
    }

//    @Override
//    public Resume get(String uuid) {
//        return sqlHelper.executeAndGet(new SqlGetter<Resume>() {
//                                    @Override
//                                    public Resume execute(PreparedStatement ps) throws SQLException {
//                                        ps.setString(1, uuid);
//                                        ResultSet rs = ps.executeQuery();
//                                        if (!rs.next()) {
//                                            throw new NotExistStorageException(uuid);
//                                        }
//
//                                        return Resume.builder()
//                                                .withUuid(uuid)
//                                                .withFullName(rs.getString("full_name"))
//                                                .build();
//                                    }
//                                }, "SELECT * FROM resume r WHERE r.uuid =?",
//                uuid);
//    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeDml((ps) -> {
            ps.setString(1, uuid);
            return ps.executeUpdate();
        }, "DELETE FROM resume r WHERE r.uuid =?", uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumesSorted = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM resume r ORDER BY r.full_name")) {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("Storage is empty");
            }
            resumesSorted.add(
                    Resume.builder()
                            .withUuid(rs.getString("uuid"))
                            .withFullName(rs.getString("full_name"))
                            .build());
            while (rs.next()) {
                resumesSorted.add(
                        Resume.builder()
                                .withUuid(rs.getString("uuid"))
                                .withFullName(rs.getString("full_name"))
                                .build());
            }
            return resumesSorted;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT COUNT(*) AS size FROM resume")) {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("Storage is empty");
            }

            return rs.getInt("size");
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
//
//    @Override
//    public int size() {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement(
//                     "SELECT COUNT(*) AS size FROM resume")) {
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new NotExistStorageException("Storage is empty");
//            }
//
//            return rs.getInt("size");
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }
}
