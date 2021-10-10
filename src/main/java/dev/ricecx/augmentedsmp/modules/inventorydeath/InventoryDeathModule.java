package dev.ricecx.augmentedsmp.modules.inventorydeath;


import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

@Module(
        name = "Inventory Death Module",
        parentConfig = "inventorydeath"
)
public class InventoryDeathModule extends AbstractModule {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(PlayerDeathEvent evt) {

    }

}
