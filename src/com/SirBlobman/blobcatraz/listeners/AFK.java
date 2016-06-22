package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.command.AFKCommand;
import com.SirBlobman.blobcatraz.config.Database;

public class AFK implements Listener
{
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e)
	{
		boolean AFK = Database.getAFKStatus(e.getPlayer());
		
		if(AFK)
		{
			AFKCommand.notAFK(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		boolean AFK = Database.getAFKStatus(e.getPlayer());
		
		if(AFK)
		{
			AFKCommand.notAFK(e.getPlayer());
		}
	}
}
