package ru.javawebinar.basejava.storage.sqlstorage;

import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void executeSqlStatement(SqlExecutor executor, String sqlQuery) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            executor.execute(sqlQuery);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
