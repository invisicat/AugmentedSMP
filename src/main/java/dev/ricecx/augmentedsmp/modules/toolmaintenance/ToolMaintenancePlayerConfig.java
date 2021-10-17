package dev.ricecx.augmentedsmp.modules.toolmaintenance;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.inventory.types.InventoryToggleEnchantingButton;
import dev.ricecx.augmentedsmp.core.inventory.types.InventoryToggleGlassButton;
import dev.ricecx.augmentedsmp.core.module.AbstractPlayerConfig;
import dev.ricecx.augmentedsmp.core.module.PlayerConfig;
import dev.ricecx.augmentedsmp.menu.ModuleMenu;
import dev.ricecx.augmentedsmp.utils.ItemBuilder;
import dev.ricecx.augmentedsmp.utils.PlayerCachingManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;


@PlayerConfig(
        icon = Material.LAVA_BUCKET
)
public class ToolMaintenancePlayerConfig extends AbstractPlayerConfig {

    @Override
    public void onMenuOpen(Player player) {
        ModuleMenu menu = new ModuleMenu("Tool Maintenance Module", 3, this);

        PlayerCachingManager cachingManager = AugmentedSMP.getInstance().getCachingManager();

        Boolean isEnabled = cachingManager.get(player, "dtq-enabled", false, Boolean.class);
        Boolean isDurabilityEnabled = cachingManager.get(player, "dur-sound", false, Boolean.class);

        menu.setButton(13, new InventoryToggleEnchantingButton(new ItemBuilder(Material.DIAMOND_PICKAXE).toItemStack(),"Double Tap Drop", isEnabled, b -> cachingManager.set(player, "dtq-enabled", b)));
        menu.setButton(15, new InventoryToggleEnchantingButton(new ItemBuilder(Material.GOLDEN_SHOVEL).toItemStack(),"Low Durability Warning", isDurabilityEnabled, b -> cachingManager.set(player, "dur-sound", b)));

        player.openInventory(menu.getInventory());
    }
}
