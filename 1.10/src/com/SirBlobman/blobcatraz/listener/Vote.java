package com.SirBlobman.blobcatraz.listener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.item.Items;
import com.vexsoftware.votifier.model.VotifierEvent;

public class Vote implements Listener
{
	Server s = Bukkit.getServer();
	
	@EventHandler
	public void onVote(VotifierEvent e)
	{
		com.vexsoftware.votifier.model.Vote v = e.getVote();
		String voter = v.getUsername();
		String service = v.getServiceName();
		
		Player pvoter = Bukkit.getPlayer(voter);
		if(pvoter == null)
		{
			Util.print("§1" + voter + " §rtyped their name wrong or is offline");
			return;
		}
		
		Util.broadcast("§c§l" + voter + " §rhas voted using §c§l" + service + "§r!");
		
		double chance = Math.random();
		
		if(chance <= (1.0/3.0))
		{
			ItemStack diamonds = Items.voteDiamonds(service, voter);
			
			Util.giveItem(pvoter, diamonds);
			pvoter.sendMessage(Util.blobcatraz + "Thanks for voting! You got §310 diamonds");
		}
		if((2.0/3.0) < chance && chance < (2.0/3.0))
		{
			ItemStack wood = Items.voteWood(service, voter);
			
			Util.giveItem(pvoter, wood);
			pvoter.sendMessage(Util.blobcatraz + "Thanks for voting! You got some §cWood");
		}
		if(chance > (2.0/3.0))
		{
			Random rand = new Random();
			double amount = rand.nextInt((5000 - 500) + 1) + 500;
			
			ConfigDatabase.addMoney(pvoter, amount);
			pvoter.sendMessage(Util.blobcatraz + "Thanks for voting! You got §3$" + amount);
		}
	}
}