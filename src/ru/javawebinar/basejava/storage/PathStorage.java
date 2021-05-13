package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exceptions.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    public AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory of is not writable");
        }
    }

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(String.valueOf(directory), uuid);
    }

    @Override
    protected void updateResumeInStorage(Resume resume, Path path) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(String.valueOf(path))));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
            doWrite(resume, new FileOutputStream(String.valueOf(path)));
        } catch (IOException e) {
            throw new StorageException(resume.getUuid(), "Couldn't create file " + path, e);
        }
        updateResumeInStorage(resume, path);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException(null, "Couldn't delete file " + path, e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Resume getFromStorage(Path path) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(String.valueOf(path))));
        } catch (IOException e) {
            throw new StorageException(path.getFileName().toString(), "File read error", e);
        }
    }

    @Override
    protected List<Resume> doCopy() {
        List<Resume> resumeList = new ArrayList<>();

        try {
            List<Path> files = Files.list(directory).collect(Collectors.toList());
            for (Path path : files) {
                Resume resume = doRead(new BufferedInputStream(new FileInputStream(String.valueOf(path))));
                    resumeList.add(resume);
            }
        } catch (IOException e) {
            throw new StorageException(null, "Cannot copy resume", e);
        }
        return resumeList;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException(null, "Path delete error", e);
        }
    }

    @Override
    public int size() {
        try {
            return Files.list(directory).collect(Collectors.toList()).size();
        } catch (IOException e) {
            throw new StorageException(null, "Couldn't read files");
        }
    }
}
