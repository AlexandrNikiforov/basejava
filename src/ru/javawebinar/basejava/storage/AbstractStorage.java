package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.function.Predicate;

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
    public Resume get(String uuid) {
        getKeyIfResumeExist(uuid);
        return getFromStorage(uuid);
    }

    protected Object getKeyIfResumeExist(String uuid) {
        if ((int) getIndexFromStorage(new Resume(uuid)) < 0) {
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }
        return getIndexFromStorage(new Resume(uuid));
    }

    protected Object getKeyIfResumeNotExist(Resume resume, Predicate<Resume> storageContainsTheResume) {
        if (storageContainsTheResume.test(resume)) {
            throw new ExistStorageException(resume.getUuid(),
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
        }
        return getIndexFromStorage(new Resume(resume.getUuid()));
    }

    protected abstract Object getIndexFromStorage(Resume searchResume);

    protected abstract void updateResumeInStorage(Resume resume);

    protected abstract Resume getFromStorage(String uuid);
}
