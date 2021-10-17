package dev.ricecx.augmentedsmp.core.inventory.types;

import dev.ricecx.augmentedsmp.utils.ItemBuilder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class InventoryToggleEnchantingButton extends InventoryToggleButton {


    public InventoryToggleEnchantingButton(ItemStack item, String name, Boolean defaultStatus) {
        this(item, name, defaultStatus, null);
    }

    public InventoryToggleEnchantingButton(ItemStack item, String name, Boolean defaultStatus, Consumer<Boolean> onChange) {
        super(new ItemBuilder(item).setName("&aEnable " + name).toItemStack(), new ItemBuilder(item).addEnchantGlow(Enchantment.DAMAGE_ALL, 1).setName("&cDisable " + name).toItemStack(), defaultStatus, onChange);
    }
    public InventoryToggleEnchantingButton(ItemStack item, Boolean defaultStatus) {
        this(item, defaultStatus, null);
    }

    public InventoryToggleEnchantingButton(ItemStack item, Boolean defaultStatus, Consumer<Boolean> onChange) {

        super(item, new ItemBuilder(item).addEnchantGlow(Enchantment.DAMAGE_ALL, 1).toItemStack(), defaultStatus, onChange);
    }
}
