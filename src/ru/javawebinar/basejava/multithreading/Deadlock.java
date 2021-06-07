package ru.javawebinar.basejava.multithreading;

public class Deadlock {

    private static final Object roadA = new Object();
    private static final Object roadB = new Object();

    public static void main(String[] args) {
        Car carA = new Car(roadA, roadB);
        Car carB = new Car(roadB, roadA);
        carA.start();
        carB.start();
        try {
            carA.join();
            carB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class Car extends Thread {
        private Object lock1;
        private Object lock2;

        public Car(Object lock1, Object lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            crossTheRoad(lock1, lock2);
        }

        private void crossTheRoad(Object lock1, Object lock2) {
            synchronized (lock1) {
                System.out.println("CarA is driving along the road A");
                synchronized (lock2) {
                    System.out.println("CarA crossed the roadB");
                }
            }
        }
    }
}
