package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage2 extends AbstractStorage {

    protected static final int STORAGE_CAPACITY = 10_000;
    protected static final String ERROR_TEXT_NO_SUCH_RESUME =
            "ERROR: the storage doesn't contain the resume with uuid: ";
    protected static final String ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE = "ERROR: the storage already contains the " +
            "resume with uuid: ";
    protected static final String ERROR_TEXT_STORAGE_OUT_OF_SPACE = "ERROR: no space in the storage";
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size;

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid(),
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
        }
        if (size == STORAGE_CAPACITY) {
            throw new StorageException(resume.getUuid(), ERROR_TEXT_STORAGE_OUT_OF_SPACE);
        }
        saveToStorage(resume, index);
        size++;
    }

    @Override
    protected void validate(Resume resume, int index) {
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid(),
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
        }
        if (size == STORAGE_CAPACITY) {
            throw new StorageException(resume.getUuid(), ERROR_TEXT_STORAGE_OUT_OF_SPACE);
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid(), ERROR_TEXT_NO_SUCH_RESUME + resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected  Resume getFromStorage(int index) {
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }
        deleteFromArray(index);
        storage[size - 1] = null;
        size--;

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

    protected abstract void saveToStorage(Resume resume, int index);

    protected abstract void deleteFromArray(int index);
}
