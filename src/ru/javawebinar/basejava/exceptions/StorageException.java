package ru.javawebinar.basejava.exceptions;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String uuid, String message) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String uuid, String message, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }
}
