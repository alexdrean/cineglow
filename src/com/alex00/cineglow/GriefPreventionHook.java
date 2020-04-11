package com.alex00.cineglow;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.glow.GlowAPI;

public class GriefPreventionHook {
    public static GlowAPI.Color getGlowColor(Player subject, Player viewer) {
        DataStore data = JavaPlugin.getPlugin(GriefPrevention.class).dataStore;

        // Si viewer a accès au claim dans lequel subject est
        //fixme cachedclaim
        Claim claim = data.getClaimAt(subject.getLocation(), false, null);
        if (claim == null) return null;
        if (claim.allowContainers(viewer) != null)
            return null;

        return GlowAPI.Color.GREEN;
    }
}
