package com.SirBlobman.blobcatraz.listener;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenLoginLogout implements Listener
{
	@EventHandler(priority=EventPriority.LOWEST)
	public void login(PlayerLoginEvent e)
	{
		Player p = e.getPlayer();
		YamlConfiguration config = ConfigDatabase.load(p);
		long time = System.currentTimeMillis();
		long end = ConfigDatabase.endOfBan(p);
		String reason = Util.format(ConfigDatabase.banReason(p));
		
		if(ConfigDatabase.banned(p))
		{
			Object length = config.get("banned.length");
			if(length == null)
			{
				e.disallow(Result.KICK_BANNED, reason);
				return;
			}
			if(time < end)
			{
				String format = ConfigDatabase.endOfBanFormatted(p);
				e.disallow(Result.KICK_BANNED, reason + "\n" + format);
				return;
			}
			if(end < time)
			{
				ConfigDatabase.unban("Automatically", p);
				return;
			}
		}
	}
	
	@EventHandler
	public void inf(PlayerLoginEvent e)
	{
		YamlConfiguration config = ConfigBlobcatraz.load();
		if(config.getBoolean("unlimited players"))
		{
			Player p = e.getPlayer();
			Server S = Bukkit.getServer();
			boolean b1 = S.hasWhitelist();
			if(b1)
			{
				Set<OfflinePlayer> whitelist = S.getWhitelistedPlayers();
				if(!whitelist.contains(p)) return;
			}
			
			e.setResult(Result.ALLOWED);
		}
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		String join = PlayerUtil.join(p);
		e.setJoinMessage(join);
		
		OfflinePlayer owner = PlayerUtil.owner();
		if(owner == null) return;
		if(!owner.isOnline()) return;
		PlayerUtil.ping((Player) owner);
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		String quit = PlayerUtil.quit(p);
		e.setQuitMessage(quit);
	}
}