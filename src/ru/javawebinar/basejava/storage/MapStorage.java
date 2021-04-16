package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

import static ru.javawebinar.basejava.storage.AbstractStorage.ERROR_TEXT_NO_SUCH_RESUME;
import static ru.javawebinar.basejava.storage.AbstractStorage.ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE;

public class MapStorage {

    private final Map<String, Resume> storage = new HashMap<>();

//    @Override
    public void save(Resume resume) {
        validate(resume);
        storage.put(resume.getUuid(), resume);    }

    public Resume get(String uuid) {
        checkIfResumeAbsent(uuid);
        return storage.get(uuid);
    }

    protected void validate(Resume resume) {
        checkIfResumeExist(resume);
    }

    protected void checkIfResumeExist(Resume resume) {
        if (storage.containsKey(resume.getUuid())) {
            throw new ExistStorageException(resume.getUuid(),
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
        }
    }

    protected void checkIfResumeAbsent(String uuid) {
        if (!storage.containsKey(uuid)) {
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }

    }



}
