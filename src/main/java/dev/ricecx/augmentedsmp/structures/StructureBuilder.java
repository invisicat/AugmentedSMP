package dev.ricecx.augmentedsmp.structures;

import dev.ricecx.augmentedsmp.structures.exceptions.StructureConfigException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class StructureBuilder {

    protected String name;
    protected String schematic;

    /**
     * Build a structure from yaml config.
     * @param name Name of the structure
     * @param file the YAML configuration
     */
    public StructureBuilder(String name, File file) {
        if(!file.exists())
            throw new RuntimeException("Cannot build this structure: This file doesn't exist! " + file.getName());

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    }

    private void checkValidConfig(YamlConfiguration config) {
        if (!config.contains("schematic")) {
            throw new StructureConfigException("No Schematic found!");
        }
        if (!config.contains("Chance.Number")) {
            throw new StructureConfigException("`Chance.Number` is required!");
        }
        if (!config.contains("Chance.OutOf")) {
            throw new StructureConfigException("`Chance.OutOf` is required!");
        }
        if (!config.isInt("Chance.Number") || config.getInt("Chance.Number") < 1) {
            throw new StructureConfigException("`Chance.Number` must be a number cannot be less than 1!");
        }
        if (!config.isInt("Chance.OutOf") || config.getInt("Chance.OutOf") < 1) {
            throw new StructureConfigException("`Chance.OutOf` must be a number cannot be less than 1!");
        }
    }
}
