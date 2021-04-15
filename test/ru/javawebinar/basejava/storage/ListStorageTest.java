package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListStorageTest {

    private final ListStorage storage = new ListStorage();
    private static final String UUID_01 = "uuid01";
    private static final Resume RESUME_1 = new Resume(UUID_01);
    private static final String UUID_02 = "uuid02";
    private static final Resume RESUME_2 = new Resume(UUID_02);
    private static final String UUID_03 = "uuid03";
    private static final Resume RESUME_3 = new Resume(UUID_03);
    private static final String UUID_04 = "uuid04";
    private static final Resume NON_EXISTENT_RESUME = new Resume(UUID_04);

    @BeforeEach
    void init() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clearShouldFillArrayWithNull() {
        storage.clear();

        int expected = 0;
        int actual = storage.size();

        assertEquals(expected, actual);
    }

    @Test
    void updateShouldUpdateResumeIfResumeExistsInStorage() {
        Resume newResume = new Resume(UUID_01);
        storage.update(newResume);

        assertTrue(newResume == storage.get(UUID_01));
    }

    @Test
    void updateShouldThrowIfResumeDoesNotExistInStorage() {
        Executable executable = () -> storage.update(NON_EXISTENT_RESUME);

        assertThrows(NotExistStorageException.class, executable);
    }

    @Test
    void saveShouldAddResumeInStorageIfResumeNotExist() {
        Resume expectedResume = new Resume(UUID_04);
        storage.save(NON_EXISTENT_RESUME);
        Resume actualResume = storage.get(expectedResume.getUuid());

        assertEquals(expectedResume, actualResume);
    }

    @Test
    void saveShouldThrowWhenResumeExistsInStorage() {
        Executable executable = () -> storage.save(RESUME_1);

        assertThrows(ExistStorageException.class, executable);
    }

    @Test
    void getShouldReturnResumeWithGivenUuidWhenResumeExistsInStorage() {
        Resume expected = RESUME_2;
        Resume actual = storage.get(RESUME_2.getUuid());

        assertEquals(expected, actual);
    }

    @Test
    void getShouldThrowIfResumeDoesNotExistInStorage() {
        Executable executable = () -> storage.get(NON_EXISTENT_RESUME.getUuid());

        assertThrows(NotExistStorageException.class, executable);
    }

    @Test
    void deleteShouldRemoveResumeWhenResumeExistsInStorage() {
        storage.delete(RESUME_2.getUuid());
        Executable executable = () -> storage.get(RESUME_2.getUuid());

        int expectedSize = 2;
        int actualSize = storage.size();

        assertAll(
                () -> assertThrows(NotExistStorageException.class, executable),
                () -> assertEquals(expectedSize, actualSize));
    }

    @Test
    void deleteShouldThrowWhenResumeExistsInStorage() {
        Executable executable = () -> storage.delete(NON_EXISTENT_RESUME.getUuid());

        assertThrows(NotExistStorageException.class, executable);
    }

    @Test
    void getAllShouldReturnArrayOfExistedResumes() {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Resume[] actual = storage.getAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    void sizeShouldReturnNumberOfResumesInStorage() {
        int expected = 3;
        int actual = storage.size();

        assertEquals(expected, actual);
    }
}
