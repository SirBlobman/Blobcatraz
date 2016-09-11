package com.SirBlobman.blobcatraz.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class MOTD implements Listener
{
	@EventHandler
	public void motd(ServerListPingEvent e)
	{
		String motd = Util.message("blobcatraz.motd");
		motd = motd.replace("/n", "\n");
		e.setMotd(motd);
		FileConfiguration config = ConfigBlobcatraz.load();
		if(config.getBoolean("unlimited players"))
		{
			int amount = e.getNumPlayers();
			int max = amount + 1;
			e.setMaxPlayers(max);
		}
	}
}