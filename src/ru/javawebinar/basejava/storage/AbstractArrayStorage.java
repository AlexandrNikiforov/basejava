package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_CAPACITY = 10_000;
    protected static final String ERROR_TEXT_NO_SUCH_RESUME = "ERROR: the storage doesn't contain the resume with uuid: ";
    protected static final String ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE = "ERROR: the storage already contains the " +
            "resume with uuid: ";
    protected static final String ERROR_TEXT_STORAGE_OUT_OF_SPACE = "ERROR: no space in the storage";
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println(ERROR_TEXT_NO_SUCH_RESUME + uuid);
            return null;
        } else {
            return storage[index];
        }
    }

    protected abstract int getIndex(String uuid);
}
