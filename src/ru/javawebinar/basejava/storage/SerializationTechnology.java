package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public interface SerializationTechnology {
    void save(Resume resume);
}
