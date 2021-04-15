package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;

public class ListStorage extends AbstractStorage {

    List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.stream()
                .filter(Objects::nonNull)
                .toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
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
    protected void validate(Resume resume, int index) {
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid(),
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        return Collections.binarySearch(storage, searchResume);
    }

    @Override
    protected void saveToStorage(Resume resume, int index) {
        index = -index - 1;
        storage.add(index, resume);
    }

    @Override
    protected void deleteFromStorage(int index) {
        storage.remove(index);
    }
}
