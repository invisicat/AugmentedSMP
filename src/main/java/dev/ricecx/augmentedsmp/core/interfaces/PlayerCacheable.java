package dev.ricecx.augmentedsmp.core.interfaces;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.utils.PlayerCachingManager;
import org.bukkit.entity.Player;

public interface PlayerCacheable {

    PlayerCachingManager manager = AugmentedSMP.getInstance().getCachingManager();

    default <T> T getCache(Player player, String key, T defaultz, Class<? extends T> clazz) {
        return manager.get(player, key, defaultz, clazz);
    }

    default void setCache(Player player, String key, Object value) {
        manager.set(player, key, value);
    }

}
