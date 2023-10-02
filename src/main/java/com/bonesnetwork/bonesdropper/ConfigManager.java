package com.bonesnetwork.bonesdropper;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    public static YamlDocument mainConfig;
    public static HashMap<String,YamlDocument> langFiles = new HashMap<>();
    
    
    public static void setup(BonesDropper bonesDropper) throws IOException {
        mainConfig = YamlDocument.create(new File(bonesDropper.getDataFolder(), "config.yml"), bonesDropper.getResource("config.yml"), GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).build(), LoaderSettings.DEFAULT, DumperSettings.DEFAULT, UpdaterSettings.DEFAULT);
    
        bonesDropper.saveResource("lang/en_us.yml", false);
        File langFolder = new File(bonesDropper.getDataFolder(), "lang");
        for (File langFile:langFolder.listFiles()) {
            YamlDocument langConfig = YamlDocument.create(langFile, GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).build(), LoaderSettings.DEFAULT, DumperSettings.DEFAULT, UpdaterSettings.DEFAULT);
            String configKey = langConfig.getString("lang.key");
            langFiles.put(configKey,langConfig);
        }
    }
    
    public static void reload() throws IOException {
        mainConfig.reload();
        for(Map.Entry<String, YamlDocument> document : langFiles.entrySet()) {
            document.getValue().reload();
        }
    }
}
