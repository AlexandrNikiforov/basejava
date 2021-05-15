//package ru.javawebinar.basejava.storage;
//
//import ru.javawebinar.basejava.model.Resume;
//
//public class Serializer {
//    private SerializationTechnology serializationTechnology;
//
//    public void chooseSerializationTechnology(SerializationType serializationType, String directory) {
//        this.serializationTechnology = serializationType.getSerializer(directory);
//    }
//
//    public void save(Resume resume) {
//        serializationTechnology.save(resume);
//    }
//}
