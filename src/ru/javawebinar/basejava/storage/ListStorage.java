package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        storage.add(resume);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.stream()
                .filter(Objects::nonNull)
                .sorted(RESUME_NAME_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateResumeInStorage(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
    }

    @Override
    protected Resume getFromStorage(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected List<Resume> doCopy() {
        List<Resume> resumesCopy = new ArrayList<>();
        Collections.copy(resumesCopy, storage);
        return resumesCopy;
    }
}
