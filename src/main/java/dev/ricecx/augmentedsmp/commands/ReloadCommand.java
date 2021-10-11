package dev.ricecx.augmentedsmp.commands;


import dev.ricecx.augmentedsmp.core.command.CommandCategory;
import dev.ricecx.augmentedsmp.core.command.ICommand;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;
import dev.ricecx.augmentedsmp.modules.ModulesEnum;
import org.bukkit.command.CommandSender;

@Command(
        name = "reload",
        description = "Reloads AugmentedSMP",
        category = CommandCategory.GENERAL
)
public class ReloadCommand implements ICommand {

    @Override
    public void run(CommandSender sender, String[] args) {
        for (ModulesEnum module : ModulesEnum.values()) {
            module.getModule().loadConfiguration();
        }

        sender.sendMessage("Successfully reloaded AugmentedSMP");
    }
}
