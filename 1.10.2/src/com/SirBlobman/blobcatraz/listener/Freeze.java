package com.SirBlobman.blobcatraz.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class Freeze implements Listener 
{
	
	@EventHandler
	public void freeze(PlayerMoveEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		boolean f = ConfigDatabase.isFrozen(p);
		if(f)
		{
			e.setCancelled(true);
			try{PlayerUtil.sendAction1_10(p, Util.message("player.isFrozen"));}
			catch(Exception | Error ex) {p.sendMessage(Util.blobcatraz + Util.message("player.isFrozen"));}
		}
	}
}