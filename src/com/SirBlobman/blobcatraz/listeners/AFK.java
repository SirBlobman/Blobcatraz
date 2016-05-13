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
		boolean AFK = AFKCommandHandler.afk.getBoolean(e.getPlayer().getName() + ".afk");
		
		if(AFK == true)
		{
			e.getPlayer().performCommand("not-afk");
			AFKCommandHandler.afk.set(e.getPlayer().getName() + ".afk", false);
		}
	}
}
