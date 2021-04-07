package ru.javawebinar.basejava.exceptions;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid, String message) {
        super(uuid, message);
    }
}
