package dev.ricecx.augmentedsmp.utils;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Constants {

    public static final String RESOURCE_ID = "";

    public static final ItemStack LEFT_ARROW = new ItemBuilder(Material.PLAYER_HEAD).setName("&b← Previous Page").setSkullOwnerNMS(new ItemBuilder.SkullData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RjOWU0ZGNmYTQyMjFhMWZhZGMxYjViMmIxMWQ4YmVlYjU3ODc5YWYxYzQyMzYyMTQyYmFlMWVkZDUifX19", ItemBuilder.SkullDataType.TEXTURE)).toItemStack();
    public static final ItemStack RIGHT_ARROW = new ItemBuilder(Material.PLAYER_HEAD).setName("&bNext Page →").setSkullOwnerNMS(new ItemBuilder.SkullData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTU2YTM2MTg0NTllNDNiMjg3YjIyYjdlMjM1ZWM2OTk1OTQ1NDZjNmZjZDZkYzg0YmZjYTRjZjMwYWI5MzExIn19fQ==", ItemBuilder.SkullDataType.TEXTURE)).toItemStack();

    public static ComponentBuilder createTitle() {
        return new ComponentBuilder()
                .append(gradient("#55FF55", "#FFFF55", "AugmentedSMP"))
                .append(gradient("#b2ef91", "#fa9372", " v" + AugmentedSMP.getInstance().getDescription().getVersion() + " (MC " + AugmentedSMP.getInstance().getDescription().getAPIVersion() + ")"));
    }


    public static BaseComponent[] gradient(String hexStart, String hexStop, String text) {
        net.md_5.bungee.api.ChatColor start = net.md_5.bungee.api.ChatColor.of(hexStart);
        net.md_5.bungee.api.ChatColor stop = net.md_5.bungee.api.ChatColor.of(hexStop);

        ComponentBuilder builder = new ComponentBuilder();
        for (double i = 0; i < text.toCharArray().length; i++) {
            double percentage = ((i + 1) / text.toCharArray().length);
            builder.append(String.valueOf(text.charAt((int) i))).color(net.md_5.bungee.api.ChatColor.of(introp(start, stop, percentage)));
        }

        return builder.create();
    }

    public static String introp(net.md_5.bungee.api.ChatColor start, net.md_5.bungee.api.ChatColor stop, double pos) {
        double r = start.getColor().getRed() * pos + stop.getColor().getRed() * (1 - pos);
        double g = start.getColor().getGreen() * pos + stop.getColor().getGreen() * (1 - pos);
        double b = start.getColor().getBlue() * pos + stop.getColor().getBlue() * (1 - pos);

        return String.format("#%02x%02x%02x", (int) r, (int) g, (int) b);
    }
}
