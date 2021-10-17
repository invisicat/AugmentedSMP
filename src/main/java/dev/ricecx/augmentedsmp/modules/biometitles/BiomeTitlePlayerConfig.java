package dev.ricecx.augmentedsmp.modules.biometitles;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.inventory.InventoryMenu;
import dev.ricecx.augmentedsmp.core.inventory.types.InventoryToggleEnchantingButton;
import dev.ricecx.augmentedsmp.core.module.AbstractPlayerConfig;
import dev.ricecx.augmentedsmp.core.module.PlayerConfig;
import dev.ricecx.augmentedsmp.menu.ModuleMenu;
import dev.ricecx.augmentedsmp.utils.ItemBuilder;
import dev.ricecx.augmentedsmp.utils.PlayerCachingManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;


@PlayerConfig(
        icon = Material.RED_TULIP
)
public class BiomeTitlePlayerConfig extends AbstractPlayerConfig {

    @Override
    public void onMenuOpen(Player player) {
        InventoryMenu menu = new ModuleMenu("Biome Title Settings", 3, this);

        PlayerCachingManager cachingManager = AugmentedSMP.getInstance().getCachingManager();

        Boolean isSoundEnabled = cachingManager.get(player, BiomeTitleKeys.SOUND.getKey(), false, Boolean.class);
        Boolean isTitleEnabled = cachingManager.get(player, BiomeTitleKeys.TITLE.getKey(), true, Boolean.class);

        menu.setButton(21, new InventoryToggleEnchantingButton(new ItemBuilder(Material.NOTE_BLOCK).setLore(getSoundLore()).toItemStack(), "Sound", isSoundEnabled, (ch) -> cachingManager.set(player, BiomeTitleKeys.SOUND.getKey(), ch)));

        menu.setButton(23, new InventoryToggleEnchantingButton(new ItemBuilder(Material.LEVER).toItemStack(), "Title", isTitleEnabled, (ch) -> cachingManager.set(player, BiomeTitleKeys.TITLE.getKey(), ch)));

        player.openInventory(menu.getInventory());
    }

    private String getSoundLore() {
        return "Toggle the enchanting sound when you've found a new Biome.";
    }
}