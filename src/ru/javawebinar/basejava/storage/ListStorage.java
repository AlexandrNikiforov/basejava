package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

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
    protected void updateResumeInStorage(Resume resume) {

        storage.set(getIndex(resume.getUuid()), resume);
    }

    @Override
    protected Resume getFromStorage(String uuid) {
        return storage.get(getIndex(uuid));
    }

    @Override
    protected void validate(Resume resume) {
        checkIfResumeExist(resume, getIndex(resume.getUuid()) >= 0);
    }

    @Override
    protected int getIndexFromStorage(Resume searchResume) {
        return Collections.binarySearch(storage, searchResume);
    }

    @Override
    protected void saveToStorage(Resume resume) {
        int index = getIndex(resume.getUuid());
        index = -index - 1;
        storage.add(index, resume);
    }

    @Override
    protected void deleteFromStorage(String uuid) {
        storage.remove(getIndex(uuid));
    }
}
