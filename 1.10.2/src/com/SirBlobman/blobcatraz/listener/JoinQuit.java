package com.SirBlobman.blobcatraz.listener;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map.Entry;

import com.SirBlobman.blobcatraz.command.CommandMessage;
import com.SirBlobman.blobcatraz.scoreboard.ScoreBoardManager;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class JoinQuit implements Listener 
{
	@EventHandler
	public void join(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		
		String join = Util.getJoinMessage(p);
		e.setJoinMessage(join);
		p.sendMessage(Util.blobcatraz + Util.message("player.joinGamemode", p.getGameMode().name()));
		
		OfflinePlayer owner = PlayerUtil.getOwner();
		if(owner == null) return;
		if(!owner.isOnline()) return;
		PlayerUtil.ping((Player) owner); 
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		CommandMessage.reply.remove(p);
		for(Entry<CommandSender, CommandSender> entry : CommandMessage.reply.entrySet())
		{
			if(entry.getValue().equals(p)) CommandMessage.reply.remove(entry.getKey());
		}
		if(CombatLog.tagged.containsKey(p))
		{
			String display = p.getDisplayName();
			e.setQuitMessage(Util.message("player.combatLogQuit", display));
			p.setHealth(0);
			CombatLog.tagged.remove(0);
			ScoreBoardManager.setCombat(p, 0);
			return;
		}
		String quit = Util.getQuitMessage(p);
		e.setQuitMessage(quit);
	}
}