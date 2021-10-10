package dev.ricecx.augmentedsmp.core.command;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICommand {
    void run(CommandSender sender, String[] args);

    default List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return List.of();
    }

    default String fmt(String... text) {
        return ChatColor.translateAlternateColorCodes('&', String.join(" ", text));
    }

    default BaseComponent[] gradient(String hexStart, String hexStop, String text) {
        net.md_5.bungee.api.ChatColor start = net.md_5.bungee.api.ChatColor.of(hexStart);
        net.md_5.bungee.api.ChatColor stop = net.md_5.bungee.api.ChatColor.of(hexStop);

        ComponentBuilder builder = new ComponentBuilder();
        for (double i = 0; i < text.toCharArray().length; i++) {
            double percentage = ((i + 1) / text.toCharArray().length);
            builder.append(String.valueOf(text.charAt((int) i))).color(net.md_5.bungee.api.ChatColor.of(introp(start, stop, percentage)));
        }

        return builder.create();
    }

    default String introp(net.md_5.bungee.api.ChatColor start, net.md_5.bungee.api.ChatColor stop, double pos) {
        double r = start.getColor().getRed() * pos + stop.getColor().getRed() * (1 - pos);
        double g = start.getColor().getGreen() * pos + stop.getColor().getGreen() * (1 - pos);
        double b = start.getColor().getBlue() * pos + stop.getColor().getBlue() * (1 - pos);

        return String.format("#%02x%02x%02x", (int) r, (int) g, (int) b);
    }
}
