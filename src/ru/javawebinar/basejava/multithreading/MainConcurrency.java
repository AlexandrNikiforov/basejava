package ru.javawebinar.basejava.multithreading;

public class MainConcurrency {
    private static int counter;
    public static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName());
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", "
                + Thread.currentThread().getState())).start();

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
            }).start();
        }

        Thread.sleep(500);
        System.out.println(counter);
    }

    private static void inc() {
        double a = Math.sin(13.);

        synchronized (LOCK) {
            counter++;
        }
    }


}
