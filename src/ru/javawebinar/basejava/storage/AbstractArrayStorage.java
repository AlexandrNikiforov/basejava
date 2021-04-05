package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_CAPACITY = 10_000;
    protected static final String ERROR_TEXT_NO_SUCH_RESUME = "ERROR: the storage doesn't contain the resume with uuid: ";
    protected static final String ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE = "ERROR: the storage already contains the " +
            "resume with uuid: ";
    protected static final String ERROR_TEXT_STORAGE_OUT_OF_SPACE = "ERROR: no space in the storage";
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println(ERROR_TEXT_NO_SUCH_RESUME + resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println(ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
            return;
        }
        if (size == STORAGE_CAPACITY) {
            System.out.println(ERROR_TEXT_STORAGE_OUT_OF_SPACE);
        } else {
            performSavingAfterValidation(resume, index);
        }
    }

    protected abstract void performSavingAfterValidation(Resume resume, int index);

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println(ERROR_TEXT_NO_SUCH_RESUME + uuid);
            return null;
        } else {
            return storage[index];
        }
    }

    public Resume[] getAll() {
        return Arrays.stream(storage)
                .filter(Objects::nonNull)
                .toArray(Resume[]::new);
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);
}
