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
		
		m = m.replace("[:)]", "☺");
		m = m.replace("[:-)]", "☻");
		m = m.replace("[<3]", "♥");
		m = m.replace("[:(]", "☹");
		m = m.replace("[telephone]", "📞");
		m = m.replace("[cellphone]", "📱");
		m = m.replace("[c]", "©");
		m = m.replace("[r]", "®");
		m = m.replace("[n~]", "ñ");
		m = m.replace("[~n]", "ñ");
		m = m.replace("[~N]", "Ñ");
		m = m.replace("[n~]", "Ñ");
		m = m.replace("[degree]", "º");
		m = m.replace("[r]", "®");
		m = m.replace("[tm]", "™");
		m = m.replace("[male]", "♂");
		m = m.replace("[female]", "♀");
		m = m.replace("[male+female]", "⚥");
		m = m.replace("[skull]", "💀");
		m = m.replace("[skullxbones]", "☠");
		m = m.replace("[cent]", "¢");
		m = m.replace("[?!]", "‽");
		
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
}
