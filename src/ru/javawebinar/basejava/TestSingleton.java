package ru.javawebinar.basejava;

public class TestSingleton {
    private static TestSingleton instance = new TestSingleton();

    private static TestSingleton getInstance() {
        return instance;
    }

// On demand implementation of singleton pattern. Doesn't work in
//apps with multithreading
//    private static TestSingleton getInstance() {
//        if (instance == null) {
//            instance = new TestSingleton();
//        }
//        return instance;
//    }

    private TestSingleton() {
    }
}
