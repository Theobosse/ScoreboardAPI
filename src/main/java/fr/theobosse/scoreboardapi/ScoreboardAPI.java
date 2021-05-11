package fr.theobosse.scoreboardapi;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ScoreboardAPI extends JavaPlugin implements Listener {

    public static ScoreboardAPI instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
    }
}
