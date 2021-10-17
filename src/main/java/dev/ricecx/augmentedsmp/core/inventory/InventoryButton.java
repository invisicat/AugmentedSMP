package dev.ricecx.augmentedsmp.core.inventory;

import org.bukkit.inventory.ItemStack;

public class InventoryButton {

    private InventoryButtonListener listener;
    private ItemStack icon;

    public InventoryButton(ItemStack icon) {
        this.icon = icon;
    }

    public InventoryButton(ItemStack icon, InventoryButtonListener listener) {
        this.icon = icon;
        this.listener = listener;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public void setListener(InventoryButtonListener listener) {
        this.listener = listener;
    }

    public InventoryButtonListener getListener() {
        return listener;
    }

    public ItemStack getIcon() {
        return icon;
    }
}
