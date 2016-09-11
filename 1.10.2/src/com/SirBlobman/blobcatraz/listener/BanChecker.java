package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class BanChecker implements Listener 
{
	@EventHandler(priority=EventPriority.LOWEST)
	public void onLogin(PlayerLoginEvent e)
	{
		Player p = e.getPlayer();
		if(p == null) return;
		FileConfiguration db = ConfigDatabase.load(p);
		long current = System.currentTimeMillis();
		long end = ConfigDatabase.getEndOfBan(p);
		String reason = Util.format(ConfigDatabase.getBanReason(p));
		if(ConfigDatabase.isBanned(p))
		{
			reason = Util.format(reason);
			if(db.get("banned.length") == null)
			{
				e.disallow(Result.KICK_BANNED, Util.banned + reason);
				return;
			}
			if(current < end)
			{
				String format = ConfigDatabase.getEndOfBanFormatted(p);
				e.disallow(Result.KICK_BANNED, Util.banned + reason + "\n" + format);
				return;
			}
			if(end < current)
			{
				ConfigDatabase.unban("Blobcatraz", p);
				return;
			}
		}
		
		FileConfiguration config = ConfigBlobcatraz.load();
		
		if(config.getBoolean("unlimited players"))
		{
			Server s = Bukkit.getServer();
			if(s.hasWhitelist() && !s.getWhitelistedPlayers().contains(p)) return;
			
			e.setResult(Result.ALLOWED);
		}
	}
}