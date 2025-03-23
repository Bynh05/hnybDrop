package com.hyperfaststudio.hnybdrop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class HnybDrop extends JavaPlugin {

    private boolean confirmEnabled;
    private ConfigManager configManager;
    private UpdateChecker updateChecker;

    @Override
    public void onEnable() {
        // Lưu file config nếu chưa tồn tại
        saveDefaultConfig();

        // Tạo instance ConfigManager và load các thông báo
        configManager = new ConfigManager(this);
        configManager.loadMessages();

        // Lấy trạng thái bật/tắt chức năng từ config.yml
        confirmEnabled = getConfig().getBoolean("confirm-enabled", true);

        // Đăng ký listener sự kiện (DropListener)
        getServer().getPluginManager().registerEvents(new DropListener(this), this);

        // Đăng ký command với 2 prefix: hnybdrop và drop
        CommandHandler commandHandler = new CommandHandler(this);
        getCommand("hnybdrop").setExecutor(commandHandler);
        getCommand("drop").setExecutor(commandHandler);

        // Hiển thị banner ASCII theo trạng thái (xanh nếu bật, đỏ nếu tắt)
        displayBanner();

        // Kiểm tra cập nhật plugin từ GitHub (URL mẫu, hãy thay bằng URL repo của bạn)
        updateChecker = new UpdateChecker(this, "https://api.github.com/repos/YourRepo/hnybdrop/releases/latest");
        updateChecker.checkForUpdates();

        getLogger().info("Plugin hnybDrop đã được bật!");
    }

    @Override
    public void onDisable() {
        // Hiển thị banner ASCII với màu đỏ khi plugin tắt
        displayBanner();
        getLogger().info("Plugin hnybDrop đã được tắt!");
    }

    public boolean isConfirmEnabled() {
        return confirmEnabled;
    }

    public void setConfirmEnabled(boolean confirmEnabled) {
        this.confirmEnabled = confirmEnabled;
        getConfig().set("confirm-enabled", confirmEnabled);
        saveConfig();
        // Hiển thị lại banner theo trạng thái mới
        displayBanner();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    // Hiển thị banner ASCII theo trạng thái plugin: màu xanh nếu bật, màu đỏ nếu tắt
    public void displayBanner() {
        String banner = ChatColor.BOLD + "  _   _ _   _ _   _\n" +
                "██╗  ██╗███╗   ██╗██╗   ██╗██████╗ " +
                "██║  ██║████╗  ██║╚██╗ ██╔╝██╔══██╗" +
                "███████║██╔██╗ ██║ ╚████╔╝ ██████╔╝" +
                "██╔══██║██║╚██╗██║  ╚██╔╝  ██╔══██╗" +
                "██║  ██║██║ ╚████║   ██║   ██████╔╝" +
                "╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝   ╚═════╝ ";

        if (confirmEnabled) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + banner);
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + banner);
        }
    }
}
