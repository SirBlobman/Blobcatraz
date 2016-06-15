package com.SirBlobman.blobcatraz.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.economy.Database;

public class JoinBroadcast implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		if(Database.databaseConfig.get("players." + uuid.toString() + ".lastName") == null)
		{
			Database.writeDefaults(uuid);
		}
		
		Boolean banned = Database.isPlayerBanned(uuid);
		
		if(banned)
		{
			String reason = Database.getBanReason(uuid);
			
			p.kickPlayer(Util.blobcatraz + "§4You have been banned:\n§r" + reason);
			return;
		}
		
		e.setJoinMessage("Yay! §1" + e.getPlayer().getName() + "§r joined the server!");
		p.sendMessage(Util.blobcatraz + "You are in §2" + e.getPlayer().getGameMode());
	}
}
