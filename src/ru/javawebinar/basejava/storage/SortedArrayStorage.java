package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void saveToStorage(Resume resume, int index) {
        index = -index - 1;
        if (index <= size - 1) {
            System.arraycopy(storage, index, storage, (index + 1), (size - index));
        }
        storage[index] = resume;
    }

    @Override
    protected void deleteFromStorage(int index) {
        System.arraycopy(storage, (index + 1), storage, (index), (size - index) - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchResume = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchResume, RESUME_COMPARATOR);
    }
}
