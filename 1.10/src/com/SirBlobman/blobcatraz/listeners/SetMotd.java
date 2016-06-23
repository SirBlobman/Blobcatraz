package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class SetMotd implements Listener
{
	@EventHandler
	public void setMotd(ServerListPingEvent e)
	{
		Blobcatraz.instance.reloadConfig();
		Blobcatraz.instance.reloadConfig();
		String motd = Blobcatraz.instance.config.getString("motd");
		e.setMotd(Util.color(motd));
	}
}