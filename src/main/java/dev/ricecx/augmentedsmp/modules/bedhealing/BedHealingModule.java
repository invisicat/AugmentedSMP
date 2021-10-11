package dev.ricecx.augmentedsmp.modules.bedhealing;


import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;
import dev.ricecx.augmentedsmp.utils.RandomCollection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Module(
        name = "Bed Healing",
        description = "Heals the player when sleeping",
        parentConfig = "bedhealing",
        configName = "bed-healing",
        configClass = BedHealingConfig.class)
public class BedHealingModule extends AbstractModule {

    private final RandomCollection<PotionEffectRecord> randomSelector = new RandomCollection<>();

    public BedHealingModule() {
        BedHealingConfig config = getConfig(BedHealingConfig.class);

        for (Map.Entry<PotionEffectType, BedHealingConfig.PotionKV> potions : config.getPotions().entrySet()) {
            randomSelector.add(potions.getValue().getChance(), new PotionEffectRecord(potions.getKey(), potions.getValue().getAmplifier()));
        }
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent evt) {
        if (!isModuleEnabled()) return;
        BedHealingConfig config = getConfig(BedHealingConfig.class);

        if (evt.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) return;

        if(config.getHealPercentage() * 100 > ThreadLocalRandom.current().nextDouble(1) * 100) {
            PotionEffectRecord pot = randomSelector.next();
            evt.getPlayer().addPotionEffect(new PotionEffect(pot.potionEffectType(), 480, pot.amplifier()));
        }
    }

    @EventHandler
    public void onExitBed(PlayerBedLeaveEvent evt) {
        if (!isModuleEnabled()) return;
        BedHealingConfig config = getConfig(BedHealingConfig.class);

        for (PotionEffectType potion : config.getPotions().keySet()) {
            evt.getPlayer().removePotionEffect(potion);
        }
    }
}
