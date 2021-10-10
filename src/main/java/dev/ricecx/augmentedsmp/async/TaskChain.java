package dev.ricecx.augmentedsmp.async;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskChain {
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public void runAtFixedRate(Task task, int initialDelay, int period, TimeUnit unit) {

    }

    interface Task {
        void run();
    }
}
