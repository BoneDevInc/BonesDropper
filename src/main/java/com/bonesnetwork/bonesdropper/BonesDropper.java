package com.bonesnetwork.bonesdropper;

import com.bonesnetwork.bonesdropper.Command.DropperCommand;
import com.bonesnetwork.bonesdropper.Listeners.PlayerJoinEventListener;
import com.bonesnetwork.bonesdropper.Listeners.PlayerPortalEventListener;
import com.bonesnetwork.bonesdropper.Util.DropperUtil;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class BonesDropper extends JavaPlugin {

    private static BonesDropper bonesDropper;
    public static boolean debugMode;
    @Override
    public void onEnable() {
        bonesDropper = this;
        PluginManager pluginManager = this.getServer().getPluginManager();
        try {
            ConfigManager.setup(this);
            LangManager.setCurrentLanguage(ConfigManager.mainConfig.getString("Language"));
        } catch (IOException e) {
            pluginManager.disablePlugin(this);
            throw new RuntimeException(e);
        }
        
        //Register Commands
        getCommand("bdropper").setExecutor(new DropperCommand());
        getCommand("bdropper").setTabCompleter(new DropperCommand());
        //Register Events
        pluginManager.registerEvents(new PlayerJoinEventListener(),this);
        pluginManager.registerEvents(new PlayerPortalEventListener(), this);
    }

    @Override
    public void onDisable() {
    
    }
    
    public static BonesDropper getPlugin() {
        return bonesDropper;
    }
}
