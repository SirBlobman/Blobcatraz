package com.SirBlobman.blobcatraz.listener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class PreLogin implements Listener 
{
	@EventHandler
	public void onLogin(AsyncPlayerPreLoginEvent e)
	{
		UUID uuid = e.getUniqueId();
		if(uuid == null || !ConfigDatabase.banned.containsKey(uuid)) return;
		Player p = Bukkit.getPlayer(uuid);
		if(p == null) return;
		
		long current = System.currentTimeMillis();
		long end = ConfigDatabase.getEndOfBan(p);
		String reason = ConfigDatabase.getBanReason(p);
		
		if(ConfigDatabase.isBanned(p))
		{
			if(ConfigDatabase.databaseConfig.get("players." + uuid + ".banned.length") == null) e.disallow(Result.KICK_OTHER, Util.banned + Util.color(reason));
			
			if(current < end) 
			{
				String lengthFormatted = ConfigDatabase.getEndOfBanFormatted(p);
				
				e.disallow(Result.KICK_OTHER, Util.banned + Util.color(reason) + "\n§9" + lengthFormatted);
			}
			else if(end < current) ConfigDatabase.unban(Util.blobcatrazUnformatted, p);
		}
	}
}