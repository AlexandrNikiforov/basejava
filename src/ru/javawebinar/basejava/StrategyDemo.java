package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SerializationType;
import ru.javawebinar.basejava.storage.Serializer;

import java.io.File;

public class StrategyDemo {
    protected static final File STORAGE_DIR = new File("/home/nikiforov-java/workspace/javaops/basejava/storage");
    protected static final String UUID_01 = "StrategyTest01";
    protected static final String UUID_02 = "StrategyTest02";
    protected static final Resume RESUME_1 = ResumeTestData.createResume(UUID_01, "fullName1");
    protected static final Resume RESUME_2 = ResumeTestData.createResume(UUID_02, "fullName2");


    public static void main(String[] args) {
        saveResumeWithSerializationTechnology(SerializationType.FILE, RESUME_1);
        saveResumeWithSerializationTechnology(SerializationType.PATH, RESUME_2);
    }

    public static void saveResumeWithSerializationTechnology(SerializationType serializationType, Resume resume) {
        Serializer serializer = new Serializer();
        serializer.chooseSerializationTechnology(serializationType, STORAGE_DIR.toString());
        serializer.save(resume);
    }
}
