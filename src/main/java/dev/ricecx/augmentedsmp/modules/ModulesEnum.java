package dev.ricecx.augmentedsmp.modules;

import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.modules.inventorydeath.InventoryDeathModule;
import dev.ricecx.augmentedsmp.modules.leveling.LevelingModule;

public enum ModulesEnum {
    LEVELING(new LevelingModule()),
    INVENTORY_DEATH(new InventoryDeathModule())
    ;

    private static final ModulesEnum[] CACHE = values();

    private final AbstractModule module;

    ModulesEnum(AbstractModule module) {
        this.module = module;
    }

    public AbstractModule getModule() {
        return module;
    }
}