package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Override
    @Test
    void clearShouldFillStorageWithNullAndMakeSizeZero() {
        Resume[] expectedResumes = new Resume[0];

        storage.clear();
        Resume[] actualResumes = storage.getAll();

        assertAll(
                () -> assertArrayEquals(expectedResumes, actualResumes),
                () -> assertSizeEqualsZero()
        );
    }

    @Test
    void saveShouldAddResumeInStorageIfResumeNotExist() {
        assertAll(
                this::assertResumeEqualsWhenSaved,
                this::assertSizeEqualsWhenSaved
        );
    }

    protected void assertSizeEqualsWhenSaved() {
        int expectedSize = 4;
        int actualSize = storage.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void saveShouldThrowWhenResumeExistsInStorage() {
        Executable executable = () -> storage.save(RESUME_1);

        assertThrows(ExistStorageException.class, executable);
    }

    @Test
    void saveShouldThrowWhenStorageOverflow() {
        for (int i = 3; i < AbstractArrayStorage.STORAGE_CAPACITY; i++) {
            try {
                Resume newResume = new Resume();
                storage.save(newResume);
            } catch (StorageException e) {
                fail("An exception was thrown before storage was overflowed");
            }
        }
        Resume resume10001 = new Resume();
        Executable executable = () -> storage.save(resume10001);

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
