package ru.javawebinar.basejava.storage;
/*
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapStorage2 extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();
    private final Predicate<Resume> storageContainsTheResume = storage::containsValue;

    @Override
    public void update(Resume resume) {
        getKeyIfResumeExist(resume.getUuid());
        updateResumeInStorage(resume);
    }

    @Override
    public void save(Resume resume) {
        getKeyIfResumeNotExist(resume, storageContainsTheResume);
        saveToStorage(resume);
    }

    @Override
    public Resume get(String uuid) {
        getKeyIfResumeExist(uuid);
        return getFromStorage(uuid);
    }

    @Override
    public void delete(String uuid) {
        getKeyIfResumeExist(uuid);
        storage.remove(uuid);
    }

    @Override
    protected Object getKeyIfResumeExist(String uuid) {
        if (!storage.containsKey(uuid)) {
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }
        return getSearchKey(new Resume(uuid));
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
    protected Object getSearchKey(Resume searchResume) {
        return searchResume.getUuid();
    }

    @Override
    protected void updateResumeInStorage(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(String uuid) {
        return storage.get(uuid);
    }

    protected void saveToStorage(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }
}

 */
