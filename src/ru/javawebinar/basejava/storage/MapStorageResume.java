package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapStorageResume extends AbstractStorage<Resume> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void updateResumeInStorage(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doSave(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(Resume searchKey) {
        return searchKey;
    }

    @Override
    public void doDelete(Resume searchKey) {
        storage.remove((searchKey).getUuid());
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.values().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected List<Resume> doCopy() {
        return new ArrayList<>(storage.values());
    }
}
