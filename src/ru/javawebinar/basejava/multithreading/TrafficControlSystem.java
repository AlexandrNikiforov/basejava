package ru.javawebinar.basejava.multithreading;

import java.util.Random;

/**
 * Conditions for Deadlock:
 * <p>
 * - Mutual Exclusion - Only one thread can have exclusive
 * access to a resource;
 * <p>
 * - Hold and Wait - At least one thread is holding a resource and
 * is waiting for another resource
 * <p>
 * - Non-preemptive allocation - A resource is released only after the thread
 * is done using it
 * <p>
 * -  Circular wait - A chain  of at least two threads each one is holding
 * one resource and waiting for another resource
 */

public class TrafficControlSystem {

    public static void main(String[] args) {
        Intersection intersection = new Intersection();

        Thread car1 = new Thread(new Car1(intersection));
        car1.setName("Car1");
        car1.start();
        Thread car2 = new Thread(new Car2(intersection));
        car2.setName("Car2");
        car2.start();
    }

    private static class Car1 implements Runnable {
        private Intersection intersection;
        private Random random;

        public Car1(Intersection intersection) {
            this.intersection = intersection;
            this.random = new Random();
        }

        @Override
        public void run() {
            while (true) {
                long sleepTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intersection.takeRoad1();
            }
        }
    }

    private static class Car2 implements Runnable {
        private Intersection intersection;
        private Random random;

        public Car2(Intersection intersection) {
            this.intersection = intersection;
            this.random = new Random();
        }

        @Override
        public void run() {
            while (true) {
                long sleepTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intersection.takeRoad2();
            }
        }
    }

    private static class Intersection {
        private Object road1 = new Object();
        private Object road2 = new Object();

        public void takeRoad1() {
            synchronized (road1) {
                System.out.println("The road1 was blocked by the car " + Thread.currentThread().getName());
                synchronized (road2) {
                    System.out.println(Thread.currentThread().getName() + " is passing through road1");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void takeRoad2() {
            synchronized (road2) {
                System.out.println("The road2 was blocked by the car " + Thread.currentThread().getName());
                synchronized (road1) {
                    System.out.println(Thread.currentThread().getName() + " is passing through road2");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
