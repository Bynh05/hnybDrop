package com.hyperfaststudio.hnybdrop;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final JavaPlugin plugin;
    private final Map<String, String> messages = new HashMap<>();

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadMessages() {
        FileConfiguration config = plugin.getConfig();
        messages.put("drop-first-attempt", config.getString("messages.drop-first-attempt", "Nhấn vứt lần đầu, sau đó nhấn lại trong 10 giây để xác nhận vứt."));
        messages.put("drop-confirmed", config.getString("messages.drop-confirmed", "Bạn đã xác nhận vứt vật phẩm."));
        messages.put("enabled-message", config.getString("messages.enabled-message", "Chức năng xác nhận vứt đã được bật."));
        messages.put("disabled-message", config.getString("messages.disabled-message", "Chức năng xác nhận vứt đã được tắt."));
        messages.put("help-message", config.getString("messages.help-message", "Cách dùng: /<command> <on|off>"));
        messages.put("no-permission", config.getString("messages.no-permission", "Bạn không có quyền sử dụng lệnh này."));
        messages.put("invalid-argument", config.getString("messages.invalid-argument", "Tham số không hợp lệ. Sử dụng: /<command> <on|off>"));
    }

    public String getMessage(String key) {
        return messages.getOrDefault(key, "Undefined message for key: " + key);
    }
}
