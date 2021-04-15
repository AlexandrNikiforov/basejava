package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;
import java.util.stream.Collectors;

public class ListStorage extends AbstractStorage {

    List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    public Resume[] getAll() {
        return storage.stream()
                .filter(Objects::nonNull)
                .toArray(Resume[]::new);
    }

    @Override
    protected void validate(Resume resume, int index) {
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid(),
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
        }
    }

    @Override
    protected void updateResumeInStorage(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected Resume getFromStorage(int index) {
        return storage.get(index);
    }

    @Override
    protected void deleteFromStorage(int index) {
        storage.remove(index);
    }

    //
//    public Resume[] getAll() {
//        return Arrays.stream(storage)
//                .filter(Objects::nonNull)
//                .toArray(Resume[]::new);
//    }
//
//    public int size() {
//        return size;
//    }
//
//    protected abstract int getIndex(String uuid);
//
//
    @Override
    protected void saveToStorage(Resume resume, int index) {
        index = -index - 1;
        if (index <= storage.size() - 1) {
            System.arraycopy(storage, index, storage, (index + 1), (storage.size() - index));
        }
        storage.add(index, resume);
    }
//    protected abstract void deleteFromArray(int index);

    @Override
    protected int getIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        return Collections.binarySearch(storage, searchResume);
    }


}
