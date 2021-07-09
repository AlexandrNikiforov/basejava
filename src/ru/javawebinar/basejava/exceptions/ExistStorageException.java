package ru.javawebinar.basejava.exceptions;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid, String message) {
        super(uuid, message);
    }

    public ExistStorageException(String uuid, String message, Exception e) {
        super(uuid, message, e);
    }

    public ExistStorageException(String message, Exception e) {
        super(null, message, e);
    }

    public ExistStorageException(String uuid) {
        super(uuid, null, null);
    }
}
