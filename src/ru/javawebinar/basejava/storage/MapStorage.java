package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void update(Resume resume) {
        checkIfResumeAbsent(resume.getUuid());
        updateResumeInStorage(resume);
    }

    @Override
    public void save(Resume resume) {
        validate(resume);
        saveToStorage(resume);
    }

    @Override
    public Resume get(String uuid) {
        checkIfResumeAbsent(uuid);
        return getFromStorage(uuid);
    }

    @Override
    public void delete(String uuid) {
        checkIfResumeAbsent(uuid);
        deleteFromStorage(uuid);
    }

    @Override
    protected void checkIfResumeAbsent(String uuid) {
        if (!storage.containsKey(uuid)) {
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }
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
    public int size() {
        return storage.size();
    }

    //The method is not supported by MapStorage
    @Override
    protected int getIndexFromStorage(Resume searchResume) {
        return -1;
    }

    @Override
    protected void updateResumeInStorage(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getFromStorage(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void validate(Resume resume) {
        checkIfResumeExist(resume, storage.containsKey(resume.getUuid()));
    }

    @Override
    protected void saveToStorage(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteFromStorage(String uuid) {
        storage.remove(uuid);
    }
}
