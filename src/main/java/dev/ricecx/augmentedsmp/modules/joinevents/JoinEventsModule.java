package dev.ricecx.augmentedsmp.modules.joinevents;


import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.ThreadLocalRandom;

@Module(
        name = "Join Events",
        parentConfig = "joinevents",
        forUser = true,
        playerConfigClass = JoinEventPlayerConfig.class
)
public class JoinEventsModule extends AbstractModule {

    private static final Sound[] joinSounds = new Sound[] {
            Sound.BLOCK_ANVIL_PLACE,
            Sound.BLOCK_BEACON_ACTIVATE,
            Sound.ENTITY_GENERIC_EXPLODE
    };

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(AugmentedSMP.getInstance(), () -> {
            playJoinSound();

            evt.getPlayer().sendTitle("[ASMP] Test Welcome Join Title", "Good evening " + evt.getPlayer().getDisplayName(), 2 * 20,3 * 20, 20);
        }, 1);
    }


    private void playJoinSound() {
        Sound sound = joinSounds[ThreadLocalRandom.current().nextInt(joinSounds.length)];
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, 1, 1);
        }
    }
}
