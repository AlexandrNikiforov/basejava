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
        int index = getIndex(resume.getUuid());
        checkIfResumeAbsent(resume.getUuid(), index);
        updateResumeInStorage(resume, index);
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        validate(resume, index);
        saveToStorage(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        checkIfResumeAbsent(uuid, index);
        return getFromStorage(index);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        checkIfResumeAbsent(uuid, index);
        deleteFromStorage(index);
    }

    protected void checkIfResumeAbsent(String uuid, int index) {
        if (index < 0) {
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

    protected abstract void updateResumeInStorage(Resume resume, int index);

    protected abstract Resume getFromStorage(int index);

    protected abstract void validate(Resume resume, int index);

    protected abstract void saveToStorage(Resume resume, int index);

    protected abstract void deleteFromStorage(int index);
}
