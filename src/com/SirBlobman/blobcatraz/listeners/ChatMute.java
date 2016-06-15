package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class ChatMute implements Listener 
{
	public static boolean isGlobalMute = Blobcatraz.instance.getConfig().getBoolean("chat.disabled");
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		
		if(isGlobalMute && p.hasPermission("blobcatraz.mute.bypass") == false)
		{
			p.sendMessage(Util.blobcatraz + "§cChat is currently §dDisabled");
			e.setCancelled(true);
		}
	}
}