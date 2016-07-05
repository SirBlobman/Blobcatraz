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
	public static boolean globalMute = ConfigBlobcatraz.config.getBoolean("chat.disabled");
	private static boolean ping = ConfigBlobcatraz.config.getBoolean("chat.ping");
	private static boolean emojis = ConfigBlobcatraz.config.getBoolean("chat.emojis");
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		if(p == null) return;
		
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

			for(final Player P : Bukkit.getServer().getOnlinePlayers())
			{
				if(P != p && msg.toLowerCase().contains(P.getName().toLowerCase()) || msg.toLowerCase().contains(P.getDisplayName().toLowerCase()))
				{
					P.playSound(P.getLocation(), Sound.ORB_PICKUP, 20.0F, 1.0F);
					Bukkit.getServer().getScheduler().runTaskLater(Blobcatraz.instance, new Runnable()
					{
						@Override
						public void run() {P.playSound(P.getLocation(), Sound.ORB_PICKUP, 20.0F, 1.0F);}
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
	
	@EventHandler
	public void colors(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		String m = e.getMessage();
		
		if(p.hasPermission("blobcatraz.chat.color")) m = Util.color(m);
		
		e.setMessage(m);
	}
	
	public static class Emojis
	{
		public static char smiley = '\u263a';
		public static char heart = '\u2665';
		public static char sad = '\u2639';
		public static char telephone = '\u260e';
		public static char copyright = '\u00a9';
		public static char registered = '\u00ae';
		public static char trademark = '\u2122';
		public static String spanish_n = "ñ";
		public static String spanish_N = "Ñ";
		public static char degree = '\u00b0';
		public static char male = '\u2642';
		public static char female = '\u2640';
		public static char transgender = '\u26a3';
		public static char skullXbones = '\u2620';
		public static char cent = '\u00a2';
		public static char questionExclamation = '\u2048';
		
		public static String getString(Character c)
		{
			return Character.toString(c);
		}
	}
}