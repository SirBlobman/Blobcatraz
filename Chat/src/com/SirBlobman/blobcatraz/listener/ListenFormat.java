package com.SirBlobman.blobcatraz.listener;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.SirBlobman.blobcatraz.config.ConfigChat;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenFormat implements Listener
{
	private static YamlConfiguration config = ConfigChat.load();
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void format(AsyncPlayerChatEvent e)
	{
		String format = config.getString("default format");
		format = Util.color(format);
		format = Util.symbolize(format);
		format = format.replace("%displayname%", "%1s");
		format = format.replace("%message%", "%2s");
		e.setFormat(format);
	}
}