package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToStorage(Resume resume, int searchKey) {

        storage[size] = resume;
    }

    @Override
    protected void deleteFromStorage(int searchKey) {
        storage[searchKey] = storage[size - 1];
    }

    @Override
    protected Object getSearchKeyFromStorage(Resume searchResume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(searchResume.getUuid())) {
                return i;
            }
        }
        return -1;
    }

//    @Override
//    public List<Resume> getAllSorted() {
//        return Arrays.stream(storage)
//                .filter(Objects::nonNull)
//                .sorted()
//                .collect(Collectors.toList());
//    }
}
