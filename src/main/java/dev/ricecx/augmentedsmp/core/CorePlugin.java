package dev.ricecx.augmentedsmp.core;

import dev.ricecx.augmentedsmp.utils.LoggingUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CorePlugin extends JavaPlugin {

    private final boolean paper;
    private final String databasePath = this.getDataFolder().getPath();

    public CorePlugin() {
        paper = isUsingPaper();

        configureLogger();
    }


    /**
     * Register a listener to Bukkit
     *
     * @param listeners Listeners to register
     */
    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            LoggingUtils.debug("Registering listener " + listener.toString());
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    /**
     * Call an event through Bukkit
     *
     * @param evt Event to call
     */
    public void callEvent(Event evt) {
        getServer().getPluginManager().callEvent(evt);
    }

    /**
     * Register recipe to Bukkit
     * @param recipe Recipe to register
     */
    public void registerRecipe(@NotNull Recipe recipe) {
        Bukkit.addRecipe(recipe);
    }

    private void loadDependencies() {
        for (Dependencies dependency : Dependencies.values()) {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(dependency.getName());

        }
    }
    private void configureLogger() {
        this.saveDefaultConfig();
        if(this.getConfig().getBoolean("verbose"))
            LoggingUtils.setVerbose(true);
    }
    private boolean isUsingPaper() {
        try {
            Class.forName("com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent");
            this.getLogger().info("Paper events will be used in order to improve performance");
            return true;
        } catch (ClassNotFoundException e) {
            this.getLogger().info("This server doesn't seem to be running Paper or a paper-fork, falling back to Spigot events.");
            return false;
        }
    }
    public boolean isPaper() {
        return paper;
    }

    public String getDatabasePath() {
        return databasePath;
    }
}
