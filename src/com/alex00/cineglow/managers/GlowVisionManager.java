package com.alex00.cineglow.managers;

import com.alex00.cineglow.Cineglow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.inventivetalent.glow.GlowAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.alex00.cineglow.Utils.getGlowColor;
import static com.alex00.cineglow.Utils.onoff;
import static org.bukkit.ChatColor.DARK_GREEN;
import static org.bukkit.ChatColor.RED;

public class GlowVisionManager extends GlowManager {

    @Override
    protected boolean isStillValid(@NotNull Player p, @Nullable Long enabledAt) {
        if (enabledAt == null) return false;
        if (p.hasPermission("cineglow.vision.permanent"))
            return true;
        if (p.hasPermission("cineglow.vision.temporary"))
            return enabledAt + 3000 > System.currentTimeMillis();
        return false;
    }

    @Override
    public boolean hasPermission(Player player) {
        return player.hasPermission("cineglow.vision.permanent") || player.hasPermission("cineglow.vision.temporary");
    }

    @Override
    protected boolean isCooldown(Player p, Long lastDisabled) {
        if (lastDisabled == null) return false;
        if (p.hasPermission("cineglow.vision.nocooldown")) return false;
        return lastDisabled + 15000 > System.currentTimeMillis();
    }

    @Override
    public String getStateChangeMessage(boolean value) {
        ChatColor color = value ? DARK_GREEN : RED;
        return "§aGlow vision " + color + onoff(value, true, false);
    }

    @Override
    String getStateUnchangedMessage(boolean value) {
        //noinspection SpellCheckingInspection
        return "Votre vision glow est déjà " + onoff(value, true, false);
    }

    @Override
    String getCooldownMessage(Player p) {
        return "Vous devez attendre 15 secondes avant de réactiver votre /glow";
    }

    @Override
    protected void updateActivated0(Player player) {
        for (Player glowing : Bukkit.getOnlinePlayers())
            GlowAPI.setGlowing(glowing, getGlowColor(glowing, player), player);
    }

    @Override
    protected void updateDeactivated0(Player player) {
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            if (!Cineglow.glowSelf.isActivated(player))
                GlowAPI.setGlowing(viewer, null, player);
        }
    }


}
