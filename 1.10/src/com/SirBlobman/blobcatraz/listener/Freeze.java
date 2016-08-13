package com.SirBlobman.blobcatraz.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class Freeze implements Listener
{
	@EventHandler
	public void onAttemptMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		ConfigDatabase.load(p);
		boolean frozen = ConfigDatabase.isFrozen(p);
		
		if(frozen) 
		{
			e.setCancelled(true);
			p.sendMessage(Util.blobcatraz + "You are §bFROZEN§r! That means you can't move");
		}
	}
}