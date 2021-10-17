package dev.ricecx.augmentedsmp.core;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.events.PlayerBiomeChangeEvent;
import dev.ricecx.augmentedsmp.utils.LoggingUtils;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class CoreEvent implements Listener {

    private final HashMap<UUID, String> playerBiomes = new HashMap<>();

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() == Objects.requireNonNull(e.getTo()).getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) return;

        Biome biome = e.getPlayer().getLocation().getBlock().getBiome();
        String biomeName = playerBiomes.getOrDefault(e.getPlayer().getUniqueId(), biome.name());

        if(!biome.name().equalsIgnoreCase(biomeName))
            AugmentedSMP.getInstance().callEvent(new PlayerBiomeChangeEvent(e.getPlayer(), Biome.valueOf(biomeName), biome));

        playerBiomes.put(e.getPlayer().getUniqueId(), biome.name());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
    }
}
