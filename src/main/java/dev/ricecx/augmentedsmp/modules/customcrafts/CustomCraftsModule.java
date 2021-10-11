package dev.ricecx.augmentedsmp.modules.customcrafts;


import dev.ricecx.augmentedsmp.core.module.AbstractModule;
import dev.ricecx.augmentedsmp.core.module.Module;

@Module(
        name = "Custom Crafts",
        description = "Custom Crafts",
        parentConfig = "customcrafts",
        configName = "custom-crafts",
        configClass = CustomCraftsConfig.class)
public class CustomCraftsModule extends AbstractModule {
}
