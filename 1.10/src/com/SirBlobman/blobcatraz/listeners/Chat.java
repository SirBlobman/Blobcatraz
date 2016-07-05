package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.SirBlobman.blobcatraz.Blobcatraz;
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
		
		if(globalMute && !p.hasPermission("blobcatraz.mute.bypass"))
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
			Player p = e.getPlayer();
			String msg = e.getMessage();
			
			for(final Player P : Bukkit.getOnlinePlayers())
			{
				if(P != p && msg.toLowerCase().contains(P.getName().toLowerCase()) || msg.toLowerCase().contains(p.getDisplayName().toLowerCase()))
				{
					P.playSound(P.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100.0F, 1.0F);
					Bukkit.getServer().getScheduler().runTaskLater(Blobcatraz.instance, new Runnable()
					{
						@Override
						public void run() {P.playSound(P.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20.0F, 1.0F);}
					}, 5L);
				}
			}
		}
	}
	
	@EventHandler
	public void emojis(AsyncPlayerChatEvent e)
	{
		if(emojis)
		{
			Emojis E = new Emojis();
			String m = e.getMessage();

			m = m.replace(":)", E.getString(E.smiley));
			m = m.replace("<3", E.getString(E.heart));
			m = m.replace(":(", E.getString(E.sad));
			m = m.replace("[telephone]", E.getString(E.telephone));
			m = m.replace("[c]", E.getString(E.copyright));
			m = m.replace("[r]", E.getString(E.registered));
			m = m.replace("[tm]", E.getString(E.trademark));
			m = m.replace("[n~]", E.spanish_n);
			m = m.replace("[~n]", E.spanish_n);
			m = m.replace("[~N]", E.spanish_N);
			m = m.replace("[N~]", E.spanish_N);
			m = m.replace("[degree]", E.getString(E.degree));
			m = m.replace("[male]", E.getString(E.male));
			m = m.replace("[female]", E.getString(E.female));
			m = m.replace("[transgender]", E.getString(E.transgender));
			m = m.replace("[skullxbones]", E.getString(E.skullXbones));
			m = m.replace("[cent]", E.getString(E.cent));
			m = m.replace("[?!]", E.getString(E.questionExclamation));

			e.setMessage(m);
		}
	}
}