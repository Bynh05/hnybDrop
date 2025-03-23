package com.hyperfaststudio.hnybdrop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DropListener implements Listener {

    private final HnybDrop plugin;
    private final Map<UUID, Long> pendingDrop = new HashMap<>();
    private final long confirmInterval;

    public DropListener(HnybDrop plugin) {
        this.plugin = plugin;
        this.confirmInterval = plugin.getConfig().getLong("confirm-interval", 10000);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (!plugin.isConfirmEnabled()) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        long now = System.currentTimeMillis();

        if (pendingDrop.containsKey(uuid)) {
            long firstAttempt = pendingDrop.get(uuid);
            if (now - firstAttempt <= confirmInterval) {
                pendingDrop.remove(uuid);
                player.sendMessage(plugin.getConfigManager().getMessage("drop-confirmed"));
                return;
            } else {
                pendingDrop.remove(uuid);
            }
        }
        event.setCancelled(true);
        pendingDrop.put(uuid, now);
        player.sendMessage(plugin.getConfigManager().getMessage("drop-first-attempt"));
    }
}
