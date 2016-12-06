package com.SirBlobman.blobcatraz.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenFreeze implements Listener
{
	@EventHandler
	public void freeze(PlayerMoveEvent e)
	{
		if(e.isCancelled()) return;
		
		Player p = e.getPlayer();
		boolean frozen = ConfigDatabase.frozen(p);
		if(frozen)
		{
			e.setCancelled(true);
			String msg = Util.option("player.frozen");
			PlayerUtil.action1_11(p, msg);
		}
	}
}