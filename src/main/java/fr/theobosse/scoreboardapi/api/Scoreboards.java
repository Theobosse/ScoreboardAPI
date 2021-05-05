package fr.theobosse.scoreboardapi.api;

import java.util.HashMap;
import java.util.UUID;

public class Scoreboards {

    private final static HashMap<UUID, TScoreboard> scoreboards = new HashMap<>();

    public static HashMap<UUID, TScoreboard> getScoreboards() {
        return scoreboards;
    }

    public static TScoreboard getScoreboard(UUID uuid) {
        return scoreboards.get(uuid);
    }

    public static void addScoreboard(TScoreboard board) {
        scoreboards.put(board.getPlayer().getUniqueId(), board);
    }

    public static void removeScoreboard(UUID player) {
        scoreboards.remove(player);
    }
}
