package dev.ricecx.augmentedsmp;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import dev.ricecx.augmentedsmp.bStats.UpdateChecker;
import dev.ricecx.augmentedsmp.core.CoreEvent;
import dev.ricecx.augmentedsmp.core.CorePlugin;
import dev.ricecx.augmentedsmp.core.event.EventManager;
import dev.ricecx.augmentedsmp.core.command.CommandManager;
import dev.ricecx.augmentedsmp.core.inventory.InventoryMenuListener;
import dev.ricecx.augmentedsmp.database.DatabaseManager;
import dev.ricecx.augmentedsmp.database.SQLUtils;
import dev.ricecx.augmentedsmp.database.utils.SQLTypes;
import dev.ricecx.augmentedsmp.models.daos.AugmentedPlayerTable;
import dev.ricecx.augmentedsmp.modules.Modules;
import dev.ricecx.augmentedsmp.utils.LoggingUtils;
import dev.ricecx.augmentedsmp.utils.PlayerCachingManager;


public final class AugmentedSMP extends CorePlugin {

    private CommandManager commandManager;
    private DatabaseManager databaseManager;
    private PlayerCachingManager cachingManager;
    private EventManager eventManager;

    /* APIs */
    private ParticleNativeAPI particlesAPI;

    @Override
    public void onEnable() {
        long startingTime = System.currentTimeMillis();

        particlesAPI = registerParticleAPI();

        commandManager = new CommandManager();
        cachingManager = new PlayerCachingManager();
        eventManager = new EventManager();
        registerDatabase();
        Modules.loadModules();


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

    public EventManager getEventManager() {
        return eventManager;
    }

    public ParticleNativeAPI getParticlesAPI() {
        return particlesAPI;
    }
}
