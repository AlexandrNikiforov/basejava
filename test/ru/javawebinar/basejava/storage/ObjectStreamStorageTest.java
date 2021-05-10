package ru.javawebinar.basejava.storage;

import static org.junit.jupiter.api.Assertions.*;

class ObjectStreamStorageTest extends AbstractStorageTest {

    protected ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}