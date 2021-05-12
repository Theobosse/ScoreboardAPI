package fr.theobosse.scoreboardapi.api;

import fr.theobosse.scoreboardapi.ScoreboardAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class TScoreboard {

    private final ScoreboardManager manager;
    private final ScoreboardData data;
    private final Player player;

    private boolean cancel = false;
    private Objective objective;
    private Scoreboard board;
    private ChatColor color;


    public TScoreboard(ScoreboardData data, Player player, int refreshDelay) {
        this.manager = Bukkit.getScoreboardManager();
        this.player = player;

        assert manager != null;
        this.board = manager.getNewScoreboard();
        if (board.getObjective(player.getName()) == null)
            this.objective = board.registerNewObjective(player.getName(), "dummy", data.getTitle(player));
        else this.objective = board.getObjective(player.getName());

        assert objective != null;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Scoreboards.addScoreboard(this);
        this.data = data;

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

    public ChatColor getColor() {
        return color;
    }

    public Objective getObjective() {
        return objective;
    }

    public ScoreboardData getData() {
        return data;
    }

    public Scoreboard getBoard() {
        return board;
    }

    public ScoreboardManager getManager() {
        return manager;
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

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public void refresh() {
        for (String entry : board.getEntries())
            board.resetScores(entry);

        if (color != null) {
            Team team;
            if (board.getTeam("TEAM") == null) {
                team = board.registerNewTeam("TEAM");
                team.addPlayer(player);
            } else {
                team = board.getTeam("TEAM");
            }
            team.setColor(color);
        }

        objective.setDisplayName(data.getTitle(player));
        ArrayList<String> l = data.getLines(player);
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
