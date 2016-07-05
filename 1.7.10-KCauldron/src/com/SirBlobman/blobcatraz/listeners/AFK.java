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
		boolean AFK = ConfigDatabase.getAFKStatus(e.getPlayer());
		
		if(AFK) notAFK(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		boolean AFK = ConfigDatabase.getAFKStatus(e.getPlayer());
		
		if(AFK) notAFK(e.getPlayer());
	}
	
	private void notAFK(Player p)
	{
		if(p == null) return;
		
		String name = p.getName();
		
		Bukkit.broadcastMessage("§6§l* §7" + name + " §6is no longer AFK");
		ConfigDatabase.setNotAFK(p);
	}
}