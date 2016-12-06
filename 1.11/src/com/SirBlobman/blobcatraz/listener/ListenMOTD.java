package com.SirBlobman.blobcatraz.listener;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenMOTD implements Listener
{
	@EventHandler
	public void motd(ServerListPingEvent e)
	{
		String msg = Util.option("motd");
		msg = msg.replace("/n", "\n");
		e.setMotd(msg);
	}
	
	@EventHandler
	public void inf(ServerListPingEvent e)
	{
		YamlConfiguration config = ConfigBlobcatraz.load();
		boolean inf = config.getBoolean("unlimited players");
		if(inf)
		{
			int a = e.getNumPlayers();
			int m = a + 1;
			e.setMaxPlayers(m);
		}
	}
}