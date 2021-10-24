package dev.ricecx.augmentedsmp.modules.supplydrops.drops;

import dev.ricecx.augmentedsmp.modules.supplydrops.SupplyDrop;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RegularSupplyDrop extends SupplyDrop {

    @Override
    public List<ItemStack> getLoot() {
        return null;
    }

    @Override
    public void onLand(Location location) {

    }
}
