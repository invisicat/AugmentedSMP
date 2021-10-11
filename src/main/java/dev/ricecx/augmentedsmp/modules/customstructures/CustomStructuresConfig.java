package dev.ricecx.augmentedsmp.modules.customstructures;

import dev.ricecx.augmentedsmp.core.file.ConfigField;
import dev.ricecx.augmentedsmp.core.module.ModuleConfig;

public class CustomStructuresConfig implements ModuleConfig {

    @ConfigField
    private boolean newChunks = false;

    public boolean isNewChunks() {
        return newChunks;
    }
}
