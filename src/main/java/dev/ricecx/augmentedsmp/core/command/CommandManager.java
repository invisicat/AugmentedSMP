package dev.ricecx.augmentedsmp.core.command;

import dev.ricecx.augmentedsmp.utils.LoggingUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandManager {

    private final Map<String, ICommand> cachedCommands = new HashMap<>();

    public CommandManager() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("asmp", createParentCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean executeCommand(@NotNull CommandSender sender, @NotNull String[] args) {

        try {
            ICommand command = args.length <= 0 ? getCommand("help") : getCommand(args[0]);

            command.run(sender, args.length <= 0 ? new String[]{} : Arrays.copyOfRange(args, 1, args.length));
        } catch (NoCommandFoundException e) {
            LoggingUtils.debug(sender.getName(), "tried to execute a invalid command", args[0]);
            return false;
        }

        return true;
    }

    public ICommand getCommand(String name) {
        ICommand command = cachedCommands.getOrDefault(name, Commands.getCommand(name).orElseThrow(NoCommandFoundException::new));
        cachedCommands.putIfAbsent(name, command);

        return command;
    }

    private List<String> handleMainTabComplete() {
        return Arrays.stream(Commands.values()).map((command) -> command.getCommandMetadata().name()).collect(Collectors.toList());
    }

    private List<String> handleCommandAutoCompletion(@NotNull CommandSender sender, @NotNull String[] args) {
        if(args.length <= 1) return handleMainTabComplete();

        ICommand command = getCommand(args[0]);
        if(command != null)
            return command.tabComplete(sender, args);

        return List.of();
    }

    private org.bukkit.command.Command createParentCommand() {
        org.bukkit.command.Command command = new Command("asmp", "Gateway to all AugmentedSMP commands", "/asmp help", List.of()) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                return executeCommand(sender, args);
            }

            @NotNull
            @Override
            public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
                return handleCommandAutoCompletion(sender, args);
            }
        };

        command.setPermission("augmentedsmp.admin");

        return command;
    }
}
