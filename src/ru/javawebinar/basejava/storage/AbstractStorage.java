package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

//public abstract class AbstractStorage implements Storage { //TODO do implement Storage
public abstract class AbstractStorage {

    protected static final String ERROR_TEXT_NO_SUCH_RESUME =
            "ERROR: the storage doesn't contain the resume with uuid: ";
    protected static final String ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE = "ERROR: the storage already contains the " +
            "resume with uuid: ";
    protected static final String ERROR_TEXT_STORAGE_OUT_OF_SPACE = "ERROR: no space in the storage";
    //    protected final Collection<Resume> storage;
    protected int size;

//    public AbstractStorage(Collection<Resume> storage) {
//        this.storage = storage;
//    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        validate(resume, index);
        saveToStorage(resume, index);
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }
        return getFromStorage(index);
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid(), ERROR_TEXT_NO_SUCH_RESUME + resume.getUuid());
        } else {
            updateResumeInStorage(resume, index);
        }
    }



    protected abstract void updateResumeInStorage(Resume resume, int index);

    protected abstract int size ();

    protected abstract Resume getFromStorage(int index);

    protected abstract void validate(Resume resume, int index);

    protected abstract void clear();

    protected abstract int getIndex(String uuid);

    protected abstract void saveToStorage(Resume resume, int index);
}
