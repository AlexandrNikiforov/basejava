package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToStorage(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void deleteFromStorage(int index) {
        storage[index] = storage[size - 1];
    }

//    @Override
//    protected int getIndex(String uuid) {
//        for (int i = 0; i < size; i++) {
//            if (storage[i].getUuid().equals(uuid)) {
//                return i;
//            }
//        }
//        return -1;
//    }

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
