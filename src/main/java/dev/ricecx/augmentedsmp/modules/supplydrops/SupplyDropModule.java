package dev.ricecx.augmentedsmp.modules.supplydrops;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.event.Eventable;
import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;
import dev.ricecx.augmentedsmp.utils.FireworkBuilder;
import dev.ricecx.augmentedsmp.utils.ItemBuilder;
import dev.ricecx.augmentedsmp.utils.TimeUnits;
import dev.ricecx.augmentedsmp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Module(
        name = "Supply Drops",
        description = "Supply drops with custom drops",
        parentConfig = "supply_drops",
        configName = "supply-drops",
        configClass = SupplyDropConfig.class)
public class SupplyDropModule extends AbstractModule {

    private Eventable<Boolean> event;

    public List<Location> getPossibleSpawnLocations() {
        return getPossibleSpawnLocations(false);
    }

    public List<Location> getPossibleSpawnLocations(boolean fromConfig) {
        List<Location> locations = new ArrayList<>();
        if (fromConfig)
            return getLocationsFromConfig();
        else {
            for (Player onlinePlayer : AugmentedSMP.getInstance().getAllOnlinePlayers()) {
                locations.add(onlinePlayer.getLocation().clone().add(0, 40, 0));
            }
        }

        return locations;
    }

    private List<Location> getLocationsFromConfig() {
        throw new NotImplementedException("Not implemented locations from config");
    }

    public CompletableFuture<Boolean> spawnSupplyDrop(@NotNull Location location, @NotNull SupplyDrop supplyDrop) {
        if(event == null || event.getFuture().isDone() || event.getFuture().isCancelled())
            event = AugmentedSMP.getInstance().getEventManager().createEvent(5, TimeUnit.MINUTES);
        else
            return CompletableFuture.failedFuture(new Throwable("Event already started."));

        CompletableFuture<Boolean> future = event.createFuture();
        CompletableFuture<ArmorStand> armorStandSupplyDrop = event.addFuture(spawnArmorStandSupplyDrop(location, supplyDrop));

        armorStandSupplyDrop.whenComplete((stand, err) -> Bukkit.getScheduler().runTaskLater(AugmentedSMP.getInstance(), () -> {
            if (err != null)
                err.printStackTrace();

            stand.remove();

            BlockData chest = Material.CHEST.createBlockData();
            ((Directional) chest).setFacing(stand.getFacing());
            stand.getWorld().setBlockData(stand.getLocation(), chest);

            event.complete(true);
            future.complete(true);
        }, 1)).thenAccept((stand) -> Bukkit.getScheduler().runTaskLater(AugmentedSMP.getInstance(), () -> {
            FireworkBuilder.spawnFireworks(stand.getLocation(), 5);
            supplyDrop.onLand(stand.getLocation());
            if (supplyDrop.isAnnounce())
                supplyDrop.sendPlayerLandedMessage(stand.getLocation());
        }, 1));

        return future;
    }

    public CompletableFuture<ArmorStand> spawnArmorStandSupplyDrop(@NotNull Location location, SupplyDrop supplyDrop) {
        CompletableFuture<ArmorStand> future = event.createFuture();
        if (location.getWorld() == null)
            return CompletableFuture.failedFuture(new Throwable("no world location found"));
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        if (armorStand.getEquipment() == null)
            return CompletableFuture.failedFuture(new Throwable("no equpiment found on armor"));
        armorStand.getEquipment().setHelmet(new ItemBuilder(Material.CHEST).toItemStack());
        armorStand.setInvisible(true);
        armorStand.setGliding(true);

        Bukkit.getScheduler().runTaskLater(AugmentedSMP.getInstance(), () ->
                        armorStand.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 4, false))
                , 1);

        // Announcing
        event.addRunnable((runnable) -> {
            if (armorStand.isDead() || !supplyDrop.isAnnounce())
                runnable.cancel();
            else
                supplyDrop.sendPlayerLocationMessage(location);
        }).runTaskTimerAsynchronously(AugmentedSMP.getInstance(), 1, TimeUnits.TICKS.toSeconds(30));

        // Rotating
        event.addRunnable(runnable -> {
            if (armorStand.isDead())
                runnable.cancel();

            armorStand.getLocation().setPitch(armorStand.getLocation().getPitch() + 4);

            System.out.println("ROTATING" + armorStand.getLocation().getYaw());
        }).runTaskTimer(AugmentedSMP.getInstance(), 1, 10);

        // Lightning Strike
        event.addRunnable((r) -> {
            if (armorStand.isDead())
                r.cancel();

            Location groundLocation = armorStand.getLocation().clone().add(0, armorStand.getFallDistance(), 0);

            location.getWorld().strikeLightning(groundLocation);
        }).runTaskTimer(AugmentedSMP.getInstance(), 1, TimeUnits.TICKS.toSeconds(5));


        event.addRunnable((r) -> {
            if (armorStand.isOnGround()) {
                future.complete(armorStand);
                r.cancel();
            }
        }).runTaskTimerAsynchronously(AugmentedSMP.getInstance(), 1, 1);

        return future;
    }
}