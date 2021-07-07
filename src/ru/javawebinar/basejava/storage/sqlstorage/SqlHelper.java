package ru.javawebinar.basejava.storage.sqlstorage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
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

    public <T> T executeAndGet(String sqlQuery, StatementExecutionStrategy<T> sqlGetter) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sqlQuery)) {
            return sqlGetter.execute(ps);
        } catch (SQLException e) {
            String sqlState = e.getSQLState();
            if (sqlState.equalsIgnoreCase("23505")) {
                throw new ExistStorageException("Resume exists in the storage", e);
            }
            throw new StorageException(e);
        }
    }
}
