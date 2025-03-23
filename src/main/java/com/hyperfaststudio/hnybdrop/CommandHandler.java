package com.hyperfaststudio.hnybdrop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    private final HnybDrop plugin;

    public CommandHandler(HnybDrop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Chỉ cho phép người chơi sử dụng lệnh này
        if (!(sender instanceof Player)) {
            sender.sendMessage("Lệnh này chỉ dành cho người chơi.");
            return true;
        }
        Player player = (Player) sender;

        // Kiểm tra quyền sử dụng lệnh
        if (!player.hasPermission("hnybdrop.use")) {
            player.sendMessage(ChatColor.RED + plugin.getConfigManager().getMessage("no-permission"));
            return true;
        }

        // Nếu không có tham số, hiển thị hướng dẫn
        if (args.length < 1) {
            player.sendMessage(ChatColor.YELLOW + plugin.getConfigManager().getMessage("help-message"));
            return true;
        }

        String arg = args[0].toLowerCase();
        if (arg.equals("on")) {
            plugin.setConfirmEnabled(true);
            player.sendMessage(ChatColor.GREEN + plugin.getConfigManager().getMessage("enabled-message"));
        } else if (arg.equals("off")) {
            plugin.setConfirmEnabled(false);
            player.sendMessage(ChatColor.YELLOW + plugin.getConfigManager().getMessage("disabled-message"));
        } else {
            player.sendMessage(ChatColor.RED + plugin.getConfigManager().getMessage("invalid-argument"));
        }
        return true;
    }
}
