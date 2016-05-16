package com.SirBlobman.blobcatraz.listeners;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

@SuppressWarnings("unused")
public class Votes implements Listener
{
	Server s = Bukkit.getServer();
	
	@EventHandler
	public void onVoteEvent(VotifierEvent e)
	{
		Vote v = e.getVote();
		String voter = v.getUsername();
		
		String service = v.getServiceName();
		
		String time = v.getTimeStamp();
		String address = v.getAddress();
		
		Player pvoter = Bukkit.getServer().getPlayer(voter);
		if(pvoter == null)
		{
			System.out.println("§1[§6Blobcatraz§1]§r §1" + voter + " §rtyped their name wrong or is offline");
			return;
		}
		
		Bukkit.broadcastMessage("§1[§6Blobcatraz§1]§r §c§l" + voter + " §rhas voted using §c§l" + service + "§r:§c§l" + address + " §r!");
		
		double chance = Math.random();
		
		if(chance >= .5)
		{
			ItemStack diamonds = new ItemStack(Material.DIAMOND);
			ItemMeta md = diamonds.getItemMeta();
			md.setDisplayName("§1§ki§4Vote§1§ki§r §3Diamonds");
			md.setLore(Arrays.asList("§1Thanks!", "§fSite: §6" + service, "§fVoter: §1" + voter));
			diamonds.setItemMeta(md);
			diamonds.setAmount(10);
			
			pvoter.getInventory().addItem(diamonds);
			pvoter.sendMessage("§1[§6Blobcatraz§1]§r Thanks for voting! You got 10 diamonds");
		}
		else
		{
			Random rand = new Random();
			int r = rand.nextInt(5);
			
			ItemStack wood = new ItemStack(Material.WOOD, r);
			ItemMeta mw = wood.getItemMeta();
			mw.setDisplayName("§1§ki§4Vote§1§ki§r §cWood");
			mw.setLore(Arrays.asList("§1Thanks!", "§fSite: §6" + service, "§fVoter: §1" + voter));
			wood.setAmount(64);
			wood.setItemMeta(mw);
			
			pvoter.getInventory().addItem(wood);
			pvoter.sendMessage("§1[§6Blobcatraz§1]§r Thanks for voting! You got some wood");
		}
	}
}
