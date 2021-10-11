package dev.ricecx.augmentedsmp.modules;

import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.modules.bedhealing.BedHealingModule;
import dev.ricecx.augmentedsmp.modules.customcrafts.CustomCraftsModule;
import dev.ricecx.augmentedsmp.modules.experiencemultiplier.XPMultiplierModule;
import dev.ricecx.augmentedsmp.modules.foodbuff.FoodBuffModule;
import dev.ricecx.augmentedsmp.modules.inventorydeath.InventoryDeathModule;
import dev.ricecx.augmentedsmp.modules.joinevents.JoinEventsModule;
import dev.ricecx.augmentedsmp.modules.leveling.LevelingModule;
import dev.ricecx.augmentedsmp.modules.supplydrops.SupplyDropModule;

public enum ModulesEnum {
    LEVELING(new LevelingModule()),
    INVENTORY_DEATH(new InventoryDeathModule()),
    BED_HEALING(new BedHealingModule()),
    FOOD_BUFF(new FoodBuffModule()),
    XP_MULTIPLIER(new XPMultiplierModule()),
    CUSTOM_CRAFTS(new CustomCraftsModule()),
    SUPPLY_DROPS(new SupplyDropModule()),
    JOIN_EVENTS(new JoinEventsModule())
    ;

    private static final ModulesEnum[] CACHE = values();

    private final AbstractModule module;

    ModulesEnum(AbstractModule module) {
        this.module = module;
    }

    public static void loadModules() {
        for (ModulesEnum module : CACHE) {
            module.getModule().poke();
        }
    }

    public AbstractModule getModule() {
        return module;
    }
}
