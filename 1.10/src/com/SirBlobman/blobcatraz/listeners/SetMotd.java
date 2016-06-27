package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.BlobcatrazConfig;

public class SetMotd implements Listener
{
	@EventHandler
	public void setMotd(ServerListPingEvent e)
	{
		BlobcatrazConfig.loadConfig();
		String motd = BlobcatrazConfig.config.getString("motd");
		e.setMotd(Util.color(motd));
	}
}