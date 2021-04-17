package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToStorage(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteFromStorage(String uuid) {

        storage[getIndex(uuid)] = storage[size - 1];
    }

    @Override
    protected int getIndexFromStorage(Resume searchResume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(searchResume.getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
