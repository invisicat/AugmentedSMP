package dev.ricecx.augmentedsmp.core.event;

import com.google.common.collect.Maps;
import dev.ricecx.augmentedsmp.utils.LoggingUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class EventManager {

    private final int maxTimeout;
    private final TimeUnit timeUnit;
    private final Map<Eventable<?>, Long> futureMap = Maps.newConcurrentMap();

    public EventManager() {
        this(5, TimeUnit.MINUTES);
    }
    /**
     * @deprecated Use {@link #EventManager(int, TimeUnit)} instead.
     * @param maxTimeout max time out of all events
     */
    public EventManager(int maxTimeout) {
        this(maxTimeout, TimeUnit.SECONDS);
    }
    public EventManager(int maxTimeout, TimeUnit unit) {
        this.maxTimeout = maxTimeout;
        this.timeUnit = unit;
    }

    public <T> Eventable<T> createEvent(int maxTimeout, TimeUnit unit) {
        var e = new Eventable<T>(new CompletableFuture<>());
        futureMap.put(e, unit.toMillis(maxTimeout));

        e.getFuture().orTimeout(maxTimeout, unit).whenComplete((c, v) -> removeEvent(e));

        return e;
    }

    private void removeEvent(Eventable<?> event) {
        for (BukkitRunnable bukkitRunnable : event.getBukkitRunnables()) {
            bukkitRunnable.cancel();
        }
        for (CompletableFuture<?> future : event.getFutures()) {
            future.cancel(true);
        }
        LoggingUtils.info("Removed event " + event);
        futureMap.remove(event);
    }


    public int getMaxTimeout() {
        return maxTimeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public Map<Eventable<?>, Long> getFutureMap() {
        return futureMap;
    }
}
