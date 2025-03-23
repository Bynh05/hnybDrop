package com.hyperfaststudio.hnybdrop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class HnybDrop extends JavaPlugin {

    private boolean confirmEnabled;
    private ConfigManager configManager;
    private UpdateChecker updateChecker;
    private String updateStatus = ChatColor.GREEN + "[hnybdrop] Bạn đang sử dụng phiên bản mới nhất.";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        configManager.loadMessages();
        confirmEnabled = getConfig().getBoolean("confirm-enabled", true);
        getServer().getPluginManager().registerEvents(new DropListener(this), this);
        getServer().getPluginManager().registerEvents(new NotifyListener(this), this);

        CommandHandler commandHandler = new CommandHandler(this);
        getCommand("hnybdrop").setExecutor(commandHandler);
        getCommand("hnybdrop").setTabCompleter(commandHandler);
        getCommand("drop").setExecutor(commandHandler);
        getCommand("drop").setTabCompleter(commandHandler);

        displayBanner();
        updateChecker = new UpdateChecker(this, "https://api.github.com/repos/YourRepo/hnybdrop/releases/latest");
        updateChecker.checkForUpdates();
        getLogger().info("Plugin hnybDrop đã được bật!");
    }

    @Override
    public void onDisable() {
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
        displayBanner();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public void displayBanner() {
        String banner = ChatColor.BOLD +
                "██╗  ██╗███╗   ██╗██╗   ██╗██████╗\n" +
                "██║  ██║████╗  ██║╚██╗ ██╔╝██╔══██╗\n" +
                "███████║██╔██╗ ██║ ╚████╔╝ ██████╔╝\n" +
                "██╔══██║██║╚██╗██║  ╚██╔╝  ██╔══██╗\n" +
                "██║  ██║██║ ╚████║   ██║   ██████╔╝\n" +
                "╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝   ╚═════╝";

        if (confirmEnabled) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + banner);
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + banner);
        }
    }
}
