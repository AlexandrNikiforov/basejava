package ru.javawebinar.basejava.multithreading;

public class Deadlock {

    private static final Object roadA = new Object();
    private static final Object roadB = new Object();

    public static void main(String[] args) {
        CarA carA = new CarA();
        CarB carB = new CarB();
        carA.start();
        carB.start();
        try {
            carA.join();
            carB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class CarA extends Thread {

        @Override
        public void run() {
            synchronized (roadA) {
                System.out.println("CarA is driving along the road A");
                synchronized (roadB) {
                    System.out.println("CarA crossed the roadB");
                }
            }
        }
    }

    public static class CarB extends Thread {

        @Override
        public void run() {
            synchronized (roadB) {
                System.out.println("CarB is driving along the road B");
                synchronized (roadA) {
                    System.out.println("CarB crossed the road A");
                }
            }
        }
    }
}
