package com.SirBlobman.blobcatraz.listener;

import java.util.Random;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VoteLogger implements Listener
{
	Server S = Bukkit.getServer();
	
	@EventHandler
	public void vote(VotifierEvent e)
	{
		Vote v = e.getVote();
		String time = v.getTimeStamp();
		String address = v.getAddress();
		String voter = v.getUsername();
		String service = v.getServiceName();
		
		@SuppressWarnings("deprecation")
		OfflinePlayer op = Bukkit.getOfflinePlayer(voter);
		if(op == null) 
		{
			Util.print(voter + " typed their name wrong!");
			return;
		}
		if(!op.isOnline()) Util.print(voter + " is Offline!");
		
		Player p = op.getPlayer();
		String msg = Util.message("vote");
		msg = msg.replace("%time%", time);
		msg = msg.replace("%ip%", address);
		msg = msg.replace("%voter%", voter);
		msg = msg.replace("%site%", service);
		Util.broadcast(msg);
		vote(p);
	}
	
	private void vote(Player p)
	{
		if(p == null) return;
		double chance = Math.random();
		Random r = new Random();
		
		double half = 1.0D / 2.0D;
		double third = 1.0D / 3.0D;
		double eighth = 1.0D / 8.0D;
		
		if(chance < half)
		{
			//Wood
		}
		if(chance < third)
		{
			//Diamonds
		}
		if(chance < eighth)
		{
			int money = r.nextInt(5000);
			ConfigDatabase.addMoney(p, money);
			ConfigDatabase.addVote(p);	
		}
	}
}