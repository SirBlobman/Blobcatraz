package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.command.AFKCommandHandler;

public class AFK implements Listener
{
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e)
	{
		boolean AFK = AFKCommand.afkConfig.getBoolean(e.getPlayer().getName() + ".afk");
		
		if(AFK == true)
		{
			AFKCommand.notAFK(e.getPlayer());
		}
	}
}
