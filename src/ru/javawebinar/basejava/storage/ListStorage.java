package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void save(Resume resume) {
        int searchKey = getKeyIfResumeNotExist(resume.getUuid(), getIndex(resume.getUuid()) >= 0);
        saveToStorage(resume, searchKey);
    }

    //Comparator is implemented as anonymous class
    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };

//    Comparator is implemented as lambda expression
//    Comparator<Resume> resumeComparator = (resume1, resume2) -> resume1.getUuid().compareTo(resume2.getUuid());

    //Comparator is implemented using Comparator.comparing static method
//    Comparator<Resume> resumeComparator = Comparator.comparing(Resume::getUuid);

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.stream()
                .filter(Objects::nonNull)
                .toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateResumeInStorage(Resume resume) {
        storage.set(getIndex(resume.getUuid()), resume);
    }

    @Override
    protected Resume getFromStorage(String uuid) {
        return storage.get(getIndex(uuid));
    }

//    @Override
//    protected void validate(Resume resume) {
//        getKeyIfResumeNotExist(resume.getUuid(), getIndex(resume.getUuid()) >= 0);
//    }

    @Override
    protected int getIndexFromStorage(Resume searchResume) {
        return Collections.binarySearch(storage, searchResume, RESUME_COMPARATOR);
    }

    protected void saveToStorage(Resume resume, int searchKey) {
        searchKey = -searchKey - 1;
        storage.add(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        getKeyIfResumeExist(uuid);
        storage.remove(getIndex(uuid));
    }
}
