package ru.javawebinar.basejava.storage;

class ObjectStreamStorageTest extends AbstractStorageTest {

    protected ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}