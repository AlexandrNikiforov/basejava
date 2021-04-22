package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_CAPACITY = 10_000;
//    protected final Predicate<Resume> storageContainsTheResume = (r) -> (int) (getIndex(r.getUuid())) >= 0;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected abstract Object getSearchKeyFromStorage(Resume searchResume);

    protected abstract void saveToStorage(Resume resume, int searchKey);

    //    @Override
//
//    public void save(Resume resume) {
//        int searchKey = (int) getKeyIfResumeNotExist(resume,
//                storageContainsTheResume);
//        validate(resume);
//        saveToStorage(resume, searchKey);
//        size++;

//    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        validate(resume);
        int numericSearchKey = (int) searchKey;
        saveToStorage(resume, numericSearchKey);
        size++;
    }

    @Override
    protected void updateResumeInStorage(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
    }

    protected void validate(Resume resume) {
        if (size == STORAGE_CAPACITY) {
            throw new StorageException(resume.getUuid(), ERROR_TEXT_STORAGE_OUT_OF_SPACE);
        }
    }

    @Override
    protected Resume getFromStorage(Object searchKey, String uuid) {
        return storage[(int) searchKey];
    }

    @Override
    public void doDelete(Object searchKey) {
        int numericSearchKey = (int) searchKey;
        deleteFromStorage(numericSearchKey);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteFromStorage(int numericSearchKey);

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

    public Object getSearchKey(String uuid) {
        Resume searchResume = new Resume(uuid);
        return getSearchKeyFromStorage(searchResume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

//    @Override
//    protected abstract void deleteFromStorage(Integer searchKey);
}
