package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class AFK implements Listener
{
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		
		boolean AFK = ConfigDatabase.getAFKStatus(p);
		
		if(AFK) notAFK(p);
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		
		boolean AFK = ConfigDatabase.getAFKStatus(p);
		
		if(AFK) notAFK(p);
	}
	
	public void notAFK(Player p)
	{
		if(p == null) return;
		
		String name = p.getName();
		
		Bukkit.broadcastMessage("§6§l* §7" + name + " §6is no longer AFK");
		ConfigDatabase.setNotAFK(p);
	}
}