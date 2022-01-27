package me.swanis.pouches.utils;

import me.swanis.pouches.Pouches;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlFile {

    private File file;
    private FileConfiguration config;

    public YamlFile(String name, Pouches instance) {
        file = new File(instance.getDataFolder(), name + ".yml");

        if (!file.exists()) {
            instance.getDataFolder().mkdirs();
            instance.saveResource(name + ".yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
