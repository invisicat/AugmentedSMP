package dev.ricecx.augmentedsmp.core.module;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    String name();
    String description() default "No description for this module yet :(";
    String parentConfig();
    boolean forUser() default false;
    String configName() default "";
    Class<? extends ModuleConfig> configClass() default ModuleConfig.class;
    Class<? extends AbstractPlayerConfig> playerConfigClass() default AbstractPlayerConfig.class;
}
