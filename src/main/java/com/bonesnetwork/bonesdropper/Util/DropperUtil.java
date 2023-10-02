package com.bonesnetwork.bonesdropper.Util;

import com.bonesnetwork.bonesdropper.BonesDropper;
import com.bonesnetwork.bonesdropper.ConfigManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;


public class DropperUtil {
    
    public static Boolean getDropperExists(String dropper) {
        return ConfigManager.mainConfig.contains("Dropper."+dropper);
    }
    
    public static Location getDropperSpawn(String dropper) {
        return ConfigManager.mainConfig.getAs("Dropper."+dropper+".Location", Location.class);
    }
    
    public static Component getDropperTitle(String dropper) {
        return MiniMessage.miniMessage().deserialize(ConfigManager.mainConfig.getString("Dropper."+dropper+".Title"));
    }
    
    public static void setDropperSpawn(String dropper, Location dropperSpawn) {
        ConfigManager.mainConfig.set("Dropper."+dropper+".Location", dropperSpawn);
    }
    
    public static void setDropperTitle(String dropper, String dropperTitle) {
        ConfigManager.mainConfig.set("Dropper."+dropper+".Title", dropperTitle);
    }
    
    public static ArrayList<String> getAllDroppers() {
        return new ArrayList<>(ConfigManager.mainConfig.getSection("Dropper").getRoutesAsStrings(false));
    }
    
    public static void createDropper(String id) {
        ConfigManager.mainConfig.createSection("Dropper."+id);
        try {
            ConfigManager.mainConfig.save();
            ConfigManager.mainConfig.reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void removeDropper(String dropper) {
        ConfigManager.mainConfig.remove("Dropper."+dropper);
        try {
            ConfigManager.mainConfig.save();
            ConfigManager.mainConfig.reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void teleportPlayerToDropperSpawn(Player player) {
        NamespacedKey dropperKey = new NamespacedKey(BonesDropper.getPlugin(), "dropper");
        String dropper = player.getPersistentDataContainer().get(dropperKey, PersistentDataType.STRING);
        player.teleport(getDropperSpawn(dropper));
    }
    
    public static void teleportPlayerToNextDropper(Player player) {
        NamespacedKey dropperKey = new NamespacedKey(BonesDropper.getPlugin(), "dropper");
        String dropper = player.getPersistentDataContainer().get(dropperKey, PersistentDataType.STRING);
        Bukkit.getLogger().info("Player Current Dropper: "+dropper);
        try {
            dropper = getAllDroppers().get(getAllDroppers().indexOf(dropper)+1);
        } catch (IndexOutOfBoundsException e) {
            //TODO: Add end of dropper sequence
        }
        
        Bukkit.getLogger().info("Player Next Dropper: "+dropper);
        player.getPersistentDataContainer().set(dropperKey, PersistentDataType.STRING, dropper);
        player.teleport(getDropperSpawn(dropper));
    }
}
