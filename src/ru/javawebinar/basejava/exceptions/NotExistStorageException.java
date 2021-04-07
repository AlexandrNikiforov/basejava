package ru.javawebinar.basejava.exceptions;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String uuid, String message) {
        super(uuid, message);
    }
}
