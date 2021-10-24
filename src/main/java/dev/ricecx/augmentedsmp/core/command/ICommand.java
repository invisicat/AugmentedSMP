package dev.ricecx.augmentedsmp.core.command;

import dev.ricecx.augmentedsmp.core.interfaces.PlayerCacheable;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICommand extends PlayerCacheable {
    void run(CommandSender sender, String[] args);

    default List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return List.of();
    }

    default String fmt(String... text) {
        return ChatColor.translateAlternateColorCodes('&', String.join(" ", text));
    }

}
