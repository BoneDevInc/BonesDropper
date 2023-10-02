package com.bonesnetwork.bonesdropper.Listeners;

import com.bonesnetwork.bonesdropper.BonesDropper;
import com.bonesnetwork.bonesdropper.ConfigManager;
import com.bonesnetwork.bonesdropper.Util.DropperUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinEventListener implements Listener {
    
    
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        if(!BonesDropper.debugMode) {
            Player player = event.getPlayer();
            NamespacedKey gamemodeKey = new NamespacedKey(BonesDropper.getPlugin(), "gamemode");
            NamespacedKey dropperKey = new NamespacedKey(BonesDropper.getPlugin(), "dropper");
            player.getPersistentDataContainer().set(gamemodeKey, PersistentDataType.STRING, "competitive");
            player.getPersistentDataContainer().set(dropperKey, PersistentDataType.STRING, DropperUtil.getAllDroppers().get(0));
            DropperUtil.teleportPlayerToDropperSpawn(player);
        }
    }
}
