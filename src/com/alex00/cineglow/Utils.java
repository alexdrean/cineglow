package com.alex00.cineglow;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.inventivetalent.glow.GlowAPI;
import org.jetbrains.annotations.Nullable;

public class Utils {

    public static boolean parseBoolean(String s) throws IllegalArgumentException {
        switch (s.toLowerCase()) {
            case "on":
            case "1":
            case "oui":
            case "true":
                return true;
            case "off":
            case "0":
            case "non":
            case "false":
                return false;
            default:
                throw new IllegalArgumentException("\"" + s + "\" n'est pas \"on\" ou \"off\"");
        }
    }

    public static String onoff(boolean value, boolean feminine, boolean plural) {
        String f = feminine ? "e" : "";
        String p = plural ? "s" : "";
        return (value ? "activé" : "désactivé") + f + p;
    }

    @Nullable
    public static GlowAPI.Color getGlowColor(Player subject, Player viewer) {
        if (subject.getUniqueId().equals(viewer.getUniqueId()))
            return null;
        try {
            if (Bukkit.getPluginManager().isPluginEnabled("GriefPrevention")) {
                return GriefPreventionHook.getGlowColor(subject, viewer);
            }
        } catch (Exception ignored) {}
        return null;
    }

}
