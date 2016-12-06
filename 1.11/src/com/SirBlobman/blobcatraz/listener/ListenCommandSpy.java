package com.SirBlobman.blobcatraz.listener;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenCommandSpy implements Listener
{
	private static YamlConfiguration config = ConfigBlobcatraz.load();
	private static List<String> ignored = config.getStringList("command spy.ignored commands");
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void spy(PlayerCommandPreprocessEvent e)
	{
		if(e.isCancelled()) return;
		
		Player p = e.getPlayer();
		String msg = e.getMessage();
		String cmd = msg.toLowerCase();
		Collection<? extends Player> online = Bukkit.getOnlinePlayers();
		for(Player on : online)
		{
			boolean spy = ConfigDatabase.canSpy(on);
			if(spy)
			{
				String n1 = p.getName();
				String n2 = on.getName();
				
				for(String i : ignored)
				{
					boolean b1 = cmd.startsWith(i);
					boolean b2 = cmd.startsWith("/" + i);
					boolean b3 = (b1 || b2);
					if(b3) return;
				}

				boolean b4 = !(n1.equalsIgnoreCase(n2));
				if(b4)
				{
					on.sendMessage(Util.format("&7&o%s:&7 %s", n1, msg));
					break;
				}
			}
		}
	}
}