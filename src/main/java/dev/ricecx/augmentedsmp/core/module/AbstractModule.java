package dev.ricecx.augmentedsmp.core.module;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.file.InvalidConfigurationCastException;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;

public class AbstractModule implements Listener {

    private final String name;
    private final String description;
    private final String configKey;
    private final ModuleConfig config;

    public AbstractModule() {
        Module metadata = this.getClass().getAnnotation(Module.class);
        this.name = metadata.name();
        this.description = metadata.description();
        this.configKey = metadata.parentConfig();
        this.config = loadConfiguration();

        AugmentedSMP.getInstance().registerListeners(this);
    }

    public ModuleConfig loadConfiguration() {
        if(this.getClass().getAnnotation(Module.class).configName().isEmpty()) return null;
        ModuleConfiguration.FileConfigurationPair fileConfiguration = ModuleConfiguration.getModuleFileConfiguration(this.getClass().getAnnotation(Module.class).configName());
        ModuleConfig mc = this.createObject(this.getClass().getAnnotation(Module.class).configClass());
        mc.setDefaultData(fileConfiguration);
        mc.setData(fileConfiguration);
        return mc;
    }

    private <T> T createObject(Class<? extends T> clazz) {
        T object = null;
        try {
            object = clazz.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return object;
    }

    public boolean isModuleEnabled() {
        return AugmentedSMP.getInstance().getConfig().getBoolean("modules." + configKey + ".enabled");
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean poke() {
        return true;
    }

    public <T> T getConfig(Class<? extends T> clazz) {
     if(!clazz.isInstance(this.config)) throw new InvalidConfigurationCastException("Check to make sure module annotation is setup properly.");
        return clazz.cast(this.config);
    }

    public void cleanUp() {}
}
