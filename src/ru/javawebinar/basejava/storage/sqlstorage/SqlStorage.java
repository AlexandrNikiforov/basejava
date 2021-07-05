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
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        int affectedRows = 0;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            affectedRows = ps.executeUpdate();

        } catch (SQLException e) {
            throw new StorageException(e);
        }
        if (affectedRows == 0) throw new NotExistStorageException(r.getUuid(), "Resume does not exist in the Storage");
    }

    @Override
    public void save(Resume r) {
        sqlHelper.executeSqlStatement((ps) -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.executeUpdate();
        }, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)  ", r);
    }

//    @Override
//    public void save(Resume r) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement(
//                     "INSERT INTO resume (uuid, full_name) VALUES (?, ?)  ")) {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getFullName());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new ExistStorageException(r.getUuid(), "Resume exists in the storage", e);
//        }
//    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return Resume.builder()
                    .withUuid(uuid)
                    .withFullName(rs.getString("full_name"))
                    .build();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        int affectedRows = 0;

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            affectedRows = ps.executeUpdate();

        } catch (SQLException e) {
            throw new NotExistStorageException(uuid, "Resume does not exist in the Storage", e);
        }
        if (affectedRows == 0) throw new NotExistStorageException(uuid, "Resume does not exist in the Storage");
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
}
