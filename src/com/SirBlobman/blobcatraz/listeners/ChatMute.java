package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.SirBlobman.blobcatraz.Blobcatraz;

public class ChatMute implements Listener 
{
	public static boolean isGlobalMute = Blobcatraz.plugin.getConfig().getBoolean("chat.disabled");
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		
		if(isGlobalMute == Boolean.valueOf(true))
		{
			p.sendMessage("§1[§6Blobcatraz§1]§r §cChat is currently §dDisabled");
			e.setCancelled(true);
		}
	}
}