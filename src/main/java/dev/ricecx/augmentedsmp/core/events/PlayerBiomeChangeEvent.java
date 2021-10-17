package dev.ricecx.augmentedsmp.core.events;

import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerBiomeChangeEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Biome previousBiome;
    private final Biome biome;

    public PlayerBiomeChangeEvent(@NotNull final Player player, Biome previousBiome, Biome biome) {
        super(player);
        this.previousBiome = previousBiome;
        this.biome = biome;
    }

    /**
     * Get the previous biome the player was in
     *
     * @return The previous biome
     */
    public Biome getPreviousBiome() {
        return previousBiome;
    }

    public Biome getBiome() {
        return biome;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
