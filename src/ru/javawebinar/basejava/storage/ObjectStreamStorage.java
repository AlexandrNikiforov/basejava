package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectStreamStorage extends AbstractFileStorage {

    public ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        Resume resume;
        try (ObjectInputStream ois = new ObjectInputStream(is)) {

            resume = (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException(null, "Cannot read file", e);
        }
        return resume;
    }


    @Override
    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }
}
