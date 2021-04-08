package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.*;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {

//    private final Storage arrayStorage;

    private final Resume resume1 = new Resume("uuid01");
    private final Resume resume2 = new Resume("uuid02");
    private final Resume resume3 = new Resume("uuid03");

//    public AbstractArrayStorageTest() {
////        this.arrayStorage = arrayStorage;
//    }

    @BeforeEach
    void init(Storage arrayStorage) {
        Resume resume1 = new Resume("uuid01");
        Resume resume2 = new Resume("uuid02");
        Resume resume3 = new Resume("uuid03");
        arrayStorage.clear();
        arrayStorage.save(resume1);
        arrayStorage.save(resume2);
        arrayStorage.save(resume3);
    }

    @Test
    void clearShouldFillArrayWithNullAndMakeSizeZero(Storage arrayStorage) {
        Resume[] expectedResumes = new Resume[0];
        arrayStorage.clear();
        Resume[] actualResumes = arrayStorage.getAll();

        int expectedSize = 0;
        int actualSize = arrayStorage.size();

        assertAll(
                () -> assertArrayEquals(expectedResumes, actualResumes),
                () -> assertEquals(expectedSize, actualSize)
        );
    }

    @Disabled
    @Test
    void update() {
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