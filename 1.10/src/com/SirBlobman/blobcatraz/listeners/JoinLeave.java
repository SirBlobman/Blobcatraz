package com.SirBlobman.blobcatraz.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class JoinLeave implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		
		if(ConfigDatabase.databaseConfig.getString("players." + uuid + ".name") == null) ConfigDatabase.writeDefaults(uuid);
		
		e.setJoinMessage("§1" + p.getName() + " §rjoined the server");
		p.sendMessage(Util.blobcatraz + "You are in §1" + p.getGameMode());
		
		Player SirBlobman = Bukkit.getPlayer("SirBlobman");
		if(SirBlobman == null) return;
		if(!SirBlobman.isOnline()) return;
		SirBlobman.playSound(SirBlobman.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		
		e.setQuitMessage("§1" + p.getDisplayName() + " §rleft!");
	}
}