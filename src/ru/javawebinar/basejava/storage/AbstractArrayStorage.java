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
        int searchKey = (int) getKeyIfResumeNotExist(resume.getUuid(),
                (int)getIndexFromStorage(new Resume(resume.getUuid())) >= 0);
        validate(resume);
        saveToStorage(resume, searchKey);
        size++;
    }

    @Override
    protected void updateResumeInStorage(Resume resume) {

        storage[(int)getIndexFromStorage(new Resume(resume.getUuid()))] = resume;
    }

    protected void validate(Resume resume) {
        if (size == STORAGE_CAPACITY) {
            throw new StorageException(resume.getUuid(), ERROR_TEXT_STORAGE_OUT_OF_SPACE);
        }
    }

    @Override
    protected Resume getFromStorage(String uuid) {
        return storage[(int)getIndexFromStorage(new Resume(uuid))];
    }

    @Override
    public void delete(String uuid) {
        int searchKey = (int) getKeyIfResumeExist(uuid);
        deleteFromStorage(searchKey);
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

    protected abstract void saveToStorage(Resume resume, int searchKey);

    protected abstract void deleteFromStorage(int searchKey);
}
