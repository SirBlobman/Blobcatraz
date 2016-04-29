package com.SirBlobman.blobcatraz;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
public class Util 
{
	public static Blobcatraz plugin;
	public Util(Blobcatraz instance)
	{
		plugin = instance;
	}
	
	public static String color(String s)
	{
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static void broadcast(String m)
	{
		Bukkit.broadcastMessage(m);
	}
	
	public static void setLore(Player p, String l)
	{
		String[] sLore = l.split("/n");
		ItemStack hI = p.getItemInHand();
		List<String> lore = new ArrayList();
		for(String s : sLore)
		{
			lore.add(color(s));
		}
		ItemMeta meta = hI.getItemMeta();
		meta.setLore(lore);
		hI.setItemMeta(meta);
	}
	
	public static void addLore(Player p, String l)
	{
		String[] sLore = l.split("/n");
		ItemStack hI = p.getItemInHand();
		List<String> lore = hI.getItemMeta().getLore();
		if(lore == null)
		{
			setLore(p, l);
		}
		else
		{
			for(String s : sLore)
			{
				lore.add(color(s));
			}
			ItemMeta meta = hI.getItemMeta();
			meta.setLore(lore);
			hI.setItemMeta(meta);
		}
	}
	
	public static void clearItem(Player p)
	{
		ItemStack i = p.getItemInHand();
		int slot = p.getInventory().getHeldItemSlot();
		i.setItemMeta(null);
		p.getInventory().removeItem(new ItemStack[] {i});
		p.getInventory().setItem(slot, i);
	}
	
	public static void repairItem(Player p)
	{
		ItemStack i = p.getItemInHand();
		i.setDurability((short) 0);
	}
	
	public static void soundSonic(Player p)
	{
		Location l = p.getLocation();
		
		p.playSound(l, "sonic-screwdriver", 1, 1);
	}
	
	public static void print(String t)
	{
		System.out.println(t);
	}
	
	public static void regEvent(Listener l)
	{
		Bukkit.getServer().getPluginManager().registerEvents(l, Blobcatraz.instance);
	}
	
	public static void giveItem(Player p, ItemStack item)
	{
		if(!(item == null))
		{
			p.getInventory().addItem(item);
		}
	}
	
	public static String getFinalArg(String[] args, int start) 
	{
		StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++) 
		{
			if (i != start) 
			{
				bldr.append(" ");
			}
			bldr.append(args[i]);
		}
		return bldr.toString();
	}
}
