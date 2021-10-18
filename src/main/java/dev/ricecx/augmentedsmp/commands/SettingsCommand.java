package dev.ricecx.augmentedsmp.commands;

import dev.ricecx.augmentedsmp.core.command.CommandCategory;
import dev.ricecx.augmentedsmp.core.command.ICommand;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;
import dev.ricecx.augmentedsmp.core.inventory.InventoryButton;
import dev.ricecx.augmentedsmp.core.inventory.InventoryMenu;
import dev.ricecx.augmentedsmp.core.module.AbstractPlayerConfig;
import dev.ricecx.augmentedsmp.modules.Modules;
import dev.ricecx.augmentedsmp.utils.DurationFormatter;
import dev.ricecx.augmentedsmp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(
        name = "settings",
        description = "Opens up the settings GUI",
        usage = "settings",
        category = CommandCategory.PLAYER
)
public class SettingsCommand implements ICommand {

    private static long STIME = System.currentTimeMillis();
    private final int[] moduleSlots = new int[]{
            19, 21, 23, 25,
            28, 30, 32, 34
    };

    @Override
    public void run(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) return;
        InventoryMenu menu = new InventoryMenu("AugmentedSMP Settings", 6, "asmp-settings");

        for (int i = 0; i < 9; i++) {
            menu.setButton(i, new InventoryButton(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        }
        for (int i = 45; i < 54; i++) {
            menu.setButton(i, new InventoryButton(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        }

        for (Modules module : Modules.values()) {
            AbstractPlayerConfig cfg = module.getModule().getPlayerConfig();

            if (cfg != null)
                menu.addButtonsToNextAvailable(new InventoryButton(new ItemBuilder(cfg.getConfig().icon())
                        .setName("&e" + module.getModule().getName())
                        .setLore(module.getModule().getDescription())
                        .toItemStack(), (clicked) -> cfg.onMenuOpen(((Player) sender))), moduleSlots);
        }

        menu.setButton(13, new InventoryButton(new ItemBuilder(Material.PLAYER_HEAD).setName("&e❉ " + sender.getName() + " ❉").setLore(getPlayerDescription((Player) sender)).setSkullOwnerBukkit(((Player) sender)).toItemStack())
                .setUpdateHandler((i) -> i.setIcon(new ItemBuilder(Material.PLAYER_HEAD).setName("&e❉ " + sender.getName() + " ❉").setLore(getPlayerDescription((Player) sender)).setSkullOwnerBukkit(((Player) sender)).toItemStack())));

        ((Player) sender).openInventory(menu.getInventory());
    }

    private String[] getPlayerDescription(Player player) {
        return new String[]{
                "",
                "&8&l→ &r&7Levels: &r&e30",
                "&8&l→ &7Next Daily: &r&e" + DurationFormatter.CONCISE.formatMillis(STIME - System.currentTimeMillis())
        };
    }
}
