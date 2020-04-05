package com.alex00.cineglow;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SettingsRestorer implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO restore settings from MySQL
    }

}
