package dev.ricecx.augmentedsmp.core.command;

import dev.ricecx.augmentedsmp.commands.*;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;

import java.util.Optional;

public enum Commands {

    HELP(new HelpCommand()),
    MODULES(new ModulesCommand()),
    CONFIG(new ReloadCommand()),
    SETTINGS(new SettingsCommand()),
    SPAWN_PARTICLES(new SpawnParticleCommand()),
    SPAWN_MOB(new SpawnMobCommand()),
    SPAWN_SUPPLY_DROP(new SpawnSupplyDropCommand())
    ;

    private static final Commands[] CACHE = values();
    private final ICommand command;

    Commands(ICommand command) {
        this.command = command;
    }

    public static Optional<ICommand> getCommand(String name) {
        for (Commands command : CACHE)
            if(command.getCommandMetadata().name().equals(name)) return Optional.of(command.getCommand());

        return Optional.empty();
    }

    public static Optional<Commands> fromName(String name) {
        for (Commands command : CACHE)
            if(command.getCommandMetadata().name().equals(name)) return Optional.of(command);

        return Optional.empty();
    }

    public Command getCommandMetadata() {
        return this.getCommand().getClass().getAnnotation(Command.class);
    }

    /* Getters and Setters */

    public ICommand getCommand() {
        return command;
    }

}
