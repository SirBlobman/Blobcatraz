package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinBroadcast implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		e.setJoinMessage("Yay! §1" + e.getPlayer().getName() + "§r joined the server!");
		e.getPlayer().sendMessage("You are in §2" + e.getPlayer().getGameMode());
	}
}
