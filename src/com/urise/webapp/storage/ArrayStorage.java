package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    public static final int STORAGE_CAPACITY = 10000;
    private final Resume[] storage = new Resume[STORAGE_CAPACITY];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (containsResume(resume.getUuid())) {
            System.out.println("ERROR: the storage already contains that resume");
            return;
        }
        if (size == STORAGE_CAPACITY) {
            System.out.println("ERROR: no space in the storage");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public void update(Resume resume) {
        if (!containsResume(resume.getUuid())) {
            System.out.println("ERROR: the storage doesn't contain that resume");
        } else {
            int index = findResumeIndex(resume.getUuid());
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        if (!containsResume(uuid)) {
            System.out.println("The storage doesn't contain resume with this id");
        } else {
            int index = findResumeIndex(uuid);
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        if (!containsResume(uuid)) {
            System.out.println("ERROR: the storage doesn't contain that resume");
        } else {
            int index = findResumeIndex(uuid);
            storage[index] = null;
            for (; index < size - 1; index++) {
                storage[index] = storage[index + 1];
            }
            storage[size - 1] = null;
            size--;
        }
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

    private boolean containsResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    private int findResumeIndex (String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
