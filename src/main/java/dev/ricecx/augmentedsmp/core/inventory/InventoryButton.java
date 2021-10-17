package dev.ricecx.augmentedsmp.core.inventory;

import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class InventoryButton {

    private InventoryButtonListener listener;
    private Consumer<InventoryButton> updateHandler;
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

    public InventoryButton setUpdateHandler(Consumer<InventoryButton> handler) {
        this.updateHandler = handler;
        return this;
    }

    public Consumer<InventoryButton> getUpdateHandler() {
        return updateHandler;
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
