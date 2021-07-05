package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.storage.sqlstorage.SqlStorage;

class SqlStorageTest extends AbstractStorageTest {

    protected SqlStorageTest() {

        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(),
                Config.get().getDbPassword()));
    }
}