package com.example.demo.dto;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Archiver {
    private static String archiveStatus = "Waiting";
    private static double archiveProgress = 0.0;
    private static Thread thread = null;
    private static final Random random = new Random();

    public static String getStatus() {
        return archiveStatus;
    }

    public static double getProgress() {
        return archiveProgress;
    }

    public static void run() {
        if ("Waiting".equals(archiveStatus)) {
            archiveStatus = "Running";
            archiveProgress = 0.0;
            thread = new Thread(() -> runImpl());
            thread.start();
        }
    }

    private static void runImpl() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep((long) (1 * random.nextDouble()));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            if (!"Running".equals(archiveStatus)) {
                return;
            }
            archiveProgress = (i + 1) / 10.0;
            System.out.println("Here... " + archiveProgress);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        if (!"Running".equals(archiveStatus)) {
            return;
        }
        archiveStatus = "Complete";
    }

    public static String archiveFile() {
        return "contacts.json";
    }

    public static void reset() {
        archiveStatus = "Waiting";
    }

    public static Archiver get() {
        return new Archiver();
    }
}
