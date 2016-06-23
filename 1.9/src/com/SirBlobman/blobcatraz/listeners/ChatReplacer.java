package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;

public class ChatReplacer implements Listener
{
	@EventHandler
	public void Emojis(AsyncPlayerChatEvent e)
	{
		String m = e.getMessage();
		
		m = m.replace(":)", Character.toString(Emojis.smiley));
		m = m.replace("<3", Character.toString(Emojis.heart));
		m = m.replace(":(", Character.toString(Emojis.sad));
		m = m.replace("[telephone]", Character.toString(Emojis.telephone));
		m = m.replace("[c]", Character.toString(Emojis.copyright));
		m = m.replace("[r]", Character.toString(Emojis.registered));
		m = m.replace("[tm]", Character.toString(Emojis.trademark));
		m = m.replace("[n~]", Emojis.spanish_n);
		m = m.replace("[~n]", Emojis.spanish_n);
		m = m.replace("[~N]", Emojis.spanish_N);
		m = m.replace("[N~]", Emojis.spanish_N);
		m = m.replace("[degree]", Character.toString(Emojis.degree));
		m = m.replace("[male]", Character.toString(Emojis.male));
		m = m.replace("[female]", Character.toString(Emojis.female));
		m = m.replace("[transgender]", Character.toString(Emojis.transgender));
		m = m.replace("[skullxbones]", Character.toString(Emojis.skullXbones));
		m = m.replace("[cent]", Character.toString(Emojis.cent));
		m = m.replace("[?!]", Character.toString(Emojis.questionExclamation));
		
		e.setMessage(m);
	}
	
	@EventHandler
	public void Colors(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		String m = e.getMessage();
		if(p.hasPermission("blobcatraz.chat.color"))
		{
			m = ChatColor.translateAlternateColorCodes('&', m);
		}
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
	}
}
