package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.SirBlobman.blobcatraz.Blobcatraz;

import net.md_5.bungee.api.ChatColor;

public class SetMotd implements Listener
{
	@EventHandler
	public void setMotd(ServerListPingEvent e)
	{
		Blobcatraz.plugin.reloadConfig();
		Blobcatraz.plugin.reloadConfig();
		String motd = Blobcatraz.plugin.config.getString("motd");
		e.setMotd(ChatColor.translateAlternateColorCodes('&', motd));
	}
}
