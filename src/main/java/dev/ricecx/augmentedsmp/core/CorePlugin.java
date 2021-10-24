package dev.ricecx.augmentedsmp.core;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore;
import dev.ricecx.augmentedsmp.utils.LoggingUtils;
import dev.ricecx.augmentedsmp.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Player> getAllOnlinePlayers() {
        return Bukkit.getOnlinePlayers().parallelStream().collect(Collectors.toList());
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

    public ParticleNativeAPI registerParticleAPI() {
        ParticleNativeAPI api = null;
        try {
            api = ParticleNativeCore.loadAPI(this);
            this.getLogger().info("Native Particle API has loaded for " + Version.getServerVersion(this.getServer()));
        } catch (ParticleException e) {
            this.getLogger().severe("Could not load Native Particle API. Disabling plugin...");
            e.printStackTrace();
        }
        return api;
    }
}
