package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class Chat implements Listener 
{
	public boolean globalMute = ConfigBlobcatraz.config.getBoolean("chat.disabled");
	private boolean ping = ConfigBlobcatraz.config.getBoolean("chat.ping");
	private boolean emojis = ConfigBlobcatraz.config.getBoolean("chat.emojis");
	
	@EventHandler
	public void chatMute(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		
		if(globalMute && !p.hasPermission("blobcatraz.mutEmojis.bypass"))
		{
			p.sendMessage(Util.blobcatraz + "§cChat is currently §dDisabled");
			e.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void pingPlayer(AsyncPlayerChatEvent e)
	{
		if(ping)
		{
			Player chatter = e.getPlayer();
			String msg = e.getMessage().toLowerCase();
			for(Player other : Bukkit.getOnlinePlayers())
			{
				String name1 = chatter.getName().toLowerCase();
				String name2 = other.getName().toLowerCase();
				String display = other.getDisplayName().toLowerCase();
				if(!name1.equals(name2) && msg.contains(name2) || msg.contains(display))
				{
					Util.pingPlayer(other);
				}
			}
		}
	}
	
	@EventHandler
	public void emojis(AsyncPlayerChatEvent e)
	{
		if(emojis)
		{
			String m = e.getMessage();
			e.setMessage(Util.symbolize(m));
		}
	}
}