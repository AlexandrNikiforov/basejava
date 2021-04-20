package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_CAPACITY = 10_000;
    protected final Predicate<Resume> storageContainsTheResume = (r) -> (int) (getIndex(r.getUuid())) >= 0;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override

    public void save(Resume resume) {
        int searchKey = (int) getKeyIfResumeNotExist(resume,
                storageContainsTheResume);
        validate(resume);
        saveToStorage(resume, searchKey);
        size++;
    }

    @Override
    protected void updateResumeInStorage(Resume resume) {

        storage[(int) getIndex(resume.getUuid())] = resume;
    }

    protected void validate(Resume resume) {
        if (size == STORAGE_CAPACITY) {
            throw new StorageException(resume.getUuid(), ERROR_TEXT_STORAGE_OUT_OF_SPACE);
        }
    }

    @Override
    protected Resume getFromStorage(String uuid) {
        return storage[(int) getIndex(uuid)];
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
    public List<Resume> getAllSorted() {
        return Arrays.stream(storage)
                .filter(Objects::nonNull)
                .sorted(RESUME_NAME_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return size;
    }

    public Object getIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        return getIndexFromStorage(searchResume);
    }


    protected abstract void saveToStorage(Resume resume, int searchKey);

    protected abstract void deleteFromStorage(int searchKey);
}
