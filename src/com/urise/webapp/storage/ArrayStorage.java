package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (!containsResume(r)) {
            storage[size] = r;
            size++;
        } else {
            throw new IllegalArgumentException("The storage already contains that resume");
        }
    }

    public void update(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(resume.getUuid())) {
                storage[i] = resume;
                break;
            }
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        int i = 0;
        for (; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                storage[i] = null;
                break;
            }
        }
        for (; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.stream(storage)
                .filter(Objects::nonNull)
                .toArray(Resume[]::new);
    }

    public int size() {
        return size;
    }

    private boolean containsResume(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(resume)) {
                return true;
            }
        }
        return false;
    }
}
