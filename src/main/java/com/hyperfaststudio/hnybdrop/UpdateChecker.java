package com.hyperfaststudio.hnybdrop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {
    private final JavaPlugin plugin;
    private final String urlString;

    public UpdateChecker(JavaPlugin plugin, String urlString) {
        this.plugin = plugin;
        this.urlString = urlString;
    }

    public void checkForUpdates() {
        // Chạy kiểm tra cập nhật trên luồng bất đồng bộ
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Giả sử JSON trả về có trường "tag_name" chứa phiên bản mới nhất
                    String resp = response.toString();
                    String latestVersion = resp.split("\"tag_name\":\"")[1].split("\"")[0];
                    String currentVersion = plugin.getDescription().getVersion();

                    if (!currentVersion.equals(latestVersion)) {
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            plugin.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[hnybdrop] Có bản cập nhật mới: "
                                    + latestVersion + ". Bạn đang sử dụng phiên bản: " + currentVersion);
                        });
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Không thể kiểm tra cập nhật: " + e.getMessage());
            }
        });
    }
}
