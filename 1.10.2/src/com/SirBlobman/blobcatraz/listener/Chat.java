package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class Chat implements Listener 
{
	private FileConfiguration config = ConfigBlobcatraz.load();
	private boolean mute = config.getBoolean("chat.disabled");
	private boolean ping = config.getBoolean("chat.ping");
	private boolean emojis = config.getBoolean("chat.emojis");
	
	@EventHandler
	public void mute(AsyncPlayerChatEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		if(mute && !p.hasPermission("blobcatraz.mute.bypass"))
		{
			p.sendMessage(Util.blobcatraz + "Chat is muted!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void ping(AsyncPlayerChatEvent e)
	{
		if(e.isCancelled()) return;
		if(ping)
		{
			Player p = e.getPlayer();
			String msg = e.getMessage().toLowerCase();
			for(Player p2 : Bukkit.getOnlinePlayers())
			{
				String n1 = p.getName().toLowerCase();
				String n2 = p2.getName().toLowerCase();
				String display = Util.uncolor(p2.getDisplayName().toLowerCase());
				
				boolean b1 = !n1.equals(n2);
				boolean b2 = msg.contains(n2);
				boolean b3 = msg.contains(display);
				boolean b4 = b2 || b3;
				boolean b5 = b1 && b4;
				if(b5) PlayerUtil.ping(p2);
			}
		}
	}
	
	@EventHandler
	public void emojis(AsyncPlayerChatEvent e)
	{
		if(e.isCancelled()) return;
		if(emojis)
		{
			String msg = e.getMessage();
			e.setMessage(Util.format(msg));
		}
	}
}