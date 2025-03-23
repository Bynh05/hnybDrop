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

                    String resp = response.toString();
                    String latestVersion = resp.split("\"tag_name\":\"")[1].split("\"")[0];
                    String currentVersion = plugin.getDescription().getVersion();

                    if (!currentVersion.equals(latestVersion)) {
                        String updateMessage = ChatColor.GOLD + "[hnybdrop] Có bản cập nhật mới: "
                                + latestVersion + ". Bạn đang sử dụng phiên bản: " + currentVersion;
                        ((HnybDrop) plugin).setUpdateStatus(updateMessage);
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            plugin.getServer().getConsoleSender().sendMessage(updateMessage);
                        });
                    } else {
                        ((HnybDrop) plugin).setUpdateStatus(ChatColor.GREEN + "[hnybdrop] Bạn đang sử dụng phiên bản mới nhất.");
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Không thể kiểm tra cập nhật: " + e.getMessage());
            }
        });
    }
}
