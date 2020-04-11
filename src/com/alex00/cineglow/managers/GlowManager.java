package com.alex00.cineglow.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public abstract class GlowManager extends BukkitRunnable implements Listener {

    // Methods
    protected abstract boolean isStillValid(Player p, Long enabledAt);
    public abstract boolean hasPermission(Player player);
    protected abstract boolean isCooldown(Player p, Long lastDisabled);
    // Messages

    public abstract String getStateChangeMessage(boolean value);
    abstract String getStateUnchangedMessage(boolean value);
    abstract String getCooldownMessage(Player p);


    // Abstract logic
    private HashMap<UUID, Long> activated = new HashMap<>();
    private HashSet<UUID> deactivated = new HashSet<>();
    private HashMap<UUID, Long> cooldown = new HashMap<>();


    public boolean isActivated(Player p) {
        return isStillValid(p, activated.get(p.getUniqueId()));
    }

    public void setActivated(Player p, boolean value) {
        boolean success;
        if (value) {
            if (isCooldown(p, cooldown.get(p.getUniqueId())))
                throw new IllegalStateException(getCooldownMessage(p));
            success = !activated.containsKey(p.getUniqueId());
            if (success) {
                activated.put(p.getUniqueId(), System.currentTimeMillis());
                deactivated.remove(p.getUniqueId());
            }
        } else {
            success = activated.remove(p.getUniqueId()) != null;
            if (success) {
                deactivated.add(p.getUniqueId());
            }
        }
        if (!success)
            throw new IllegalArgumentException(getStateUnchangedMessage(value));
    }

    private void updateDeactivated() {
        for (UUID uuid : deactivated) {
            Player player = Bukkit.getPlayer(uuid);
            updateDeactivated0(player);
            cooldown.put(uuid, System.currentTimeMillis());
        }
        deactivated.clear();
    }

    protected abstract void updateDeactivated0(Player player);

    private void updateActivated() {
        Iterator<Map.Entry<UUID, Long>> iterator = activated.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, Long> next = iterator.next();

            Player player = Bukkit.getPlayer(next.getKey());
            if (player == null) {
                iterator.remove();
                continue;
            }

            if (!isStillValid(player, next.getValue())) {
                iterator.remove();
                deactivated.add(next.getKey());
                continue;
            }

            updateActivated0(player);
        }
    }

    protected abstract void updateActivated0(Player player);

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        activated.remove(e.getPlayer().getUniqueId());
        deactivated.remove(e.getPlayer().getUniqueId());
        cooldown.remove(e.getPlayer().getUniqueId());
    }

    @Override
    public void run() {
        updateActivated();
        updateDeactivated();
    }
}
