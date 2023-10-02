package com.bonesnetwork.bonesdropper.Listeners;

import com.bonesnetwork.bonesdropper.BonesDropper;
import com.bonesnetwork.bonesdropper.Util.DropperUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerPortalEventListener implements Listener {
    
    @EventHandler
    public void playerPortalEvent(PlayerPortalEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        NamespacedKey gamemodeKey = new NamespacedKey(BonesDropper.getPlugin(), "gamemode");
        if(player.getPersistentDataContainer().get(gamemodeKey, PersistentDataType.STRING).equalsIgnoreCase("competitive")) {
            Bukkit.getLogger().info("Reached Teleport Player To Next Dropper");
            DropperUtil.teleportPlayerToNextDropper(player);
        } else {
            //TODO
        }
    }
}
