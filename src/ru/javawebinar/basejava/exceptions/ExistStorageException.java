package ru.javawebinar.basejava.exceptions;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid, String message) {
        super(uuid, message);
    }

    public ExistStorageException(String uuid, String message, Exception e) {
        super(uuid, message, e);
    }
}
