package dev.ricecx.augmentedsmp.utils;

import dev.ricecx.augmentedsmp.AugmentedSMP;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Constants {

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
