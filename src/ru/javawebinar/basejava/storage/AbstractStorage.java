package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    protected static final String ERROR_TEXT_NO_SUCH_RESUME =
            "ERROR: the storage doesn't contain the resume with uuid: ";
    protected static final String ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE = "ERROR: the storage already contains the " +
            "resume with uuid: ";
    protected static final String ERROR_TEXT_STORAGE_OUT_OF_SPACE = "ERROR: no space in the storage";

    protected static final Comparator<Resume> RESUME_NAME_COMPARATOR =
            Comparator.comparing(Resume::getFullName);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void updateResumeInStorage(Resume resume, SK searchKey);

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract Resume getFromStorage(SK searchKey);

    protected abstract List<Resume> doCopy();

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume.getUuid());
        SK searchKey = getKeyIfResumeExists(resume.getUuid());
        updateResumeInStorage(resume, searchKey);
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume.getUuid());
        SK searchKey = getKeyIfResumeNotExists(resume);
        doSave(resume, searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getKeyIfResumeExists(uuid);
        doDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getKeyIfResumeExists(uuid);
        return getFromStorage(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> allResumes = new ArrayList<>(doCopy());
        allResumes.sort(RESUME_NAME_COMPARATOR);
        return allResumes;
    }

    private SK getKeyIfResumeExists(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("ERROR_TEXT_NO_SUCH_RESUME" + uuid);
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }
        return searchKey;
    }

    private SK getKeyIfResumeNotExists(Resume resume) {
        SK searchKey = getSearchKey(resume.getUuid());
        if (isExist(searchKey)) {
            LOG.warning(ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
            throw new ExistStorageException(resume.getUuid(),
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
        }
        return searchKey;
    }
}
