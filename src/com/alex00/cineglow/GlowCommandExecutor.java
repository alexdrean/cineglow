package com.alex00.cineglow;

import com.alex00.cineglow.managers.GlowManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ArrayUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.alex00.cineglow.Cineglow.glowSelf;
import static com.alex00.cineglow.Cineglow.glowVision;
import static com.alex00.cineglow.Utils.parseBoolean;

public class GlowCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length > 0 && args[0].equalsIgnoreCase("self")) {
            glow(glowSelf, player, ArrayUtils.subarray(args, 1, 10));
        } else {
            glow(glowVision, player, args);
        }
        return true;
    }

    private void glow(GlowManager glowManager, Player player, @NotNull String[] args) {
        try {
            if (!glowManager.hasPermission(player)) {
                //noinspection SpellCheckingInspection
                player.sendMessage("§cCette commande est réservée aux VIP. Pensez à visiter §fshop.cinecu.be§c pour nous soutenir !");
                return;
            }
            boolean value;
            if (args.length == 0)
                value = !glowManager.isActivated(player);
            else
                value = parseBoolean(args[0]);
            glowManager.setActivated(player, value);
            player.sendMessage(glowManager.getStateChangeMessage(value));
        } catch (Exception e) {
            player.sendMessage("§c" + e.getMessage());
        }
    }

}
