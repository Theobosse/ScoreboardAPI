package fr.theobosse.scoreboardapi.api;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface ScoreboardData {

    public abstract String getTitle(Player player);

    public abstract ArrayList<String> getLines(Player player);

}
