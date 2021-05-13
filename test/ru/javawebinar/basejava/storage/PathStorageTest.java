package ru.javawebinar.basejava.storage;

class PathStorageTest extends AbstractStorageTest {

    protected PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath()));
    }
}
