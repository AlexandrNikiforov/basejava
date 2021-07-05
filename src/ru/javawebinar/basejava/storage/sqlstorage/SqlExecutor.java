package ru.javawebinar.basejava.storage.sqlstorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutor  {
    void execute (PreparedStatement ps) throws SQLException;
}
