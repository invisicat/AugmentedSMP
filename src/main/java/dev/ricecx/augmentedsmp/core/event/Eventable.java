package dev.ricecx.augmentedsmp.core.event;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Eventable<T> {

    private final CompletableFuture<T> future;
    private final List<BukkitRunnable> bukkitRunnables = new ArrayList<>();
    private final List<CompletableFuture<?>> futures = new ArrayList<>();

    public Eventable(CompletableFuture<T> future) {
        this.future = future;
    }


    public boolean isCancelled() {
        return future.isDone() || future.isCancelled();
    }


    public void onTimeout() {}
    public BukkitRunnable addRunnable(BukkitRunnable runnable) {
        bukkitRunnables.add(runnable);

        return runnable;
    }

    public BukkitRunnable addRunnable(Consumer<BukkitRunnable> c) {
        return addRunnable(new BukkitRunnable() {
            @Override
            public void run() {
                c.accept(this);
            }
        });
    }

    public <V> CompletableFuture<V> createFuture() {
        return addFuture(new CompletableFuture<>());
    }

    public <V> CompletableFuture<V> addFuture(CompletableFuture<V> future) {
        futures.add(future);

        return future;
    }

    public void complete(T value) {
        future.complete(value);
    }

    public List<CompletableFuture<?>> getFutures() {
        return futures;
    }

    public List<BukkitRunnable> getBukkitRunnables() {
        return bukkitRunnables;
    }

    public CompletableFuture<T> getFuture() {
        return future;
    }
}
