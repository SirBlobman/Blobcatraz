package com.SirBlobman.blobcatraz.listener;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenPrefix implements Listener
{
	@EventHandler(priority=EventPriority.HIGHEST)
	public void prefix(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		YamlConfiguration config = ConfigDatabase.load(p);
		String prefix = config.getString("prefix");
		String nick = config.getString("nick");
		setDisplay(p, prefix, nick);
	}
	
	public static void setDisplay(Player p, String prefix, String nick)
	{
		prefix = Util.color(prefix);
		nick = Util.color(nick);
		p.setDisplayName(prefix + nick);
		YamlConfiguration config = ConfigDatabase.load(p);
		config.set("prefix", prefix.replace("§", "&"));
		config.set("nick", nick.replace("§", "&"));
		ConfigDatabase.save(p, config);
	}
	
	public static void setPrefix(Player p, String prefix)
	{
		YamlConfiguration config = ConfigDatabase.load(p);
		String nick = config.getString("nick");
		String cnick = Util.color(nick);
		setDisplay(p, prefix, cnick);
	}
	
	public static void setNick(Player p, String nick)
	{
		YamlConfiguration config = ConfigDatabase.load(p);
		String prefix = config.getString("prefix");
		setDisplay(p, prefix, nick);
	}
}