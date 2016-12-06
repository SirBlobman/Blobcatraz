package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenAFK implements Listener
{
	@EventHandler
	public void afk(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		boolean afk = ConfigDatabase.afk(p);
		if(afk) not(p);
	}
	
	@EventHandler
	public void afk(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		boolean afk = ConfigDatabase.afk(p);
		if(afk) not(p);
	}
	
	private void not(Player p)
	{
		if(p == null) return;
		String name = p.getName();
		String msg = Util.option("command.afk.not", name);
		Bukkit.broadcastMessage(msg);
		ConfigDatabase.afk(p, false);
	}
}