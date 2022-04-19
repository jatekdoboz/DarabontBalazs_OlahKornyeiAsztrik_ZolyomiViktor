package com.example.jatekhaboru6;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Szunet {
    private static final ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(10);

    public static void doPause(int ms) {
        try {
            scheduledThreadPoolExecutor.schedule(() -> {
            }, ms, TimeUnit.MILLISECONDS).get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
