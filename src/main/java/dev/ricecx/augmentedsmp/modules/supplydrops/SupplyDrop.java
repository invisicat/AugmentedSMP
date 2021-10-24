package dev.ricecx.augmentedsmp.modules.supplydrops;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class SupplyDrop {

    private boolean announce = false;
    public abstract List<ItemStack> getLoot();

    public abstract void onLand(Location location);

    public void sendPlayerLandedMessage(Location location) {
        for (Player onlinePlayer : AugmentedSMP.getInstance().getAllOnlinePlayers()) {
            String text = "A supply drop has landed at: &e{x} {y} {z}&8!"
                    .replace("{x}", Utils.roundDouble(location.getX()))
                    .replace("{y}", Utils.roundDouble(location.getY()))
                    .replace("{z}", Utils.roundDouble(location.getZ()))
                    ;

            Utils.sendMessage(onlinePlayer, text);
        }
    }

    public void sendPlayerLocationMessage(Location location) {
        for (Player onlinePlayer : AugmentedSMP.getInstance().getAllOnlinePlayers()) {
            String text = "There is a supply drop dropping in at location: &c{x} {y} {z}&8!"
                    .replace("{x}", Utils.roundDouble(location.getX()))
                    .replace("{y}", Utils.roundDouble(location.getY()))
                    .replace("{z}", Utils.roundDouble(location.getZ()))
                    ;

            Utils.sendMessage(onlinePlayer, text);
        }
    }

    public boolean isAnnounce() {
        return announce;
    }

    public void setAnnounce(boolean announce) {
        this.announce = announce;
    }
}
