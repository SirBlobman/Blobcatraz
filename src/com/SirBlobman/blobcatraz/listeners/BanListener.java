package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.Database;

public class BanListener implements Listener
{
	@EventHandler
	public static void onLogin(PlayerLoginEvent e)
	{
		Player p = e.getPlayer();
		if(Database.databaseConfig.getString("players." + p.getUniqueId().toString() + ".lastName") == null)
		{
			Database.writeDefaults(p.getUniqueId());
		}
		
		long current = System.currentTimeMillis();
		long end = Database.getEndOfBan(p.getUniqueId()).longValue();
		String reason = Database.getBanReason(p.getUniqueId());
		
		if(Database.isPlayerBanned(p.getUniqueId()))
		{
			if(Database.databaseConfig.get("players." + p.getUniqueId() + ".bannedLength") == null)
			{
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Util.blobcatraz + "§4You have been banned: \n§r" + Util.color(reason));
			}
			
			if(current < end)
			{
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Util.blobcatraz + "§4You have been banned: \n§r" + Util.color(reason) + "\n§9" + Database.getBanLengthFormatted(p.getUniqueId()));
			}
			else if(end < current)
			{
				Database.unban(p);
			}
		}
	}
}
