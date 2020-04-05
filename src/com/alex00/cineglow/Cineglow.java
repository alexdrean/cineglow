package com.alex00.cineglow;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Cineglow extends JavaPlugin {

    public static GlowVisionManager glowVision;

    @Override
    public void onEnable() {
        glowVision = new GlowVisionManager();

        Objects.requireNonNull(getCommand("cineglow")).setExecutor(new GlowCommandExecutor());

        getServer().getPluginManager().registerEvents(new SettingsRestorer(), this);
        getServer().getPluginManager().registerEvents(glowVision, this);

        glowVision.runTaskTimer(this, 5, 5);
    }

}
