package com.SirBlobman.blobcatraz.listener;

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
			String m = e.getMessage();

			m = m.replace(":)", Emojis.getString(Emojis.smiley));
			m = m.replace("<3", Emojis.getString(Emojis.heart));
			m = m.replace(":(", Emojis.getString(Emojis.sad));
			m = m.replace("[telephone]", Emojis.getString(Emojis.telephone));
			m = m.replace("[c]", Emojis.getString(Emojis.copyright));
			m = m.replace("[r]", Emojis.getString(Emojis.registered));
			m = m.replace("[tm]", Emojis.getString(Emojis.trademark));
			m = m.replace("[n~]", Emojis.spanish_n);
			m = m.replace("[~n]", Emojis.spanish_n);
			m = m.replace("[~N]", Emojis.spanish_N);
			m = m.replace("[N~]", Emojis.spanish_N);
			m = m.replace("[degree]", Emojis.getString(Emojis.degree));
			m = m.replace("[male]", Emojis.getString(Emojis.male));
			m = m.replace("[female]", Emojis.getString(Emojis.female));
			m = m.replace("[transgender]", Emojis.getString(Emojis.transgender));
			m = m.replace("[skullxbones]", Emojis.getString(Emojis.skullXbones));
			m = m.replace("[cent]", Emojis.getString(Emojis.cent));
			m = m.replace("[?!]", Emojis.getString(Emojis.questionExclamation));

			e.setMessage(m);
		}
	}
}