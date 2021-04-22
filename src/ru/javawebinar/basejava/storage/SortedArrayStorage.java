package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void saveToStorage(Resume resume, int searchKey) {
        searchKey = -searchKey - 1;
        if (searchKey <= size - 1) {
            System.arraycopy(storage, searchKey, storage, (searchKey + 1), (size - searchKey));
        }
        storage[searchKey] = resume;
    }

    @Override
    protected void deleteFromStorage(int searchKey) {
        System.arraycopy(storage, (searchKey + 1), storage, (searchKey), (size - searchKey) - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchResume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchResume, RESUME_COMPARATOR);
    }
}
