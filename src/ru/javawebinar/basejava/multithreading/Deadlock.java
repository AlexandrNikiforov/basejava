package ru.javawebinar.basejava.multithreading;

public class Deadlock {

    private static final Object roadA = new Object();
    private static final Object roadB = new Object();

    public static void main(String[] args) {
        Thread carA = new Thread(() -> crossTheRoad(roadA, roadB));
        Thread carB = new Thread(() -> crossTheRoad(roadB, roadA));
        carA.start();
        carB.start();
        try {
            carA.join();
            carB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void crossTheRoad(Object lock1, Object lock2) {
        synchronized (lock1) {
            System.out.println("Car is driving along the road");
            synchronized (lock2) {
                System.out.println("Car crossed the roadB");
            }
        }
    }
}
