package com.hyperfaststudio.hnybdrop;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private JavaPlugin plugin;
    private Map<String, String> messages = new HashMap<>();

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadMessages() {
        FileConfiguration config = plugin.getConfig();
        messages.put("drop-first-attempt", config.getString("messages.drop-first-attempt", "&eNhấn vứt lần đầu, sau đó nhấn lại trong 10 giây để xác nhận vứt."));
        messages.put("drop-confirmed", config.getString("messages.drop-confirmed", "&aBạn đã xác nhận vứt vật phẩm."));
        messages.put("enabled-message", config.getString("messages.enabled-message", "&aChức năng xác nhận vứt đã được bật."));
        messages.put("disabled-message", config.getString("messages.disabled-message", "&cChức năng xác nhận vứt đã được tắt."));
        messages.put("help-message", config.getString("messages.help-message",
                "- &6/hnybdrop on: &aBật chức năng xác nhận vứt.\n" +
                        "- &6/hnybdrop off: &cTắt chức năng xác nhận vứt.\n" +
                        "- &6/hnybdrop reload: &eReload cấu hình plugin (yêu cầu perm hnybdrop.admin).\n" +
                        "- &6/hnybdrop checkupdate: &eKiểm tra cập nhật plugin (yêu cầu perm hnybdrop.admin).\n" +
                        "- &6/drop: &7Alias của lệnh hnybdrop."
        ));
        messages.put("no-permission", config.getString("messages.no-permission", "&cBạn không có quyền sử dụng lệnh này."));
        messages.put("invalid-argument", config.getString("messages.invalid-argument", "&cLệnh không hợp lệ."));
    }

    public String getMessage(String key) {
        String message = messages.getOrDefault(key, "Undefined message for key: " + key);
        return ColorUtil.translateColors(message);
    }
}
