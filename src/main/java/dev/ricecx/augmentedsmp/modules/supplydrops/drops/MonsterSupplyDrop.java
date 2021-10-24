package dev.ricecx.augmentedsmp.modules.supplydrops.drops;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.event.Eventable;
import dev.ricecx.augmentedsmp.modules.supplydrops.SupplyDrop;
import dev.ricecx.augmentedsmp.utils.TimeUnits;
import dev.ricecx.augmentedsmp.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MonsterSupplyDrop extends SupplyDrop {

    public MonsterSupplyDrop() {
        setAnnounce(true);
    }

    @Override
    public List<ItemStack> getLoot() {
        return null;
    }

    @Override
    public void onLand(Location location) {
        Eventable<Boolean> b = AugmentedSMP.getInstance().getEventManager().createEvent(30, TimeUnit.SECONDS);

        for (Player player : AugmentedSMP.getInstance().getAllOnlinePlayers()) {
            Utils.sendSpookyMessage(player, "The monsters have risen!");
            player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
        }

        // Checks if there is any players nearby.

        CompletableFuture<Location> whenPeopleEnter = b.createFuture();

        b.addRunnable(getPlayerCheckRunnable(whenPeopleEnter, location))
                .runTaskTimer(AugmentedSMP.getInstance(), 1, 20);

        whenPeopleEnter.whenComplete((aVoid, err) -> b.addRunnable(getMobSpawnerRunnable(location))
                .runTaskTimer(AugmentedSMP.getInstance(), 1, TimeUnits.TICKS.toMinutes(1)));

        b.getFuture().whenComplete((finished, err) -> {

            for (Player p : AugmentedSMP.getInstance().getAllOnlinePlayers()) {
                p.sendMessage("WE HAVE FINISHED MONSTER SUPPLY DROP.");
            }
        });

    }

    private BukkitRunnable getMobSpawnerRunnable(Location location) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Location loc = location.clone().add(
                            ThreadLocalRandom.current().nextInt(1,4),
                            ThreadLocalRandom.current().nextInt(1,4),
                            ThreadLocalRandom.current().nextInt(1,4)
                    );
                    Objects.requireNonNull(location.getWorld()).spawnEntity(loc, EntityType.ZOMBIE);

                    location.getWorld().playSound(loc, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1, 1);
                    location.getWorld().spawnParticle(Particle.CLOUD, loc,1);
                }
            }
        };
    }

    private BukkitRunnable getPlayerCheckRunnable(CompletableFuture<?> people, Location location) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity nearbyEntity : Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, 10, 3, 10)) {
                    if(nearbyEntity instanceof Player) {
                        people.complete(null);
                        cancel();
                        break;
                    }
                }
            }
        };
    }

}
