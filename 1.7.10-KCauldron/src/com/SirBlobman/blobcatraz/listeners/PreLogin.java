package com.SirBlobman.blobcatraz.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class PreLogin implements Listener
{
	@EventHandler
	public static void onLogin(AsyncPlayerPreLoginEvent e)
	{
		UUID uuid = e.getUniqueId();
		if(uuid == null) return;
		
		long current = System.currentTimeMillis();
		long end = ConfigDatabase.getEndOfBan(uuid);
		String reason = ConfigDatabase.getBanReason(uuid);
		
		if(ConfigDatabase.isPlayerBanned(uuid))
		{
			if(ConfigDatabase.databaseConfig.get("players." + uuid.toString() + ".bannedLength") == null)
			{
				e.disallow(Result.KICK_OTHER, Util.blobcatraz + "§4You have been banned: \n§r" + Util.color(reason));
			}
			
			if(current < end)
			{
				e.disallow(Result.KICK_OTHER, Util.blobcatraz + "§4You have been banned: \n§r" + Util.color(reason) + "\n§9" + ConfigDatabase.getBanLengthFormatted(uuid));
			}
			else if(end < current)
			{
				ConfigDatabase.unban(Bukkit.getPlayer(uuid));
			}
		}
	}
}