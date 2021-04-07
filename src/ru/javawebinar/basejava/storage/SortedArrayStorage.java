package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(Resume resume, int index) {
        if (index < 0) {
            index = -index;
        }
        if (index - 1 <= size - 1) {
            System.arraycopy(storage, (index - 1), storage, index, (size - index + 1));
        }
        storage[(index - 1)] = resume;
    }

    @Override
    protected void deleteFromArray(int index) {
        System.out.println("index: " + index);
        System.arraycopy(storage, (index + 1), storage, (index), (size - index) - 1);
        storage[size - 1] = null;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
