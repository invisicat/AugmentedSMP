package dev.ricecx.augmentedsmp.core.inventory.types;

import dev.ricecx.augmentedsmp.core.inventory.InventoryButton;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class InventoryToggleButton extends InventoryButton {
    private boolean status;
    private final ItemStack itemTrue;
    private final ItemStack itemFalse;

    public InventoryToggleButton(ItemStack itemFalse, ItemStack itemTrue, Boolean defaultStatus, Consumer<Boolean> onChange) {
        super(defaultStatus ? itemTrue : itemFalse);

        this.status = defaultStatus;
        this.itemFalse = itemFalse;
        this.itemTrue = itemTrue;
        super.setListener((e) -> {
            this.status = !status;
            this.setIcon(getMaterial(status));
            if(onChange != null) onChange.accept(status);
        });
    }

    private ItemStack getMaterial(boolean bool) {
        return bool ? itemTrue : itemFalse;
    }
}
