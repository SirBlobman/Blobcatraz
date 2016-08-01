package com.SirBlobman.blobcatraz.listener;

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
		String[] display = motd.split("/n");
		if(display.length >= 2) e.setMotd(Util.color(display[0]) + "\n" + Util.color(display[1]));
		else e.setMotd(Util.color(motd));
		if(ConfigBlobcatraz.config.getBoolean("random.unlimitedPlayers")) e.setMaxPlayers(e.getNumPlayers() + 1);
	}
}