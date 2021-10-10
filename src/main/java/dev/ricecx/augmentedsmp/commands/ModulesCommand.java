package dev.ricecx.augmentedsmp.commands;

import dev.ricecx.augmentedsmp.core.command.ICommand;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;
import dev.ricecx.augmentedsmp.modules.ModulesEnum;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Command(
        name = "module",
        description = "Configure the custom modules."
)
public class ModulesCommand implements ICommand {

    @Override
    public void run(CommandSender sender, String[] args) {
        sender.sendMessage("Module command!");
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();

        if (args.length <= 0) {
            completions.addAll(Arrays.stream(ModulesEnum.values()).map(Enum::name).collect(Collectors.toList()));
        } else if (args.length <= 2)
            StringUtil.copyPartialMatches(args[1], Arrays.stream(ModulesEnum.values()).map(Enum::name).collect(Collectors.toList()), completions);
        else
            return List.of();

        Collections.sort(completions);
        return completions;
    }
}
