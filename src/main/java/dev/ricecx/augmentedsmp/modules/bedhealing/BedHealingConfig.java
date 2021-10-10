package dev.ricecx.augmentedsmp.modules.bedhealing;

import dev.ricecx.augmentedsmp.config.adapters.PotionEffectTypeAdapter;
import dev.ricecx.augmentedsmp.core.file.ConfigAdapter;
import dev.ricecx.augmentedsmp.core.file.ConfigField;
import dev.ricecx.augmentedsmp.core.file.ConfigSerializable;
import dev.ricecx.augmentedsmp.core.module.ModuleConfig;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;


public class BedHealingConfig implements ModuleConfig {

    private static final Map<PotionEffectType, PotionKV> defaultPotions = new HashMap<>();

    static {
        defaultPotions.put(PotionEffectType.REGENERATION, new PotionKV(0.5, 1));
        defaultPotions.put(PotionEffectType.BLINDNESS, new PotionKV(0.5, 1));
        defaultPotions.put(PotionEffectType.CONFUSION, new PotionKV(0.5, 1));
    }


    public BedHealingConfig() {}

    @ConfigField
    private final double healPercentage = 0.6;

    @ConfigField
    private final double minHealth = 0.0;

    @ConfigField
    private final double maxHealth = 24.0;

    @ConfigField
    @ConfigAdapter(adapter = PotionEffectTypeAdapter.class)
    private final Map<PotionEffectType, PotionKV> potions = defaultPotions;

    public double getHealPercentage() {
        return healPercentage;
    }

    public Map<PotionEffectType, PotionKV> getPotions() {
        return potions;
    }

    public static class PotionKV {
        public double chance;
        public int amplifier;

        public PotionKV(double chance, int amplifier) {
            this.chance = chance;
            this.amplifier = amplifier;
        }

        public double getChance() {
            return chance;
        }

        public int getAmplifier() {
            return amplifier;
        }

        public Map<String, Object> serialize() {
            return Map.of("amplifier", getAmplifier(), "chance", getChance());
        }
    }
}
