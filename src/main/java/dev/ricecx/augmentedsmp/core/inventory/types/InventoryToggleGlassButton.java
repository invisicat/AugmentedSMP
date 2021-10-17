package dev.ricecx.augmentedsmp.core.inventory.types;

import dev.ricecx.augmentedsmp.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.function.Consumer;

public class InventoryToggleGlassButton extends InventoryToggleButton {


    public InventoryToggleGlassButton(String name, Boolean defaultStatus, Consumer<Boolean> onChange) {
        super(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName("&aEnable " + name).toItemStack(), new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("&cDisable " + name).toItemStack(), defaultStatus, onChange);
    }
}
