package com.alex00.cineglow;

import net.minecraft.server.v1_15_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;

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
        return (value ? "activé" : "désactivé") + feminine + plural;
    }

    // fixme
    public static Collection<? extends Player> getNearbyPlayers(CraftPlayer cp) {
        EntityPlayer p = cp.getHandle();
        return Bukkit.getOnlinePlayers();
    }

}
