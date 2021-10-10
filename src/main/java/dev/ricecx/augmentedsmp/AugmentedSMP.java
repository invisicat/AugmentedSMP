package dev.ricecx.augmentedsmp;

import dev.ricecx.augmentedsmp.bStats.UpdateChecker;
import dev.ricecx.augmentedsmp.core.CorePlugin;
import dev.ricecx.augmentedsmp.core.command.CommandManager;
import dev.ricecx.augmentedsmp.modules.ModulesEnum;
import dev.ricecx.augmentedsmp.utils.LoggingUtils;

public final class AugmentedSMP extends CorePlugin {

    private CommandManager commandManager;

    @Override
    public void onEnable() {
        long startingTime = System.currentTimeMillis();

        commandManager = new CommandManager();
        ModulesEnum.loadModules();


        UpdateChecker.checkForUpdate();
        LoggingUtils.info("Augmented SMP loaded in " + (System.currentTimeMillis() - startingTime) + "ms!");
    }

    @Override
    public void onDisable() {

    }

    public static AugmentedSMP getInstance() {
        return getPlugin(AugmentedSMP.class);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
