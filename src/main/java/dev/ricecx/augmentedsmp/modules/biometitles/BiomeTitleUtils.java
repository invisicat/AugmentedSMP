package dev.ricecx.augmentedsmp.modules.biometitles;

import org.bukkit.block.Biome;

public class BiomeTitleUtils {

    public static String getSubText(Biome biome) {
        switch (biome) {
            case BEACH -> {
                return "Shimmering waves of the sea.";
            }
            case OCEAN -> {
                return "Cold, blue, ocean breezes out here.";
            }
            case PLAINS -> {
                return "Flat, clear place. Perfect to build a base.";
            }
            case DESERT -> {
                return "Hot, scarce place.";
            }
            case FOREST -> {
                return "Woody, dark area.";
            }
            default -> {
                return "It's cold here...";
            }
        }
    }
}
