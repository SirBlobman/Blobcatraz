package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.item.Items;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class Votes implements Listener 
{
	Server s = Bukkit.getServer();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onVote(VotifierEvent e)
	{
		Vote v = e.getVote();
		String voter = v.getUsername();
		String service = v.getServiceName();
		
		Player pvoter = Bukkit.getServer().getPlayer(voter);
		if(pvoter == null)
		{
			System.out.println(Util.blobcatraz + "§1" + voter + " §rtyped their name wrong, or is Offline");
			return;
		}
		
		Bukkit.broadcastMessage(Util.blobcatraz + "§c§1" + voter + " §rhas voted using §c§l" + service + "§r!");
		
		double chance = Math.random();
		
		if(chance <= (1/3))
		{
			ItemStack voteDiamonds = Items.voteDiamonds(voter, service);
			Util.giveItem(pvoter, voteDiamonds);
			pvoter.sendMessage(Util.blobcatraz + "Thanks for voting! You got §310 Diamonds");
		}
		if((1/3) < chance && chance < (2/3))
		{
			ItemStack voteWood = Items.voteWood(voter, service);
			Util.giveItem(pvoter, voteWood);
			pvoter.sendMessage(Util.blobcatraz + "Thanks for voting! You got §c64 Wood");
		}
		else
		{
			ConfigDatabase.addToPlayerBalance(pvoter.getUniqueId(), 500);
			pvoter.sendMessage(Util.blobcatraz + "Thanks for voting! You got §2$500");
		}
	}
}