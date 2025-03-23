package com.hyperfaststudio.hnybdrop;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DropListener implements Listener {

    private final HnybDrop plugin;
    // Lưu thời gian nhấn vứt lần đầu cho từng người chơi
    private final Map<UUID, Long> pendingDrop = new HashMap<>();
    private final long confirmInterval;

    public DropListener(HnybDrop plugin) {
        this.plugin = plugin;
        // Lấy khoảng thời gian xác nhận từ config.yml (mặc định 10 giây nếu chưa cấu hình)
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
                // Đã xác nhận drop
                pendingDrop.remove(uuid);
                player.sendMessage(ChatColor.GREEN + plugin.getConfigManager().getMessage("drop-confirmed"));
                return; // Cho phép drop
            } else {
                // Hết hạn thời gian, reset lại
                pendingDrop.remove(uuid);
            }
        }
        // Hủy sự kiện drop và lưu thời gian nhấn drop đầu tiên
        event.setCancelled(true);
        pendingDrop.put(uuid, now);
        player.sendMessage(ChatColor.YELLOW + plugin.getConfigManager().getMessage("drop-first-attempt"));
    }
}
