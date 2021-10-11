package dev.ricecx.augmentedsmp.modules.experiencemultiplier;

import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerExpChangeEvent;

@Module(
        name = "Experience Multiplier",
        description = "Change the experience multiplier",
        configName = "xp-multiplier",
        parentConfig = "xp_multiplier",
        configClass = XPMultiplierConfig.class
)
public class XPMultiplierModule extends AbstractModule {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerExpChange(PlayerExpChangeEvent evt) {
        double xpMultiplier = getConfig(XPMultiplierConfig.class).getExperienceMultiplier();

        int amount = (int) Math.max(1, Math.round(evt.getAmount() * xpMultiplier));
        evt.setAmount(amount);
    }

}
