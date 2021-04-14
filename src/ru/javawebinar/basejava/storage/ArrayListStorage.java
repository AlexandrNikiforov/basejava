package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

//public class ArrayListStorage extends AbstractStorage {
public class ArrayListStorage  {

    List<Resume> storage = new ArrayList<>();
    protected static final String ERROR_TEXT_NO_SUCH_RESUME =
            "ERROR: the storage doesn't contain the resume with uuid: ";
    protected static final String ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE = "ERROR: the storage already contains the " +
            "resume with uuid: ";
    protected static final String ERROR_TEXT_STORAGE_OUT_OF_SPACE = "ERROR: no space in the storage";

//    public ArrayListStorage(Collection<Resume> storage) {
//        super(storage);
//    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid(), ERROR_TEXT_NO_SUCH_RESUME + resume.getUuid());
        } else {
            storage.set(index, resume);
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid(),
                    ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
        }
        saveToArray(resume, index);
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
        }
        return storage.get(index);
    }

//
//    public void delete(String uuid) {
//        int index = getIndex(uuid);
//        if (index < 0) {
//            throw new NotExistStorageException(uuid, ERROR_TEXT_NO_SUCH_RESUME + uuid);
//        }
//        deleteFromArray(index);
//        storage[size - 1] = null;
//        size--;
//
//    }
//
//    public Resume[] getAll() {
//        return Arrays.stream(storage)
//                .filter(Objects::nonNull)
//                .toArray(Resume[]::new);
//    }
//
//    public int size() {
//        return size;
//    }
//
//    protected abstract int getIndex(String uuid);
//
//
private void saveToArray(Resume resume, int index) {
    index = -index - 1;
    if (index <= storage.size() - 1) {
        System.arraycopy(storage, index, storage, (index + 1), (storage.size() - index));
    }
    storage.add(index, resume);
}
//    protected abstract void deleteFromArray(int index);

    private int getIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        return Collections.binarySearch(storage, searchResume);
    }


}
