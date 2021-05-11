package ru.javawebinar.basejava.storage;

class ObjectStreamPathStorageTest extends AbstractStorageTest {

    protected ObjectStreamPathStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}
