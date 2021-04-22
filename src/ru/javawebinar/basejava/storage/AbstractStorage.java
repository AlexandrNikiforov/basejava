package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    protected static final String ERROR_TEXT_NO_SUCH_RESUME =
            "ERROR: the storage doesn't contain the resume with uuid: ";
    protected static final String ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE = "ERROR: the storage already contains the " +
            "resume with uuid: ";
    protected static final String ERROR_TEXT_STORAGE_OUT_OF_SPACE = "ERROR: no space in the storage";

    protected static final Comparator<Resume> RESUME_NAME_COMPARATOR = (resume1, resume2) ->
            resume1.getFullName().compareTo(resume2.getFullName());

    protected abstract void updateResumeInStorage(Resume resume, Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected abstract Resume getFromStorage(Object searchKey, String uuid);

    protected abstract Object getSearchKey(String uuid);

    @Override
    public void update(Resume resume) {
        Object searchKey = getKeyIfResumeExist(resume.getUuid());
        updateResumeInStorage(resume, searchKey);
    }

    public void save(Resume resume) {
        Object searchKey = getKeyIfResumeNotExist(resume);
        doSave(resume, searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getKeyIfResumeExist(uuid);
        doDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getKeyIfResumeExist(uuid);
        return getFromStorage(searchKey, uuid);
    }

    protected Object getKeyIfResumeExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }
        return getSearchKey(uuid);
    }

    protected Object getKeyIfResumeNotExist(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid(),
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
        }
        return getSearchKey(resume.getUuid());
    }
}
