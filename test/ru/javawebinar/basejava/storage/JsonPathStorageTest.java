package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.serializer.JsonStreamSerializer;

import static org.junit.jupiter.api.Assertions.*;

class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }
}