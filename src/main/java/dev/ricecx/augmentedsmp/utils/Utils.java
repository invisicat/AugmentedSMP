package dev.ricecx.augmentedsmp.utils;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
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

    public static void runOnNextTick(Runnable r) {
        Bukkit.getScheduler().runTaskLater(AugmentedSMP.getInstance(), r, 1);
    }

    public static void sendSpookyMessage(Player player, String ...strings) {
        BaseComponent[] comps = new ComponentBuilder().bold(true)
                .append(Constants.gradient("#1f1e1e", "#bf0404", "AugmentedSMP"))
                .append(new ComponentBuilder().color(ChatColor.DARK_GRAY).bold(true).append(" ✦ ").create())
                .append(new ComponentBuilder().bold(false).color(ChatColor.GRAY).append(color(String.join(" ", strings))).bold(false).create()).create();
        player.spigot().sendMessage(comps);
    }

    public static String roundDouble(double x) {
        return String.valueOf(Math.round(x));
    }

    public static <T extends Comparable<T>> T clamp(T val, T min, T max) {
        if (val.compareTo(min) < 0) return min;
        else if (val.compareTo(max) > 0) return max;
        else return val;
    }

}
