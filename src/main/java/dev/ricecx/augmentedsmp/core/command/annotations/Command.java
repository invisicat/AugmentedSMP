package dev.ricecx.augmentedsmp.core.command.annotations;


import dev.ricecx.augmentedsmp.core.command.CommandCategory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String name();
    String description() default "No description provided :(";
    int minArguments() default 0;
    String usage() default "";
    String[] aliases() default "";
    String[] permissions() default "";
    CommandCategory category() default CommandCategory.NONE;
}
