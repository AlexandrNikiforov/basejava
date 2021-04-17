package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage2 extends AbstractStorage {

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
    protected void checkIfResumeAbsent(String uuid, int index) {
        //not implemented
    }

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
        return storage.entrySet().stream()
                .map(Map.Entry::getValue)
                .toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateResumeInStorage(Resume resume, int index) {
        //not implemented
    }

    @Override
    protected Resume getFromStorage(int index) {
        return storage.get(index);
    } // TODO: doesn't work for map

    protected Resume getFromStorage(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void validate(Resume resume, int index) {
        //not implemented
    }

    protected void validate(Resume resume) {
        checkIfResumeExist(resume, storage.containsKey(resume.getUuid()));
    }

    @Override
    protected int getIndexFromStorage(Resume searchResume) {
        return -1;
    }

    @Override
    protected void saveToStorage(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    protected void saveToStorage(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteFromStorage(int index) {
//not implemented
    }

    protected void deleteFromStorage(String uuid) {
        storage.remove(uuid);
    }

    protected void updateResumeInStorage(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }
}
