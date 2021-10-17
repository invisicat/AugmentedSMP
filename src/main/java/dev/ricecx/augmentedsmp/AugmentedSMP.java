package dev.ricecx.augmentedsmp;

import dev.ricecx.augmentedsmp.bStats.UpdateChecker;
import dev.ricecx.augmentedsmp.core.CoreEvent;
import dev.ricecx.augmentedsmp.core.CorePlugin;
import dev.ricecx.augmentedsmp.core.command.CommandManager;
import dev.ricecx.augmentedsmp.core.inventory.InventoryMenuListener;
import dev.ricecx.augmentedsmp.database.DatabaseManager;
import dev.ricecx.augmentedsmp.database.SQLUtils;
import dev.ricecx.augmentedsmp.database.utils.SQLTypes;
import dev.ricecx.augmentedsmp.models.daos.AugmentedPlayerTable;
import dev.ricecx.augmentedsmp.modules.ModulesEnum;
import dev.ricecx.augmentedsmp.utils.LoggingUtils;
import dev.ricecx.augmentedsmp.utils.PlayerCachingManager;


public final class AugmentedSMP extends CorePlugin {

    private CommandManager commandManager;
    private DatabaseManager databaseManager;
    private PlayerCachingManager cachingManager;

    @Override
    public void onEnable() {
        long startingTime = System.currentTimeMillis();

        commandManager = new CommandManager();
        cachingManager = new PlayerCachingManager();
        registerDatabase();
        ModulesEnum.loadModules();


        registerListeners(new CoreEvent(), new InventoryMenuListener());
        UpdateChecker.checkForUpdate();
        LoggingUtils.info("Augmented SMP loaded in " + (System.currentTimeMillis() - startingTime) + "ms!");
    }

    private void registerDatabase() {
        SQLTypes type = SQLTypes.fromName(this.getConfig().getString("database.type"));
        databaseManager = new DatabaseManager(type);
        SQLUtils.setConnection(databaseManager.getConnection());

        AugmentedPlayerTable.createDefaultTable();
    }

    @Override
    public void onDisable() {
        databaseManager.close();
    }

    public static AugmentedSMP getInstance() {
        return getPlugin(AugmentedSMP.class);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public PlayerCachingManager getCachingManager() {
        return cachingManager;
    }
}
