package com.SirBlobman.blobcatraz.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigLanguage;

public class MOTD implements Listener 
{
	@EventHandler
	public void setMOTD(ServerListPingEvent e)
	{
		String motd = ConfigLanguage.getMessage("motd");
		e.setMotd(motd);
		int amount = e.getNumPlayers();
		int max = amount + 1;
		if(ConfigBlobcatraz.config.getBoolean("random.unlimitedPlayers")) e.setMaxPlayers(max);
	}
}