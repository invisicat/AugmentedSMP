package dev.ricecx.augmentedsmp.core.command;

import dev.ricecx.augmentedsmp.commands.HelpCommand;
import dev.ricecx.augmentedsmp.commands.ModulesCommand;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;

import java.util.Optional;

public enum CommandsEnum {

    HELP(new HelpCommand()),
    MODULES(new ModulesCommand())
    ;

    private static final CommandsEnum[] CACHE = values();
    private final ICommand command;

    CommandsEnum(ICommand command) {
        this.command = command;
    }

    public static Optional<ICommand> getCommand(String name) {
        for (CommandsEnum command : CACHE)
            if(command.getCommandMetadata().name().equals(name)) return Optional.of(command.getCommand());

        return Optional.empty();
    }

    public static Optional<CommandsEnum> fromName(String name) {
        for (CommandsEnum command : CACHE)
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
