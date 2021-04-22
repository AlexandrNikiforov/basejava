package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();
//    private final Predicate<Resume> storageContainsTheResume = resume -> storage.containsKey(resume.getUuid());

    @Override
    public void updateResumeInStorage(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Resume getFromStorage(Object searchKey, String uuid) {
        return storage.get((String) searchKey);
    }

    @Override
    public void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().stream()
                .toArray(Resume[]::new);
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.values().stream()
                .sorted(RESUME_NAME_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

//    @Override
//    protected void updateResumeInStorage(Resume resume) {
//        storage.put(resume.getUuid(), resume);
//    }


    protected void saveToStorage(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }
}
