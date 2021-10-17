package dev.ricecx.augmentedsmp.utils;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;


public class Utils {

    public static String color(String ...strings) {
        return ChatColor.translateAlternateColorCodes('&', String.join("\n", strings));
    }

    public static String fixNamespaceString(String string) {
        return StringUtils.capitalize(string.replace("_", " ").toLowerCase());
    }
}
