package dev.ricecx.augmentedsmp.commands;

import dev.ricecx.augmentedsmp.core.command.CommandCategory;
import dev.ricecx.augmentedsmp.core.command.ICommand;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;
import dev.ricecx.augmentedsmp.monsters.AngryChicken;
import net.minecraft.server.level.WorldServer;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;


@Command(
        name = "spawnmob",
        category = CommandCategory.DEBUG
)
public class SpawnMobCommand implements ICommand {
    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            AngryChicken customPig = new AngryChicken(p.getLocation()/*Pig Spawn Location*/); // Calls CustomPig constructor
            WorldServer world = ((CraftWorld) p.getWorld()).getHandle(); // Creates and NMS world
            world.addEntity(customPig); // Adds the entity to the world
        }
    }
}
