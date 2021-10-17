package dev.ricecx.augmentedsmp.core.inventory.types;

import dev.ricecx.augmentedsmp.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.function.Consumer;

public class InventoryToggleGlassButton extends InventoryToggleButton {


    public InventoryToggleGlassButton(Boolean defaultStatus, Consumer<Boolean> onChange) {
        super(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).toItemStack(), new ItemBuilder(Material.RED_STAINED_GLASS_PANE).toItemStack(), defaultStatus, onChange);
    }
}
