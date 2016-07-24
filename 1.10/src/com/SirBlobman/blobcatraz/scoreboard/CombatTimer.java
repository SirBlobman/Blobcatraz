package com.SirBlobman.blobcatraz.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.SirBlobman.blobcatraz.Blobcatraz;

public class CombatTimer
{
	Blobcatraz plugin;
	public static CombatTimer instance;
	
	ScoreboardManager sbm = Bukkit.getScoreboardManager();
	Scoreboard sb = sbm.getNewScoreboard();
	Objective timer = sb.registerNewObjective("Timer", "dummy");
	
	public CombatTimer(Blobcatraz pl)
	{
		instance = this;
		plugin = pl;
		timer.setDisplayName("§2§ki§bCombat Log§2§ki");
		timer.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public void setTimer(Player p, int seconds)
	{
		Score score = timer.getScore("§2Time Left: ");
		score.setScore(seconds);
		p.setScoreboard(sb);
	}
	
	public void clearTimer(Player p)
	{
		if(p.getScoreboard().equals(sb))
		{
			p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		}
	}
}