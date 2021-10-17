package dev.ricecx.augmentedsmp.core;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public enum Dependencies {
    WORLDEDIT("WorldEdit");

    private final String name;
    private boolean enabled = false;
    private boolean available;

    Dependencies(String pluginName) {
        this.name = pluginName;
        this.available = false;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
