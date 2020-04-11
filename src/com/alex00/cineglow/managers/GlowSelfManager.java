package com.alex00.cineglow.managers;

import com.alex00.cineglow.Cineglow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.inventivetalent.glow.GlowAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.alex00.cineglow.Utils.onoff;
import static org.bukkit.ChatColor.DARK_GREEN;
import static org.bukkit.ChatColor.RED;

public class GlowSelfManager extends GlowManager {
    @Override
    protected boolean isStillValid(@NotNull Player p, @Nullable Long enabledAt) {
        if (enabledAt == null) return false;
        if (p.hasPermission("cineglow.self.permanent"))
            return true;
        if (p.hasPermission("cineglow.self.temporary"))
            return enabledAt + 3000 > System.currentTimeMillis();
        return false;
    }

    @Override
    public boolean hasPermission(Player player) {
        return player.hasPermission("cineglow.self.permanent") || player.hasPermission("cineglow.self.temporary");
    }

    @Override
    protected boolean isCooldown(Player p, Long lastDisabled) {
        if (lastDisabled == null) return false;
        if (p.hasPermission("cineglow.self.nocooldown")) return false;
        return lastDisabled + 15000 > System.currentTimeMillis();
    }

    @Override
    public String getStateChangeMessage(boolean value) {
        ChatColor color = value ? DARK_GREEN : RED;
        return "§aGlow self " + color + onoff(value, false, false);
    }

    @Override
    String getStateUnchangedMessage(boolean value) {
        //noinspection SpellCheckingInspection
        return "Votre self glow est déjà " + onoff(value, false, false);
    }

    @Override
    String getCooldownMessage(Player p) {
        return "Vous devez attendre 15 secondes avant de réactiver votre /glow self";
    }

    @Override
    protected void updateActivated0(Player player) {
        GlowAPI.setGlowing(player, GlowAPI.Color.BLUE, Bukkit.getOnlinePlayers());
    }

    @Override
    protected void updateDeactivated0(Player player) {
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            if (!Cineglow.glowVision.isActivated(player))
                GlowAPI.setGlowing(player, null, viewer);
        }
    }

}
