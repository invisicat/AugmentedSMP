package dev.ricecx.augmentedsmp.structures.data;

import org.bukkit.Location;

public record Structure(String name, String schematicFile) {

    public void spawn(Location loc) {}
}
