package dev.ricecx.augmentedsmp.menu;

import dev.ricecx.augmentedsmp.core.inventory.InventoryButton;
import dev.ricecx.augmentedsmp.core.inventory.InventoryMenu;
import dev.ricecx.augmentedsmp.core.module.AbstractPlayerConfig;
import dev.ricecx.augmentedsmp.utils.Constants;
import dev.ricecx.augmentedsmp.utils.ItemBuilder;
import org.bukkit.entity.Player;

public class ModuleMenu extends InventoryMenu {

    public ModuleMenu(String title, int rowsPerPage, AbstractPlayerConfig module) {
        super(title, rowsPerPage + 2, title);

        setButton(4, new InventoryButton(new ItemBuilder(module.getConfig().icon()).toItemStack()));
        if(rowsPerPage > 9) setButton(getPageSize() - 1, new InventoryButton(Constants.RIGHT_ARROW));
        setButton(getPageSize() - 9, new InventoryButton(Constants.LEFT_ARROW, (click) -> {
            click.getWhoClicked().closeInventory();
            ((Player) click.getWhoClicked()).performCommand("asmp settings");

        }));
    }
}
