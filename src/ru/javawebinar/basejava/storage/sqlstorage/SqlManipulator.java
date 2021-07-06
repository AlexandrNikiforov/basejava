package ru.javawebinar.basejava.storage.sqlstorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlManipulator {
    int execute(PreparedStatement ps) throws SQLException;
}
