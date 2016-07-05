package com.SirBlobman.blobcatraz.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class Join implements Listener 
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		String name = p.getDisplayName();
		
		if(ConfigDatabase.databaseConfig.getString("players." + uuid.toString() + ".lastName") == null) ConfigDatabase.writeDefaults(uuid);
		
		e.setJoinMessage("§1" + name + " §rjoined the server!");
		p.sendMessage(Util.blobcatraz + "Welcome! You are in §2" + p.getGameMode());
	}
}