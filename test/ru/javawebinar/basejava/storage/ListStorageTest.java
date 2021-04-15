package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

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
//        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
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
    void sizeShouldReturnNumberOfResumesInStorage() {
        int expected = 3;
        int actual = storage.size();

        assertEquals(expected, actual);
    }

//    @Disabled
//    @Test
//    void clearShouldFillArrayWithNullAndMakeSizeZero() {
//        Resume[] expectedResumes = new Resume[0];
//        storage.clear();
//        Resume[] actualResumes = storage.getAll();
//
//        int expectedSize = 0;
//        int actualSize = storage.size();
//
//        assertAll(
//                () -> assertArrayEquals(expectedResumes, actualResumes),
//                () -> assertEquals(expectedSize, actualSize)
//        );
//    }
//
}