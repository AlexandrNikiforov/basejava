package ru.javawebinar.basejava.storage.sqlstorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementExecutionStrategy<T> {
    T execute(PreparedStatement ps) throws SQLException;
}
