package dev.ricecx.augmentedsmp.modules.joinevents;

import dev.ricecx.augmentedsmp.core.inventory.InventoryMenu;
import dev.ricecx.augmentedsmp.core.module.AbstractPlayerConfig;
import dev.ricecx.augmentedsmp.core.module.PlayerConfig;
import dev.ricecx.augmentedsmp.menu.ModuleMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;


@PlayerConfig(
        icon = Material.NETHER_STAR
)
public class JoinEventPlayerConfig extends AbstractPlayerConfig {
    @Override
    public void onMenuOpen(Player player) {
        InventoryMenu menu = new ModuleMenu("Join Settings", 3, this);

        player.openInventory(menu.getInventory());
    }
}
