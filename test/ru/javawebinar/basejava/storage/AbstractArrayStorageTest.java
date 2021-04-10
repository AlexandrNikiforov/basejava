package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import ru.javawebinar.basejava.exceptions.ExistStorageException;
import ru.javawebinar.basejava.exceptions.NotExistStorageException;
import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {

    private final Storage storage;

    private final Resume resume1 = new Resume("uuid01");
    private final Resume resume2 = new Resume("uuid02");
    private final Resume resume3 = new Resume("uuid03");
    private final Resume resumeNotExistInStorage = new Resume("uuid4");

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
        Resume actual = storage.get("updated_uuid02");

        assertEquals(expected, actual);
    }

    @Test
    void updateShouldThrowIfResumeDoesNotExistInStorage() {
        Executable executable = () -> storage.update(resumeNotExistInStorage);
        String expectedMessage = AbstractArrayStorage.ERROR_TEXT_NO_SUCH_RESUME + resumeNotExistInStorage.getUuid();

        Throwable exceptionThatWasThrown = assertThrows(NotExistStorageException.class, executable);
        assertEquals(expectedMessage, exceptionThatWasThrown.getMessage());
    }

    @Test
    void saveShouldAddResumeInStorageIfResumeNotExistAndStorageIsNotFull() {
        Resume expected = new Resume("uuid4");
        storage.save(resumeNotExistInStorage);
        Resume actual = storage.get("uuid4");

        assertEquals(expected, actual);
    }

    @Test
    void saveShouldThrowIfResumeExistsInStorage() {
        Executable executable = () -> storage.save(resume1);
        String expectedMessage = AbstractArrayStorage.ERROR_TEXT_RESUME_IS_ALREADY_IN_STORAGE
                + resume1.getUuid();

        Throwable exceptionThatWasThrown = assertThrows(ExistStorageException.class, executable);
        assertEquals(expectedMessage, exceptionThatWasThrown.getMessage());
    }

    @Test
    void saveShouldThrowWhenStorageOverflow() {
        for(int i = 3; i < AbstractArrayStorage.STORAGE_CAPACITY; i++) {
            try {
                Resume newResume = new Resume("uuid" + i + 1);
                storage.save(newResume);
            } catch (RuntimeException e) {
                fail("An exception was thrown before storage was overflowed");
            }
        }
        Resume resume10000 = new Resume("uuid10000");
        Executable executable = () -> storage.save(resume10000);
        String expectedMessage = AbstractArrayStorage.ERROR_TEXT_STORAGE_OUT_OF_SPACE;

        Throwable exceptionThatWasThrown = assertThrows(StorageException.class, executable);
        assertEquals(expectedMessage, exceptionThatWasThrown.getMessage());
    }


    @Disabled
    @Test
    void get() {
    }

    @Disabled
    @Test
    void delete() {
    }

    @Disabled
    @Test
    void getAll() {
    }

    @Disabled
    @Test
    void size() {
    }
}