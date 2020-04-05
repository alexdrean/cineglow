package com.alex00.cineglow;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.alex00.cineglow.Cineglow.glowVision;
import static com.alex00.cineglow.Utils.parseBoolean;

public class GlowCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        try {
            boolean value;
            if (args.length == 0)
                value = !glowVision.hasVisionActivated(player);
            else
                value = parseBoolean(args[0]);
            glowVision.setVisionActivated(player, value);
            sender.sendMessage("§aSuccès");
        } catch (Exception e) {
            sender.sendMessage("§c" + e.getMessage());
        }
        return true;
    }
}
