package ru.javawebinar.basejava.storage.sqlstorage;

import java.util.function.Consumer;

public interface SqlStrategy {
    void identifyAffectedRows(Consumer<Integer> consumer);

}
