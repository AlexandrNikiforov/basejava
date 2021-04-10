package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.*;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {

    private final Storage storage;

    private final Resume resume1 = new Resume("uuid01");
    private final Resume resume2 = new Resume("uuid02");
    private final Resume resume3 = new Resume("uuid03");

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

        assertEquals(expected,actual);
    }

    @Disabled
    @Test
    void save() {
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