package dev.ricecx.augmentedsmp.modules.ambientparticles;

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
        icon = Material.ENDER_PEARL
)
public class AmbientParticlePlayerConfig extends AbstractPlayerConfig {

    @Override
    public void onMenuOpen(Player player) {
        InventoryMenu menu = new ModuleMenu("Ambient Particle Settings", 3, this);

        PlayerCachingManager cachingManager = AugmentedSMP.getInstance().getCachingManager();

        menu.setButton(21, new InventoryToggleEnchantingButton(new ItemBuilder(Material.ENDER_EYE).setLore("&cWoah. Cool ambient particles!").toItemStack(), "Particles", false, (ch) -> {}));

        player.openInventory(menu.getInventory());
    }
}
