package dev.ricecx.augmentedsmp.core.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryMenuListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent evt) {
        if(evt.getInventory().getHolder() == null || !(evt.getInventory().getHolder() instanceof InventoryMenu invMenu)) return;
        evt.setCancelled(true);

        // on pagination
        if(evt.getSlot() > invMenu.getPageSize()) return;

        InventoryButton btn = invMenu.getButton(invMenu.getCurrentPage() ,evt.getSlot());

        if(btn != null && btn.getListener() != null)
            btn.getListener().onClick(evt);

        invMenu.refreshInventory(evt.getWhoClicked());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if(evt.getInventory().getHolder() == null || !(evt.getInventory().getHolder() instanceof InventoryMenu invMenu)) return;
        if(invMenu.getOnClose() != null)
            invMenu.getOnClose().accept(invMenu);
    }
}
