package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();
    protected final Predicate<Resume> storageContainsTheResume = resume -> (getIndex(resume.getUuid())) >= 0;

    @Override
    public void save(Resume resume) {
        int searchKey = (int) getKeyIfResumeNotExist(resume, storageContainsTheResume);
        saveToStorage(resume, searchKey);
    }

    private static final Comparator<Resume> RESUME_COMPARATOR = (resume1, resume2) ->
            resume1.getUuid().compareTo(resume2.getUuid());

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
    public List<Resume> getAllSorted() {
        return storage.stream()
                .filter(Objects::nonNull)
                .sorted(RESUME_NAME_COMPARATOR)
                .collect(Collectors.toList());
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
    protected Object getIndexFromStorage(Resume searchResume) {
        return Collections.binarySearch(storage, searchResume, RESUME_COMPARATOR);
    }

    protected void saveToStorage(Resume resume, int searchKey) {
        searchKey = -searchKey - 1;
        storage.add(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        getKeyIfResumeExist(uuid);
        storage.remove(getIndex(uuid));
    }

    private int getIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        return (int) getIndexFromStorage(searchResume);
    }
}
