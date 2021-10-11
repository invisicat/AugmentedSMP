package dev.ricecx.augmentedsmp.modules.experiencemultiplier;

import dev.ricecx.augmentedsmp.core.file.ConfigField;
import dev.ricecx.augmentedsmp.core.module.ModuleConfig;

public class XPMultiplierConfig implements ModuleConfig {


    @ConfigField
    private double xp_multiplier = 1.0;

    public double getExperienceMultiplier() {
        return xp_multiplier;
    }
}
