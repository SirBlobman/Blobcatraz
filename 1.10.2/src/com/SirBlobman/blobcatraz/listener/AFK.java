package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class AFK implements Listener
{
	@EventHandler
	public void move(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		boolean AFK = ConfigDatabase.isAFK(p);
		if(AFK) notAFK(p);
	}
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		boolean AFK = ConfigDatabase.isAFK(p);
		if(AFK) notAFK(p);
	}
	
	private void notAFK(Player p)
	{
		if(p == null) return;
		String name = p.getName();
		Bukkit.broadcastMessage(Util.message("player.notAFK", name));
		ConfigDatabase.setAFK(p, false);
	}
}