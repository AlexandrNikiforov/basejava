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

    protected void checkIfResumeAbsent(String uuid) {
        if (getIndex(uuid) < 0) {
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }
    }

    protected int getIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        return getIndexFromStorage(searchResume);
    }

    protected void checkIfResumeExist(Resume resume, boolean predicate) {
        if (predicate) {
            throw new ExistStorageException(resume.getUuid(),
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
        }
    }

    protected abstract int getIndexFromStorage(Resume searchResume);

    protected abstract void updateResumeInStorage(Resume resume);

    protected abstract Resume getFromStorage(String uuid);

    protected abstract void validate(Resume resume);

    protected abstract void saveToStorage(Resume resume);

    protected abstract void deleteFromStorage(String uuid);
}
