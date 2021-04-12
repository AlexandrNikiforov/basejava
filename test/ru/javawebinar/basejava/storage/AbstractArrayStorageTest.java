package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;

abstract class AbstractArrayStorageTest {

    private final Storage storage;

    private final Resume resume1 = new Resume("uuid01");
    private final Resume resume2 = new Resume("uuid02");
    private final Resume resume3 = new Resume("uuid03");
    private final Resume nonexistentResume = new Resume("uuid4");

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void init() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    void clearShouldFillArrayWithNullAndMakeSizeZero() {
        Resume[] expectedResumes = new Resume[0];
        storage.clear();
        Resume[] actualResumes = storage.getAll();

        int expectedSize = 0;
        int actualSize = storage.size();

        assertAll(
                () -> assertArrayEquals(expectedResumes, actualResumes),
                () -> assertEquals(expectedSize, actualSize)
        );
    }

    @Test
    void updateShouldUpdateResumeIfResumeExistsInStorage() {
        resume2.setUuid("updated_uuid02");

        Resume expected = new Resume("updated_uuid02");
        storage.update(resume2);
        Resume actual = storage.get(expected.getUuid());

        assertEquals(expected, actual);
    }

    @Test
    void updateShouldThrowIfResumeDoesNotExistInStorage() {
        Executable executable = () -> storage.update(nonexistentResume);

        assertThrows(NotExistStorageException.class, executable);
    }

    @Test
    void saveShouldAddResumeInStorageIfResumeNotExist() {
        Resume expectedResume = new Resume("uuid4");
        storage.save(nonexistentResume);
        Resume actualResume = storage.get(expectedResume.getUuid());

        int expectedSize = 4;
        int actualSize = storage.size();

        assertAll(
                () -> assertEquals(expectedResume, actualResume),
                () -> assertEquals(expectedSize, actualSize)
        );
    }

    @Test
    void saveShouldThrowWhenResumeExistsInStorage() {
        Executable executable = () -> storage.save(resume1);

        assertThrows(ExistStorageException.class, executable);
    }

    @Test
    void saveShouldThrowWhenStorageOverflow() {
        for (int i = 3; i < AbstractArrayStorage.STORAGE_CAPACITY; i++) {
            try {
                Resume newResume = new Resume("uuid" + i + 1);
                storage.save(newResume);
            } catch (RuntimeException e) {
                fail("An exception was thrown before storage was overflowed");
            }
        }
        Resume resume10000 = new Resume("uuid10000");
        Executable executable = () -> storage.save(resume10000);

        assertThrows(StorageException.class, executable);
    }

    @Test
    void getShouldReturnResumeWithGivenUuidWhenResumeExistsInStorage() {
        Resume expected = resume2;
        Resume actual = storage.get(resume2.getUuid());

        assertEquals(expected, actual);
    }

    @Test
    void getShouldThrowIfResumeDoesNotExistInStorage() {
        Executable executable = () -> storage.get(nonexistentResume.getUuid());

        assertThrows(NotExistStorageException.class, executable);
    }

    @Test
    void deleteShouldRemoveResumeWhenResumeExistsInStorage() {
        storage.delete(resume2.getUuid());
        Executable executable = () -> storage.get(resume2.getUuid());

        int expectedSize = 2;
        int actualSize = storage.size();

        assertAll(
                () -> assertThrows(NotExistStorageException.class, executable),
                () -> assertEquals(expectedSize, actualSize));
    }

    @Test
    void deleteShouldThrowWhenResumeExistsInStorage() {
        Executable executable = () -> storage.delete(nonexistentResume.getUuid());

        assertThrows(NotExistStorageException.class, executable);
    }

    @Test
    void getAllShouldReturnArrayOfExistedResumes() {
        Resume[] expected = {resume1, resume2, resume3};
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
