package dev.ricecx.augmentedsmp.commands;


import dev.ricecx.augmentedsmp.core.command.CommandCategory;
import dev.ricecx.augmentedsmp.core.command.ICommand;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;
import dev.ricecx.augmentedsmp.modules.Modules;
import dev.ricecx.augmentedsmp.modules.supplydrops.SupplyDropModule;
import dev.ricecx.augmentedsmp.modules.supplydrops.SupplyDrops;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;


@Command(
        name = "spawnsupplydrop",
        category = CommandCategory.DEBUG,
        description = "Spawns a debug supply drop above the player"
)
public class SpawnSupplyDropCommand implements ICommand {

    @Override
    public void run(CommandSender sender, String[] args) {
        SupplyDropModule supplyDropModule = Modules.getModule(SupplyDropModule.class);

        Player player = ((Player) sender);

        int i = 15;
        if(args.length >= 1)
            i = Integer.parseInt(args[0]);



        Location spawnLocation = supplyDropModule.getPossibleSpawnLocations().get(0);

        CompletableFuture<Boolean> spawnSupplyDrop = supplyDropModule.spawnSupplyDrop(spawnLocation, SupplyDrops.MONSTER.getDrop());
        spawnSupplyDrop.thenAcceptAsync(success -> {
            if (success)
                System.out.println("success");
            else
                player.sendMessage("failed");
        });
    }
}
