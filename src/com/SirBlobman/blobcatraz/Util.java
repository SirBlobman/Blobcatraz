package com.SirBlobman.blobcatraz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

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
	
	public static HashMap<UUID, Long> banned = new HashMap<UUID, Long>();
	public static HashMap<UUID, String> reasons = new HashMap<UUID, String>();
	
	public static String blobcatraz = "§1[§6Blobcatraz§1]§r ";
	public static String notEnoughArguments = blobcatraz + "§4Not Enough Arguments!";
	public static String tooManyArguments = blobcatraz + "§4Too Many Arguments!";
	public static String invalidArguments = blobcatraz + "§4Invalid Arguments!";
	public static String noPerms = blobcatraz + "§4You don't have permission: ";
	public static String notAPlayer = blobcatraz + "This command must be used by a player";
	public static String notAnInventoryEntity = blobcatraz + "This command must be run by an entity with an inventory!";
	public static String randomTPNotEnabledInWorld = blobcatraz + "Random Teleportation is not enabled in this world!";
	
	public static String color(String s)
	{
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static void broadcast(String m)
	{
		Bukkit.broadcastMessage(blobcatraz + m);
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
		System.out.println("[Blobcatraz] " + t);
	}
	
	@Deprecated
	public static void print()
	{
		Blobcatraz.instance.getLogger().log(Level.SEVERE, "An error has occured");
	}
	
	public static void regEvent(Listener l)
	{
		Bukkit.getServer().getPluginManager().registerEvents(l, Blobcatraz.instance);
	}
	
	public static void giveItem(Player p, ItemStack item)
	{
		if(!(item == null))
		{
			if(p.getInventory().firstEmpty() != -1)
			{
				p.getInventory().addItem(item);
			}
			else
			{
				p.sendMessage("§1[§6Blobcatraz§1]§r Your inventory is too full to receive items");
			}
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
	
	public enum BanUnits
	{
		SECOND("s", 1/60),
		MINUTE("m", 1),
		HOUR("h", 60),
		DAY("d", 60*24),
		WEEK("w", 60*24*7),
		MONTH("mo", 30*60*24),
		YEAR("y", 30*60*24*12);
		
		public String name;
		public int multi;
		
		BanUnits(String n, int m)
		{
			name = n;
			multi = m;
		}
		
		public static long getTicks(String un, int time)
		{
			long sec;
			try
			{
				sec = time * 60;
			}
			catch (Exception ex)
			{
				return 0;
			}
			
			for(BanUnits u: BanUnits.values())
			{
				if(un.startsWith(u.name))
				{
					return (sec *= u.multi) * 1000;
				}
			}
			
			return 0;
		}
	}
}
