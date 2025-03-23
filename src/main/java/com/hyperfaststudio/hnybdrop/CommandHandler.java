package com.hyperfaststudio.hnybdrop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final HnybDrop plugin;

    public CommandHandler(HnybDrop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Lệnh này chỉ dành cho người chơi.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("hnybdrop.use")) {
            player.sendMessage(ChatColor.RED + plugin.getConfigManager().getMessage("no-permission"));
            return true;
        }
        if (args.length == 0) {
            String rawHelpMessage = plugin.getConfig().getString("messages.help-message");
            String coloredHelpMessage = ColorUtil.translateColors(rawHelpMessage);
            player.sendMessage(coloredHelpMessage);
            return true;
        }
        String arg = args[0].toLowerCase();
        switch (arg) {
            case "on":
                plugin.setConfirmEnabled(true);
                player.sendMessage(ChatColor.GREEN + plugin.getConfigManager().getMessage("enabled-message"));
                break;
            case "off":
                plugin.setConfirmEnabled(false);
                player.sendMessage(ChatColor.YELLOW + plugin.getConfigManager().getMessage("disabled-message"));
                break;
            case "reload":
                if (!player.hasPermission("hnybdrop.admin")) {
                    player.sendMessage(ChatColor.RED + "Bạn không có quyền sử dụng lệnh này.");
                    return true;
                }
                plugin.reloadConfig();
                plugin.getConfigManager().loadMessages();
                player.sendMessage(ChatColor.GREEN + "[hnybdrop] Reload config thành công.");
                break;
            case "checkupdate":
                if (!player.hasPermission("hnybdrop.admin")) {
                    player.sendMessage(ChatColor.RED + "Bạn không có quyền sử dụng lệnh này.");
                    return true;
                }
                player.sendMessage(ChatColor.GOLD + "[hnybdrop] Đang kiểm tra cập nhật...");
                plugin.getLogger().info("Đang kiểm tra cập nhật theo lệnh /hnybdrop checkupdate từ " + player.getName());
                plugin.getUpdateChecker().checkForUpdates();
                player.sendMessage(plugin.getUpdateStatus());
                break;
            default:
                player.sendMessage(ChatColor.RED + plugin.getConfigManager().getMessage("invalid-argument"));
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            String current = args[0].toLowerCase();
            if ("on".startsWith(current)) completions.add("on");
            if ("off".startsWith(current)) completions.add("off");
            if (sender.hasPermission("hnybdrop.admin")) {
                if ("reload".startsWith(current)) completions.add("reload");
                if ("checkupdate".startsWith(current)) completions.add("checkupdate");
            }
        }
        return completions;
    }
}
