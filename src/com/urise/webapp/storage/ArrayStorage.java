package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_CAPACITY = 10_000;
    private static final String ERROR_TEXT_NO_SUCH_RESUME = "ERROR: the storage doesn't contain the resume with uuid: ";
    private static final String ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE = "ERROR: the storage already contains the " +
            "resume with uuid: ";
    private static final String ERROR_TEXT_STORAGE_OUT_OF_SPACE = "ERROR: no space in the storage";
    private final Resume[] storage = new Resume[STORAGE_CAPACITY];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (findResumeIndex(resume.getUuid()) >= 0) {
            System.out.println(ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE + resume.getUuid());
            return;
        }
        if (size == STORAGE_CAPACITY) {
            System.out.println(ERROR_TEXT_STORAGE_OUT_OF_SPACE);
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public void update(Resume resume) {
        int index = findResumeIndex(resume.getUuid());
        if (index < 0) {
            System.out.println(ERROR_TEXT_NO_SUCH_RESUME + resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = findResumeIndex(uuid);
        if (index < 0) {
            System.out.println(ERROR_TEXT_NO_SUCH_RESUME + uuid);
        } else {
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index < 0) {
            System.out.println(ERROR_TEXT_NO_SUCH_RESUME + uuid);
        } else {
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

    private int findResumeIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
