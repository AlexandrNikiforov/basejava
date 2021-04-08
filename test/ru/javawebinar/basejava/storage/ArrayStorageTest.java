package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Executable;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStorageTest extends AbstractArrayStorageTest {

    private static final Storage arrayStorage = new ArrayStorage();

    @Override
    @BeforeEach
    void init(Storage arrayStorage) {
        super.init(arrayStorage);
    }

    @Override
    @Test
    void clearShouldFillArrayWithNullAndMakeSizeZero(Storage arrayStorage) {
        super.clearShouldFillArrayWithNullAndMakeSizeZero(arrayStorage);
    }

    @Disabled
    @Test
    void updateShouldThrowNotExistStorageExceptionWhenNoSuchResume() {
        Resume resumeNotExistedInStorage = new Resume("new_uuid");

//        Executable executable = () -> arrayStorage.update(resumeNotExistedInStorage);
    }

}
