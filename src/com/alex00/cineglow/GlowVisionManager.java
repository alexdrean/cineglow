package com.alex00.cineglow;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.glow.GlowAPI;

import java.util.HashSet;
import java.util.UUID;

import static com.alex00.cineglow.Utils.getNearbyPlayers;
import static com.alex00.cineglow.Utils.onoff;

public class GlowVisionManager extends BukkitRunnable implements Listener {

    private HashSet<UUID> visionActivated = new HashSet<>();
    private HashSet<UUID> visionDeactivated = new HashSet<>();

    public boolean hasVisionActivated(Player p) {
        return visionActivated.contains(p.getUniqueId());
    }

    public void setVisionActivated(Player p, boolean value) {
        boolean success;
        if (value) {
            success = visionActivated.add(p.getUniqueId());
            visionDeactivated.remove(p.getUniqueId());
        } else {
            success = visionActivated.remove(p.getUniqueId());
            if (success) visionDeactivated.add(p.getUniqueId());
        }
        if (!success)
            throw new IllegalArgumentException("Votre vision glow est déjà " + onoff(value, true, false));
        // TODO update
    }

    @Override
    public void run() {
        updateActivated();
        for (UUID uuid : visionDeactivated) {
            Player player = Bukkit.getPlayer(uuid);
            // fixme use list of players that have been set as glowing (maybe using GlowData?)
            for (Player glowing : Bukkit.getOnlinePlayers()) {
                GlowAPI.setGlowing(glowing, false, player);
            }
        }
    }

    private void updateActivated() {
        for (UUID uuid : visionActivated) {
            Player player = Bukkit.getPlayer(uuid);
            if (!(player instanceof CraftPlayer)) continue;
            for (Player visible : getNearbyPlayers((CraftPlayer) player)) {
                GlowAPI.setGlowing(visible, true, player);
            }
        }
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        visionActivated.remove(e.getPlayer().getUniqueId());
        visionDeactivated.remove(e.getPlayer().getUniqueId());
    }
}
