package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class Login implements Listener 
{
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onLogin(PlayerLoginEvent e)
	{
		Util.print(e.getEventName());
		Player p = e.getPlayer();
		ConfigDatabase.load(p);
		if(p == null) return;
		
		long current = System.currentTimeMillis();
		long end = ConfigDatabase.getEndOfBan(p);
		String reason = ConfigDatabase.getBanReason(p);
		
		if(ConfigDatabase.isBanned(p))
		{
			if(ConfigDatabase.load(p).get("banned.length") == null) 
			{
				Util.print(p.getName() + " is perma-banned");
				e.disallow(Result.KICK_OTHER, Util.banned + Util.color(reason));
				return;
			}
			
			if(current < end) 
			{
				String lengthFormatted = ConfigDatabase.getEndOfBanFormatted(p);
				
				Util.print(p.getName() + " is temp-banned");
				e.disallow(Result.KICK_OTHER, Util.banned + Util.color(reason) + "\n§9" + lengthFormatted);
				return;
			}
			if(end < current) 
			{
				Util.print(p.getName() + "'s ban is over!");
				ConfigDatabase.unban(Util.blobcatrazUnformatted, p);
				return;
			}
		}
		
		Util.print(p.getName() + " is not banned!");
		if(ConfigBlobcatraz.config.getBoolean("random.unlimitedPlayers"))
		{
			Server s = Bukkit.getServer();
			if(s.hasWhitelist() && !s.getWhitelistedPlayers().contains(p))
			{
				return;
			}
			else
			{
				e.setResult(Result.ALLOWED);
			}
		}
	}
}