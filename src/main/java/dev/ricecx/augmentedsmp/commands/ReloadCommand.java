package dev.ricecx.augmentedsmp.commands;


import dev.ricecx.augmentedsmp.core.command.CommandCategory;
import dev.ricecx.augmentedsmp.core.command.ICommand;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;
import dev.ricecx.augmentedsmp.modules.Modules;
import org.bukkit.command.CommandSender;

@Command(
        name = "reload",
        description = "Reloads AugmentedSMP",
        category = CommandCategory.GENERAL
)
public class ReloadCommand implements ICommand {

    @Override
    public void run(CommandSender sender, String[] args) {
        for (Modules module : Modules.values()) {
            module.getModule().loadConfiguration();
        }

        sender.sendMessage("Successfully reloaded AugmentedSMP");
    }
}
