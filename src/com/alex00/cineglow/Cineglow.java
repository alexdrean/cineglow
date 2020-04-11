package com.alex00.cineglow;

import com.alex00.cineglow.managers.GlowSelfManager;
import com.alex00.cineglow.managers.GlowVisionManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Cineglow extends JavaPlugin {

    public static GlowVisionManager glowVision;
    public static GlowSelfManager glowSelf;
    public static Cineglow plugin;

    @Override
    public void onEnable() {
        plugin = this;
        glowVision = new GlowVisionManager();
        glowSelf = new GlowSelfManager();

        Objects.requireNonNull(getCommand("cineglow")).setExecutor(new GlowCommandExecutor());

        getServer().getPluginManager().registerEvents(new SettingsRestorer(), this);
        getServer().getPluginManager().registerEvents(glowVision, this);
        getServer().getPluginManager().registerEvents(glowSelf, this);

        glowVision.runTaskTimer(this, 5, 5);
        glowSelf.runTaskTimer(this, 5, 5);
    }

}
