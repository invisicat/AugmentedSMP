package dev.ricecx.augmentedsmp.core.module;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import org.bukkit.event.Listener;

public class AbstractModule implements Listener {

    private final String name;
    private final String description;

    public AbstractModule() {
        this.name = this.getClass().getAnnotation(Module.class).name();
        this.description = this.getClass().getAnnotation(Module.class).description();

        AugmentedSMP.getInstance().registerListeners(this);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
