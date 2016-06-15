package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.command.AFKCommand;

public class AFK implements Listener
{
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e)
	{
		boolean AFK = getAFKStatus(e.getPlayer());
		
		if(AFK)
		{
			AFKCommand.notAFK(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		boolean AFK = getAFKStatus(e.getPlayer());
		
		if(AFK)
		{
			AFKCommand.notAFK(e.getPlayer());
		}
	}
	
	private static boolean getAFKStatus(Player p)
	{
		return AFKCommand.afkConfig.getBoolean(p.getUniqueId().toString() + ".afk");
	}
}
