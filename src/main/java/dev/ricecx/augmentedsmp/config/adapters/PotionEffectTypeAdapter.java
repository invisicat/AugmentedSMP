package dev.ricecx.augmentedsmp.config.adapters;

import dev.ricecx.augmentedsmp.core.file.ConfigSerializable;
import dev.ricecx.augmentedsmp.modules.bedhealing.BedHealingConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;


import java.util.Map;

public class PotionEffectTypeAdapter extends ConfigSerializable<Map<PotionEffectType, BedHealingConfig.PotionKV>> {

    @Override
    protected Object serialize(Map<PotionEffectType, BedHealingConfig.PotionKV> potionEffectTypePotionKVMap) {
        Map<String, Map<String, Object>> potions = new HashMap<>();
        for (Map.Entry<PotionEffectType, BedHealingConfig.PotionKV> ppe : potionEffectTypePotionKVMap.entrySet()) {

            potions.put(ppe.getKey().getName(), ppe.getValue().serialize());
        }

        return potions;
    }

    @Override
    public Map<PotionEffectType, BedHealingConfig.PotionKV> deserialize(ConfigurationSection section) {
        Map<PotionEffectType, BedHealingConfig.PotionKV> deserialized = new HashMap<>();
        var potionEffects = section.getValues(false);

        for (Map.Entry<String, Object> potionEffect : potionEffects.entrySet()) {
            deserialized.put(
                    PotionEffectType.getByName(potionEffect.getKey()),
                    new BedHealingConfig.PotionKV(
                            section.getDouble(potionEffect.getKey() + ".chance"),
                            section.getInt(potionEffect.getKey() + ".amplifier")
                    )
            );
        }

        return deserialized;
    }

}
