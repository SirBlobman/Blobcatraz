package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

@SuppressWarnings("unused")
public class Votes implements Listener
{
	@EventHandler
	public void onVoteEvent(VotifierEvent e)
	{
		Vote v = e.getVote();
		String voter = v.getUsername();
		String service = v.getServiceName();
		
		String time = v.getTimeStamp();
		String address = v.getAddress();
		
		Server s = Bukkit.getServer();
		
		Bukkit.broadcastMessage("§1[§6Blobcatraz§1]§r §c§l" + voter + " §rhas voted using §c§l" + service + "§r:§c§l" + address + " §r!");
		s.dispatchCommand(s.getConsoleSender(), "minecraft:give " + voter + " minecraft:diamond 10 0 {HideFlags:1,display:{Name:§2Vote §3Diamond,Lore:[§1Thanks!]},ench:[{id:62,lvl:1}]}");
	}
}
