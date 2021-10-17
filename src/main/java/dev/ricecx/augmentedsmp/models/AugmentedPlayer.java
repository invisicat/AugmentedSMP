package dev.ricecx.augmentedsmp.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AugmentedPlayer {
    private final UUID id;
    private final Player player;

    public AugmentedPlayer(UUID id) {
        this(id, Bukkit.getPlayer(id));
    }

    public AugmentedPlayer(UUID id, Player player) {
        this.id = id;
        this.player = player;
    }

    public UUID getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }
}
