package com.hyperfaststudio.hnybdrop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class NotifyListener implements Listener {

    private final HnybDrop plugin;

    public NotifyListener(HnybDrop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("hnybdrop.admin")) {
            player.sendMessage(ChatColor.GOLD + plugin.getUpdateStatus());
        }
    }
}
