package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_CAPACITY = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        validate(resume);
        saveToStorage(resume);
        size++;
    }

    @Override
    protected void updateResumeInStorage(Resume resume) {
        storage[getIndex(resume.getUuid())] = resume;
    }

    @Override
    protected void validate(Resume resume) {
        checkIfResumeExist(resume, getIndex(resume.getUuid()) >= 0);
        if (size == STORAGE_CAPACITY) {
            throw new StorageException(resume.getUuid(), ERROR_TEXT_STORAGE_OUT_OF_SPACE);
        }
    }

    @Override
    protected Resume getFromStorage(String uuid) {
        return storage[getIndex(uuid)];
    }

    @Override
    public void delete(String uuid) {
        checkIfResumeAbsent(uuid);
        deleteFromStorage(uuid);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.stream(storage)
                .filter(Objects::nonNull)
                .toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract void saveToStorage(Resume resume);

    protected abstract void deleteFromStorage(String uuid);
}
