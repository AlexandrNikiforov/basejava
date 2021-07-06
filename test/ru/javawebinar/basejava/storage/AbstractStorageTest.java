package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractStorageTest {

    protected final Storage storage;
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected static final String UUID_01 = UUID.randomUUID().toString();
    protected static final Resume RESUME_1 = ResumeTestData.createResume(UUID_01, "C Name 1");

    protected static final String UUID_02 = UUID.randomUUID().toString();;
    protected static final Resume RESUME_2 = ResumeTestData.createResume(UUID_02, "B Name 2");

    protected static final String UUID_03 = UUID.randomUUID().toString();;
    protected static final Resume RESUME_3 = ResumeTestData.createResume(UUID_03, "A Name 3");

    protected static final String UUID_04 = UUID.randomUUID().toString();;
    protected static final Resume NON_EXISTENT_RESUME = ResumeTestData.createResume(UUID_04, "Name 4");

    protected static final String UUID_05 = UUID.randomUUID().toString();;
    protected static final Resume NON_EXISTENT_RESUME2 = ResumeTestData.createResume(UUID_05, "Name 5");

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
    void updateShouldUpdateResumeIfResumeExistsInStorage() {
        Resume newResume = ResumeTestData.createResume(UUID_01, "New name");

        storage.update(newResume);

        assertEquals(newResume, storage.get(UUID_01));
    }

    protected void assertSizeEqualsZero() {
        int expected = 0;
        int actual = storage.size();

        assertEquals(expected, actual);
    }

    @Test
    void updateShouldThrowIfResumeDoesNotExistInStorage() {
        Executable executable = () -> storage.update(NON_EXISTENT_RESUME);

        assertThrows(NotExistStorageException.class, executable);
    }

    @Test
    void clearShouldFillStorageWithNullAndMakeSizeZero() {
        storage.clear();
        assertSizeEqualsZero();
    }

    @Test
    void saveShouldAddResumeInStorageIfResumeNotExist() {
        assertResumeEqualsWhenSaved();
    }

    protected void assertResumeEqualsWhenSaved() {
        Resume expectedResume = ResumeTestData.createResume(UUID_04, "Name 4");

        storage.save(NON_EXISTENT_RESUME);
        Resume actualResume = storage.get(expectedResume.getUuid());

        assertEquals(expectedResume, actualResume);
    }

    @Test
    void saveShouldThrowWhenResumeExistsInStorage() {
        Executable executable = () -> storage.save(RESUME_1);

        assertThrows(StorageException.class, executable);
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
    void deleteShouldThrowWhenResumeDoesNotExistInStorage() {
        Executable executable = () -> storage.delete(NON_EXISTENT_RESUME2.getUuid());

        assertThrows(NotExistStorageException.class, executable);
    }

    @Test
    void getAllShouldReturnArrayOfExistedResumes() {
//        List<Resume> expected = Arrays.asList(RESUME_3, RESUME_2, RESUME_1);
        List<Resume> expected = Stream.of(RESUME_3, RESUME_2, RESUME_1).collect(Collectors.toCollection(ArrayList::new));
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
