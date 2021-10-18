package dev.ricecx.augmentedsmp.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AugmentedPlayerJoinEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private String joinMessage;

    public AugmentedPlayerJoinEvent(@NotNull Player playerJoined, @Nullable String joinMessage) {
        super(playerJoined);
        this.joinMessage = joinMessage;
    }

    @Nullable
    public String getJoinMessage() {
        return this.joinMessage;
    }

    public void setJoinMessage(@Nullable String joinMessage) {
        this.joinMessage = joinMessage;
    }

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
