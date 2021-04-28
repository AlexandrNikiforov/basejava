package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractStorageTest {

    protected final Storage storage;

    protected static final String UUID_01 = "uuid01";
    protected static final Resume RESUME_1 = Resume.builder()
            .withUuid(UUID_01)
            .withFullName("C Name 1")
            .build();

    protected static final String UUID_02 = "uuid02";
    protected static final Resume RESUME_2 = Resume.builder()
            .withUuid(UUID_02)
            .withFullName("B Name 2")
            .build();

    protected static final String UUID_03 = "uuid03";
    protected static final Resume RESUME_3 = Resume.builder()
            .withUuid(UUID_03)
            .withFullName("A Name 3")
            .build();


    protected static final String UUID_04 = "uuid04";
    protected static final Resume NON_EXISTENT_RESUME = Resume.builder()
            .withUuid(UUID_04)
            .withFullName("Name 4")
            .build();

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void init() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clearShouldFillStorageWithNullAndMakeSizeZero() {
        storage.clear();
        assertSizeEqualsZero();
    }

    protected void assertSizeEqualsZero() {
        int expected = 0;
        int actual = storage.size();

        assertEquals(expected, actual);
    }

    @Test
    void updateShouldUpdateResumeIfResumeExistsInStorage() {
        Resume newResume = Resume.builder()
                .withUuid(UUID_01)
                .withFullName("New name")
                .build();

        storage.update(newResume);

        assertSame(newResume, storage.get(UUID_01));
    }

    @Test
    void updateShouldThrowIfResumeDoesNotExistInStorage() {
        Executable executable = () -> storage.update(NON_EXISTENT_RESUME);

        assertThrows(NotExistStorageException.class, executable);
    }

    @Test
    void saveShouldAddResumeInStorageIfResumeNotExist() {
        assertResumeEqualsWhenSaved();
    }

    protected void assertResumeEqualsWhenSaved() {
        Resume expectedResume = Resume.builder()
                .withUuid(UUID_04)
                .withFullName("Name 4")
                .build();

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
        List<Resume> expected = Arrays.asList(RESUME_3, RESUME_2, RESUME_1);
        List<Resume> actual = storage.getAllSorted();

        assertEquals(expected, actual);
    }

    @Test
    void sizeShouldReturnNumberOfResumesInStorage() {
        int expected = 3;
        int actual = storage.size();

        assertEquals(expected, actual);
    }
}
