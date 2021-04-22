package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
    @DisplayName("Clear Should Fill Storage With Null And Make Size Zero")
    void clearShouldFillStorageWithNullAndMakeSizeZero() {
        Resume[] expectedResumes = new Resume[0];

        storage.clear();
        Resume[] actualResumes = storage.getAll();

        assertAll(
                () -> assertArrayEquals(expectedResumes, actualResumes),
                this::assertSizeEqualsZero
        );
    }

    @Override
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
}
