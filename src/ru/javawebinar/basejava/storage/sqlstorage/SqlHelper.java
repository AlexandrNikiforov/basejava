package ru.javawebinar.basejava.storage.sqlstorage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);

    }

    public void executeSqlStatement(SqlExecutor executor, String sqlQuery, Resume r) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            executor.execute(ps);
        } catch (SQLException e) {
            throw new ExistStorageException(r.getUuid(), "Resume exists in the storage", e);
        }
    }

    public void executeSqlStatement(SqlExecutor executor, String sqlQuery) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            executor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void executeDml(SqlManipulator sqlManipulator, String sqlQuery, String uuid) {
        int affectedRows = 0;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            affectedRows = sqlManipulator.execute(ps);
        } catch (SQLException e) {
            throw new NotExistStorageException(uuid, "Resume does not exist in the Storage", e);
        }
        if (affectedRows == 0) throw new NotExistStorageException(uuid, "Resume does not exist in the Storage");
    }

    public <T> T executeAndGet(SqlGetter<T> sqlGetter, String sqlQuery, String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sqlQuery)) {
            return sqlGetter.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
