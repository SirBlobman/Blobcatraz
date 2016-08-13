package com.SirBlobman.blobcatraz.listener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.command.CommandMessage;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.scoreboard.CombatTimer;

public class JoinLeave implements Listener
{
	CombatTimer CT = new CombatTimer(Blobcatraz.instance);
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		
		ConfigDatabase.load(p);
		
		e.setJoinMessage(Util.getJoinMessage(p));
		p.sendMessage(Util.blobcatraz + "You are in §1" + p.getGameMode());
		
		UUID owner = Util.getOwner();
		if(owner == null) return;
		OfflinePlayer pOwner = Bukkit.getOfflinePlayer(owner);
		if(!pOwner.isOnline()) return;
		Util.pingPlayer((Player) pOwner); 
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		if(CombatLog.tagged.containsKey(p))
		{
			e.setQuitMessage(Util.blobcatraz + "§5" + p.getDisplayName() + " left during combat!");
			p.setHealth(0.0);
			CombatLog.tagged.remove(p);
			CT.clearTimer(p);
			CommandMessage.reply.remove(p);
		}
		else
		{
			e.setQuitMessage(Util.getQuitMessage(p));
		}
	}
}