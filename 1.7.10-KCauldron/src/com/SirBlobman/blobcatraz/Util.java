package com.SirBlobman.blobcatraz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class Util 
{
//Plugin
	public static Blobcatraz plugin = Blobcatraz.instance;
	
//Messages
	public static String blobcatraz = "§1[§6Blobcatraz§1]§r ";
	public static String blobcatrazUnformatted = "[Blobcatraz] ";
	public static String notEnoughArguments = blobcatraz + "§4Not Enough Arguments!";
	public static String tooManyArguments = blobcatraz + "§4Too Many Arguments!";
	public static String invalidArguments = blobcatraz + "§4Invalid Arguments";
	public static String noPermission = blobcatraz + "§You don't have permission: §r";
	public static String commandExecutorNotAPlayer = blobcatraz + "This command must be used by a Player";
	public static String commandExecutorNotAlive = blobcatraz + "This command must be used by something that is alive!";
	public static String notEnabledInWorld = blobcatraz + "You can't do that in this world!";
	
//Color Coding
	public static String color(String msg)
	{
		if(msg == null) return "";
		
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

//Broadcasting Message
	public static void broadcast(String broadcast)
	{
		Bukkit.broadcastMessage(blobcatraz + broadcast);
	}
	
//Item Editing
	//Rename the item
	public static void rename(Player p, String name)
	{
		if(p == null || name == null) return;
		
		ItemStack held = p.getItemInHand();
		ItemMeta meta = held.getItemMeta();
		
		meta.setDisplayName(Util.color(name));
		
		held.setItemMeta(meta);
	}
	//Setting a Lore
	public static void setLore(Player p, String lore)
	{
		if(p == null) return;
		
		String[] splittedLore = lore.split("/n");
		ItemStack held = p.getItemInHand();
		if(held == null || held.getType() == Material.AIR) 
		{
			p.sendMessage(blobcatraz + "Air cannot have lore");
			return;
		}
		
		List<String> newLore = new ArrayList<String>();
		for(String line : splittedLore) newLore.add(color(line));
		
		ItemMeta meta = held.getItemMeta();
		meta.setLore(newLore);
		
		held.setItemMeta(meta);
	}
	
	//Adding a Lore
	public static void addLore(Player p, String lore)
	{
		if(p == null) return;
		
		String[] splittedLore = lore.split("/n");
		ItemStack held = p.getItemInHand();
		if(held == null || held.getType() == Material.AIR)
		{
			p.sendMessage(blobcatraz + "You can't add lore to Air");
			return;
		}
		List<String> newLore = held.getItemMeta().getLore();
		
		if(newLore == null) 
		{
			setLore(p, lore);
		}
		else
		{
			for(String line : splittedLore)
			{
				newLore.add(color(line));
			}
			
			ItemMeta meta = held.getItemMeta();
			meta.setLore(newLore);
			held.setItemMeta(meta);
		}
	}
	
	//Reset Item Meta
	public static void resetItem(Player p)
	{
		if(p == null) return;
		
		ItemStack is = p.getItemInHand();
		if(is == null || is.getType() == Material.AIR)
		{
			p.sendMessage(blobcatraz + "You can't reset Air");
			return;
		}
		PlayerInventory pi = p.getInventory();
		int slot = p.getInventory().getHeldItemSlot();
		is.setItemMeta(null);
		pi.removeItem(new ItemStack[] {is});
		pi.setItem(slot, is);
	}
	
	//Remove Item Damage
	public static void repairItem(Player p)
	{
		if(p == null) return;
		
		ItemStack is = p.getItemInHand();
		if(is == null || is.getType() == Material.AIR)
		{
			p.sendMessage(blobcatraz + "You can't repair Air");
			return;
		}
		
		try
		{
			is.setDurability((short)0);
			p.sendMessage(blobcatraz + "Successfully repaired your " + is.getType().toString());
		}
		catch (Exception ex)
		{
			p.sendMessage(blobcatraz + "Failed to repair your " + is.getType().toString());
			return;
		}
	}
	
	//Give Items
	public static void giveItem(Player p, ItemStack item)
	{
		if(p == null || item == null) return;
		PlayerInventory pi = p.getInventory();
		
		if(pi.firstEmpty() != -1)
		{
			p.getInventory().addItem(item);
		}
		else
		{
			p.sendMessage(blobcatraz + "Your inventory is full!");
		}
	}
	
	//Enchant Items
	public static void enchantItem(Player p, String enchant, int level)
	{
		if(p == null || enchant == null) return;
		ItemStack held = p.getItemInHand();
		if(held == null || held.getType() == Material.AIR) 
		{
			p.sendMessage(blobcatraz + "You can't enchant air"); 
			return;
		}
		
		String lvl = "";
		if(level == 1) lvl = "I";
		if(level == 2) lvl = "II";
		if(level == 3) lvl = "III";
		if(level == 4) lvl = "IV";
		if(level == 5) lvl = "V";
		
		ItemMeta meta = held.getItemMeta();
		
		meta.setLore(Arrays.asList(enchant + " " + lvl));
		
		held.setItemMeta(meta);
	}
	
//Make a sonic screwdriver sound
	@SuppressWarnings("deprecation")
	public static void playSonicSound(Player p)
	{
		if(p == null) return;
		Location l = p.getLocation();
		p.playSound(l, "sonic-screwdriver", 1, 1);
	}
	
//Print a message to console
	public static void print(String msg)
	{
		System.out.println(blobcatrazUnformatted + msg);
	}
	
//Register an EventHandler
	public static void regEvent(Listener l)
	{
		Bukkit.getServer().getPluginManager().registerEvents(l, plugin);
	}

//Get String from multiple arguments
	public static String getFinalArg(String[] args, int start)
	{
		StringBuilder builder = new StringBuilder();
		for(int i = start; i < args.length; i++)
		{
			if(i != start) builder.append(" ");
			builder.append(args[i]);
		}
		
		return builder.toString();
	}
	
//Random Teleportation
	//Tiny
	public static void tinyRandomTP(Player p)
	{
		ConfigBlobcatraz.loadConfig();
		
		if(p == null) return;
		
		p.setFallDistance(-100.0F);
		Random r = new Random();
		
		int x = r.nextInt(ConfigBlobcatraz.config.getInt("randomtp.maxTinyDistance"));
		int y = 110;
		int z = r.nextInt(ConfigBlobcatraz.config.getInt("randomtp.maxTinyDistance"));
		
		Location tpLocation = new Location(p.getWorld(), (double)x, (double)y, (double)z);
		tpLocation.getWorld().refreshChunk(tpLocation.getChunk().getX(), tpLocation.getChunk().getZ());
		
		p.teleport(tpLocation);
		p.setFallDistance(0.0F);
		p.sendMessage(Util.blobcatraz + "You were teleported to §5" + x + "§r,§5" + y + "§r,§5" + z + "§r!");
	}
	//Normal
	public static void normalRandomTP(Player p)
	{
		ConfigBlobcatraz.loadConfig();
		
		if(p == null) return;
		
		p.setFallDistance(-100.0F);
		Random r = new Random();
		
		int x = r.nextInt(ConfigBlobcatraz.config.getInt("randomtp.maxNormalDistance"));
		int y = 110;
		int z = r.nextInt(ConfigBlobcatraz.config.getInt("randomtp.maxNormalDistance"));
		
		Location tpLocation = new Location(p.getWorld(), (double)x, (double)y, (double)z);
		tpLocation.getWorld().refreshChunk(tpLocation.getChunk().getX(), tpLocation.getChunk().getZ());
		
		p.teleport(tpLocation);
		p.setFallDistance(0.0F);
		p.sendMessage(Util.blobcatraz + "You were teleported to §5" + x + "§r,§5" + y + "§r,§5" + z + "§r!");
	}
	//Far
	public static void farRandomTP(Player p)
	{
		ConfigBlobcatraz.loadConfig();
		
		if(p == null) return;
		
		p.setFallDistance(-100.0F);
		Random r = new Random();
		
		int x = r.nextInt(ConfigBlobcatraz.config.getInt("randomtp.maxFarDistance"));
		int y = 110;
		int z = r.nextInt(ConfigBlobcatraz.config.getInt("randomtp.maxFarDistance"));
		
		Location tpLocation = new Location(p.getWorld(), (double)x, (double)y, (double)z);
		tpLocation.getWorld().refreshChunk(tpLocation.getChunk().getX(), tpLocation.getChunk().getZ());
		
		p.teleport(tpLocation);
		p.setFallDistance(0.0F);
		p.sendMessage(Util.blobcatraz + "You were teleported to §5" + x + "§r,§5" + y + "§r,§5" + z + "§r!");
	}

//Heal a Player
	public static void heal(Player p)
	{
		if(p == null) return;
		
		p.setHealth(20.0);
		for(PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
		p.setFireTicks(0);
		p.setFoodLevel(20);
		p.setSaturation(20.0F);
		p.sendMessage(Util.blobcatraz + "You have been healed!");
	}
	
//Set the motd
	public static void setMOTD(String motd)
	{
		ConfigBlobcatraz.config.set("motd", motd);
		ConfigBlobcatraz.saveConfig();
		ConfigBlobcatraz.loadConfig();
	}
}
