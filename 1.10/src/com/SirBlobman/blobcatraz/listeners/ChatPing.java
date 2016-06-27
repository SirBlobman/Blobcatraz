package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.SirBlobman.blobcatraz.Blobcatraz;

public class ChatPing implements Listener
{
	@EventHandler(ignoreCancelled=true)
	public void pingPlayer(AsyncPlayerChatEvent e)
	{			
		Player p = e.getPlayer();
		String m = e.getMessage();
		for(final Player oP : Bukkit.getServer().getOnlinePlayers())
		{
			if ((oP != p) && ((m.toLowerCase().contains(oP.getName().toLowerCase())) || (m.toLowerCase().contains(oP.getDisplayName().toLowerCase()))))
			{
				oP.playSound(oP.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20.0F, 1.0F);
				Blobcatraz.instance.getServer().getScheduler().runTaskLater(Blobcatraz.instance, new Runnable()
				{
					@Override
					public void run()
					{
						oP.playSound(oP.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20.0F, 1.0F);
					}
				}, 5L);
			}
		}
	}
}
