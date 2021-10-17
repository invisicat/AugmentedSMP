package dev.ricecx.augmentedsmp.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;


public class Utils {

    public static String color(String ...strings) {
        return ChatColor.translateAlternateColorCodes('&', String.join("\n", strings));
    }

    public static String fixNamespaceString(String string) {
        return StringUtils.capitalize(string.replace("_", " ").toLowerCase());
    }

    public static void sendMessage(Player player, String ...strings) {
        BaseComponent[] comps = new ComponentBuilder().bold(true)
                .append(Constants.gradient("#55FF55", "#FFFF55", "AugmentedSMP"))
                .append(new ComponentBuilder().color(ChatColor.DARK_GRAY).bold(true).append(" ✦ ").create())
                .append(new ComponentBuilder().bold(false).color(ChatColor.GRAY).append(color(String.join(" ", strings))).bold(false).create()).create();
        player.spigot().sendMessage(comps);
    }
}
