package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AFK implements Listener
{
	public static boolean AFK;
	
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e)
	{
		if(AFK == true)
		{
			e.getPlayer().performCommand("not-afk");
			AFK = false;
		}
	}
}
