package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class MOTD implements Listener 
{
	@EventHandler
	public void setMOTD(ServerListPingEvent e)
	{
		ConfigBlobcatraz.loadConfig();
		String motd = ConfigBlobcatraz.config.getString("motd");
		
		e.setMotd(Util.color(motd));
	}
}