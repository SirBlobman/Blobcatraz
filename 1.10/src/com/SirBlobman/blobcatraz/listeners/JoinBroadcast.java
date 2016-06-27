package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.Database;

public class JoinBroadcast implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		if(Database.databaseConfig.getString("players." + p.getUniqueId().toString() + ".lastName") == null)
		{
			Database.writeDefaults(p.getUniqueId());
		}
		
		e.setJoinMessage("§1" + e.getPlayer().getName() + "§r joined the server!");
		p.sendMessage(Util.blobcatraz + "You are in §2" + e.getPlayer().getGameMode());
	}
}
