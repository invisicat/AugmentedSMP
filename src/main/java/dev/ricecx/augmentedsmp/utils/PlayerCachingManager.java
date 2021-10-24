package dev.ricecx.augmentedsmp.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerCachingManager {

    private final HashMap<UUID, HashMap<String, Object>> cache = new HashMap<>();

    public void set(Player player, String key, Object value) {
        var map = cache.getOrDefault(player.getUniqueId(), new HashMap<>());
        map.put(key, value);
        cache.put(player.getUniqueId(), map);
    }
    public <T> T get(Player player, String key, T defaultz, Class<? extends T> clazz) {
        var map = cache.getOrDefault(player.getUniqueId(), new HashMap<>());

        return clazz.cast(map.getOrDefault(key, defaultz));
    }

    public <T> T get(Player player, String key, Class<? extends T> clazz) {
        var map = cache.getOrDefault(player.getUniqueId(), new HashMap<>());

        return clazz.cast(map.get(key));
    }

    public HashMap<String, Object> getMap(Player player) {
        return cache.getOrDefault(player.getUniqueId(), new HashMap<>());
    }


    public void remove(Player player) {
        cache.remove(player.getUniqueId());
    }

}
