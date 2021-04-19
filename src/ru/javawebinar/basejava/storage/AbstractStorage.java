package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected static final String ERROR_TEXT_NO_SUCH_RESUME =
            "ERROR: the storage doesn't contain the resume with uuid: ";
    protected static final String ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE = "ERROR: the storage already contains the " +
            "resume with uuid: ";
    protected static final String ERROR_TEXT_STORAGE_OUT_OF_SPACE = "ERROR: no space in the storage";

    @Override
    public void update(Resume resume) {
        getKeyIfResumeExist(resume.getUuid());
        updateResumeInStorage(resume);
    }

    @Override
    public abstract void save(Resume resume);
//    {
//        validate(resume);
//        saveToStorage(resume);
//    }

    @Override
    public Resume get(String uuid) {
        getKeyIfResumeExist(uuid);
        return getFromStorage(uuid);
    }

    @Override
    public abstract void delete(String uuid);
//    {
//        checkIfResumeAbsent(uuid);
//        deleteFromStorage(uuid);
//    }

    protected int getKeyIfResumeExist(String uuid) {
        if (getIndex(uuid) < 0) {
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }
        return getIndex(uuid);
    }

    protected int getIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        return getIndexFromStorage(searchResume);
    }

    protected int getKeyIfResumeNotExist(String uuid, boolean predicate) {
        if (predicate) {
            throw new ExistStorageException(uuid,
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + uuid);
        }
        return getIndex(uuid);
    }

    protected abstract int getIndexFromStorage(Resume searchResume);

    protected abstract void updateResumeInStorage(Resume resume);

    protected abstract Resume getFromStorage(String uuid);

//    protected abstract void deleteFromStorage(String uuid);
}
