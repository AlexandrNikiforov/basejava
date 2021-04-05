package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int index = Arrays.binarySearch(storage, 0, size, resume);
        if (index >= 0) {
            System.out.println(ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
            return;
        }
        if (size == STORAGE_CAPACITY) {
            System.out.println(ERROR_TEXT_STORAGE_OUT_OF_SPACE);
        } else {
            for (int i = size - 1; i >= 0 && i >= (-index - 1); i--) {
                storage[i + 1] = storage[i];
            }
            storage[(-index - 1)] = resume;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println(ERROR_TEXT_NO_SUCH_RESUME + uuid);
        } else {
            for (; index < size - 1; index++) {
                storage[index] = storage[index + 1];
            }
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
