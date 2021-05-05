package fr.theobosse.scoreboardapi;

import org.bukkit.plugin.java.JavaPlugin;

public class ScoreboardAPI extends JavaPlugin {

    public static ScoreboardAPI instance;

    @Override
    public void onEnable() {
        instance = this;
    }
}
