package com.SirBlobman.blobcatraz.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandSpy implements Listener 
{
	@EventHandler
	public void spy(PlayerCommandPreprocessEvent e)
	{
		if(e.isCancelled()) return;
		FileConfiguration config = ConfigBlobcatraz.load();
		List<String> ignored = config.getStringList("command spy.ignored commands");
		Player p = e.getPlayer();
		String c = e.getMessage().toLowerCase();
		for(Player p2 : Bukkit.getServer().getOnlinePlayers())
		{
			boolean spy = ConfigDatabase.getCanSpy(p2);
			if(spy)
			{
				for(String s : ignored)
				{
					String n1 = p.getName();
					String n2 = p2.getName();
					String ignore = s.toLowerCase();
					if(c.startsWith(ignore)) break;
					if(!n1.equalsIgnoreCase(n2))
					{
						p2.sendMessage("§7§o" + p.getName() + ":§7 " + e.getMessage());
						break;
					}
				}
			}
		}
	}
}