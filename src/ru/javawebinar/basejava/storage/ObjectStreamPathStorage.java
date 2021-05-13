//package ru.javawebinar.basejava.storage;
//
//import ru.javawebinar.basejava.model.Resume;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class ObjectStreamPathStorage extends PathStorage implements SerializationTechnology {
//
//    public ObjectStreamPathStorage(String directory) {
//        super(directory);
//    }
//
//    @Override
//    public Resume doRead(InputStream is) throws IOException {
//        return SerializationTechnology.super.doRead(is);
//    }
//
//    @Override
//    public void doWrite(Resume resume, OutputStream os) throws IOException {
//        SerializationTechnology.super.doWrite(resume, os);
//    }
//
////    @Override
////    protected Resume doRead(InputStream is) throws IOException {
////        Resume resume;
////        try (ObjectInputStream ois = new ObjectInputStream(is)) {
////
////            resume = (Resume) ois.readObject();
////        } catch (ClassNotFoundException e) {
////            throw new StorageException(null, "Cannot read file", e);
////        }
////        return resume;
////    }
////
////
////    @Override
////    protected void doWrite(Resume resume, OutputStream os) throws IOException {
////        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
////            oos.writeObject(resume);
////        }
////    }
//}
