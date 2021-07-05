package ru.javawebinar.basejava.exceptions;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String uuid, String message) {
        super(uuid, message);
    }

    public NotExistStorageException(String uuid, String message, Exception e) {
        super(uuid, message, e);
    }

    public NotExistStorageException(String uuid) {
        super(uuid);
    }
}
