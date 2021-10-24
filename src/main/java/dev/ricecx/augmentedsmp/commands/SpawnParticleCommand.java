package dev.ricecx.augmentedsmp.commands;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.command.CommandCategory;
import dev.ricecx.augmentedsmp.core.command.ICommand;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;
import dev.ricecx.augmentedsmp.modules.Modules;
import dev.ricecx.augmentedsmp.modules.ambientparticles.AmbientParticleModule;
import dev.ricecx.augmentedsmp.utils.PlayerCachingManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


@Command(
        name = "spawnparticle",
        category = CommandCategory.DEBUG
)
public class SpawnParticleCommand implements ICommand {

    @Override
    public void run(CommandSender sender, String[] args) {
        AmbientParticleModule module = Modules.getModule(AmbientParticleModule.class);

        if(args.length <= 0)
            module.spawnParticles((Player) sender);
        if(args[0].equalsIgnoreCase("on"))
            runTask((Player) sender);
        else
            cancelTask((Player) sender);

    }

    private void runTask(Player player) {
        AmbientParticleModule module = Modules.getModule(AmbientParticleModule.class);

        new BukkitRunnable() {
            @Override
            public void run() {
                Boolean running = getCache(player, "particles-run", true, Boolean.class);
                if(!running) {
                    cancel();
                    return;
                }
                module.spawnParticles(player);
            }
        }.runTaskTimerAsynchronously(AugmentedSMP.getInstance(), 1, 10);
        player.sendMessage("started task");
    }

    private void cancelTask(Player player) {
        PlayerCachingManager manager = AugmentedSMP.getInstance().getCachingManager();
         setCache(player, "particles-run", false);
         player.sendMessage("Cancelled task");
    }
}
