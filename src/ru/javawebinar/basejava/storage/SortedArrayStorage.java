package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(Resume resume, int index) {
        for (int i = size - 1; i >= 0 && i >= (-index - 1); i--) {
            storage[i + 1] = storage[i];
        }
        storage[(-index - 1)] = resume;
    }

    @Override
    protected void deleteFromArray(int index) {
        for (; index < size - 1; index++) {
            storage[index] = storage[index + 1];
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}