package ru.javawebinar.basejava.multithreading;

public class Deadlock {

    private static final String lock1 = "lockA";
    private static final String lock2 = "lockB";

    public static void main(String[] args) {
        deadlock(lock1, lock2);
        deadlock(lock2, lock1);
    }

    private static void deadlock(Object lock1, Object lock2) {
        new Thread(() -> {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " has blocked " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " is waiting for " + lock2);
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " has blocked " + lock2);
                }
            }
        }).start();
    }
}
