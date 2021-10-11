package dev.ricecx.augmentedsmp.modules.supplydrops;

import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;

@Module(
        name = "Supply Drops",
        description = "Supply drops with custom drops",
        parentConfig = "supply_drops",
        configName = "supply-drops",
        configClass = SupplyDropConfig.class)
public class SupplyDropModule extends AbstractModule {
}
