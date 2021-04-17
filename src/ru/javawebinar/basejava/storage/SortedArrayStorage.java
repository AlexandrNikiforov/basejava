package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToStorage(Resume resume) {
        int index = getIndex(resume.getUuid());
        index = -index - 1;
        if (index <= size - 1) {
            System.arraycopy(storage, index, storage, (index + 1), (size - index));
        }
        storage[index] = resume;
    }

    @Override
    protected void deleteFromStorage(String uuid) {
        int index = getIndex(uuid);
        System.arraycopy(storage, (index + 1), storage, (index), (size - index) - 1);
    }

    @Override
    protected int getIndexFromStorage(Resume searchResume) {
        return Arrays.binarySearch(storage, 0, size, searchResume);
    }
}
