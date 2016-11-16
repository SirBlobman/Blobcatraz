package com.SirBlobman.blobcatraz.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.SirBlobman.blobcatraz.utility.Util;

public class ScoreBoardManager 
{
	private static final Server S = Bukkit.getServer();
	private static final ScoreboardManager SBM = S.getScoreboardManager();
	private static final Scoreboard empty = SBM.getMainScoreboard();
	
	public static Objective getDefault(Player p)
	{
		Scoreboard s = SBM.getNewScoreboard();
		Objective o = s.registerNewObjective("default", "dummy");
		o.setDisplayName(Util.message("scoreboard.default.title"));
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		return o;
	}
	
	public static Scoreboard setCombat(Player p, int timeLeft)
	{
		Objective o = getDefault(p);
		Score combat = o.getScore(Util.message("scoreboard.combatlog"));
		combat.setScore(timeLeft);
		return o.getScoreboard();
	}
	
	public static void clear(Player p)
	{
		p.setScoreboard(empty);
	}
}