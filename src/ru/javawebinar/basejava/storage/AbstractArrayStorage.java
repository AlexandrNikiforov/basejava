package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_CAPACITY = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size;

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveToStorage(Resume resume, int searchKey);

    protected abstract void deleteFromStorage(int numericSearchKey);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        validate(resume);
        int numericSearchKey = (Integer) searchKey;
        saveToStorage(resume, numericSearchKey);
        size++;
    }

    @Override
    protected void updateResumeInStorage(Resume resume, Object searchKey) {
        storage[(Integer) searchKey] = resume;
    }

    protected void validate(Resume resume) {
        if (size == STORAGE_CAPACITY) {
            throw new StorageException(resume.getUuid(), ERROR_TEXT_STORAGE_OUT_OF_SPACE);
        }
    }

    @Override
    protected Resume getFromStorage(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    public void doDelete(Object searchKey) {
        int numericSearchKey = (Integer) searchKey;
        deleteFromStorage(numericSearchKey);
        storage[size - 1] = null;
        size--;
    }

//    @Override
//    public List<Resume> getAllSorted() {
//        return Arrays.stream(storage)
//                .filter(Objects::nonNull)
//                .sorted(RESUME_NAME_COMPARATOR)
//                .collect(Collectors.toList());
//    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected List<Resume> doCopy() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }
}
