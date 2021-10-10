package dev.ricecx.augmentedsmp.commands;


import dev.ricecx.augmentedsmp.core.command.CommandCategory;
import dev.ricecx.augmentedsmp.core.command.ICommand;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;
import dev.ricecx.augmentedsmp.modules.ModulesEnum;
import org.bukkit.command.CommandSender;

@Command(
        name = "config",
        description = "Reloads the config",
        category = CommandCategory.GENERAL
)
public class ConfigCommand implements ICommand {

    @Override
    public void run(CommandSender sender, String[] args) {
        for (ModulesEnum module : ModulesEnum.values()) {
            module.getModule().loadConfiguration();
        }

        sender.sendMessage("Reloaded the Config!");
    }
}
