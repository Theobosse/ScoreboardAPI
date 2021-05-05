package fr.theobosse.scoreboardapi.api;

import fr.theobosse.scoreboardapi.ScoreboardAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class TScoreboard {

    private final ScoreboardManager manager;
    private final ScoreboardTitle title;
    private final ScoreboardLines lines;
    private final Player player;
    private final String name;

    private boolean cancel = false;
    private Objective objective;
    private Scoreboard board;


    public TScoreboard(String name, ScoreboardTitle title, ScoreboardLines lines, Player player, int refreshDelay) {
        this.manager = Bukkit.getScoreboardManager();
        this.player = player;
        this.name = name;

        assert manager != null;
        this.board = manager.getMainScoreboard();
        if (board.getObjective(name) == null)
            this.objective = board.registerNewObjective(name, "dummy", title.getTitle(player));
        else this.objective = board.getObjective(name);

        assert objective != null;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Scoreboards.addScoreboard(this);

        this.title = title;
        this.lines = lines;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (isCancel())
                    cancel();
                refresh();
            }
        }.runTaskTimer(ScoreboardAPI.instance, 0, refreshDelay);
    }

    public Scoreboard getScoreboard() {
        this.refresh();
        return board;
    }

    public Player getPlayer() {
        return player;
    }


    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public void refresh() {
        for (String entry : board.getEntries())
            board.resetScores(entry);

        objective.setDisplayName(title.getTitle(player));
        ArrayList<String> l = lines.getLines(player);
        for (int i = 0; i < l.size(); i++) {
            Score score = objective.getScore(l.get(i));
            score.setScore(l.size() - (i + 1));
        }

        player.setScoreboard(board);
    }

    public void remove() {
        Scoreboards.removeScoreboard(player.getUniqueId());
        board.clearSlot(DisplaySlot.SIDEBAR);
        cancel = true;
    }

}
