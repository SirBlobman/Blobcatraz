package com.SirBlobman.blobcatraz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
	static Blobcatraz plugin = Blobcatraz.instance;
	
//Message
	public static final String blobcatraz = "§1[§6Blobcatraz§1]§r ";
	public static final String blobcatrazUnformatted = "[Blobcatraz] ";
	public static final String pluginEnabled = "This plugin has been §2§lEnabled§r!";
	public static final String pluginDisabled = "This plugin has been §4§lDisabled§r!";
	public static final String notEnoughArguments = blobcatraz + "§4Not Enough Arguments!";
	public static final String tooManyArguments = blobcatraz + "§4Too Many Arguments!";
	public static final String invalidArguments = blobcatraz + "§4Invalid Arguments!";
	public static final String noPermission = blobcatraz + "§4You don't have permission: ";
	public static final String commandExecutorNonPlayer = blobcatraz + "This command must be used by a Player";
	public static final String commandExecutorNonLiving = blobcatraz + "This command must be used by a Living Entity";
	public static final String notEnabledInWorld = blobcatraz + "You can't do that in this world!";
	public static final String banned = blobcatraz + "§4You have been banned :\n§r";
	
//NMS
//Color a message
	public static String color(String msg)
	{
		if(msg == null) return "";
		
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
//Broadcast a message
	public static void broadcast(String msg)
	{
		if(msg == null) return;
		
		Bukkit.broadcastMessage(blobcatraz + msg);
	}
	
//Item Editing
	//Rename
	public static void rename(Player p, String name)
	{
		if(p == null || name == null) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(is == null || is.getType() == Material.AIR)
		{
			p.sendMessage(blobcatraz + "You can't rename Air");
			return;
		}
		
		ItemMeta meta = is.getItemMeta();
		
		meta.setDisplayName(color(name));
		is.setItemMeta(meta);
		
		p.sendMessage(blobcatraz + "Renamed your §6" + is.getType().toString() + " §rto " + color(name));
		p.updateInventory();
	}
	
	//Set lore
	public static void setLore(Player p, String lore)
	{
		if(p == null || lore == null) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(is == null || is.getType() == Material.AIR)
		{
			p.sendMessage(blobcatraz + "You can't set the lore of Air");
			return;
		}
		ItemMeta meta = is.getItemMeta();
		String[] splittedLore = lore.split("/n");
		List<String> newLore = new ArrayList<String>();
		for(String split : splittedLore) newLore.add(color(split));
		
		meta.setLore(newLore);
		is.setItemMeta(meta);
		
		p.sendMessage(blobcatraz + "Set the lore of your §6" + is.getType().toString() + " §rto: \n" + newLore.toString());
		p.updateInventory();
	}
	
	//Add lore
	public static void addLore(Player p, String lore)
	{
		if (p == null || lore == null) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(is == null || is.getType() == Material.AIR)
		{
			p.sendMessage(blobcatraz + "You can't set the lore of Air");
			return;
		}
		
		ItemMeta meta = is.getItemMeta();
		
		String[] splittedLore = lore.split("/n");
		List<String> newLore = meta.getLore();
		if(newLore == null) {setLore(p, lore); return;}
		else
		{
			for(String split : splittedLore) newLore.add(color(split));
			
			meta.setLore(newLore);
			is.setItemMeta(meta);
		}
		
		p.sendMessage(blobcatraz + "Set the lore of your §6" + is.getType().toString() + " §rto " + newLore);
		p.updateInventory();
	}
	
	//Remove Lore
	public static void removeLore(Player p)
	{
		if(p == null) return;
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		if(held == null) held = pi.getItemInOffHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		
		meta.setLore(null);
		held.setItemMeta(meta);
		
		p.sendMessage(blobcatraz + "Removed the lore of your §6" + held.getType().toString());
		p.updateInventory();
	}
	
	//Remove Lore Line
	public static void removeLore(Player p, int line)
	{
		if(p == null) return;
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		if(held == null) held = pi.getItemInOffHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		String oMsg = lore.get(line);
		lore.remove(line);
		meta.setLore(lore);
		held.setItemMeta(meta);
		
		p.sendMessage(blobcatraz + "Removed " + oMsg + " §rfrom the lore of your §6" + held.getType().toString());
		p.updateInventory();
	}
	
	//Reset
	public static void resetItem(Player p)
	{
		if(p == null) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(is == null || is.getType() == Material.AIR)
		{
			p.sendMessage(blobcatraz + "You can't reset Air");
			return;
		}
		int slot = pi.getHeldItemSlot();
		
		is.setItemMeta(null);
		pi.removeItem(new ItemStack[] {is});
		pi.setItem(slot, is);
		
		p.updateInventory();
		p.sendMessage(blobcatraz + "You have reset your §6" + is.getType().toString());
	}
	
	//Repair
	public static void repairItem(Player p)
	{
		if(p == null) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(is == null || is.getType() == Material.AIR)
		{
			p.sendMessage(blobcatraz + "You can't repair Air");
			return;
		}
		is.setDurability((short) 0);
		
		p.updateInventory();
		p.sendMessage(blobcatraz + "Successfully repaired your §6" + is.getType().toString());
	}
	//Give
	public static void giveItem(Player p, ItemStack is)
	{
		if(p == null || is == null) return;
		
		PlayerInventory pi = p.getInventory();
		
		if(pi.firstEmpty() != -1) 
		{
			pi.addItem(is);
			p.sendMessage(blobcatraz + "You have been given §5" + is.getAmount() + " of §6" + is.getType().toString() + ":" + is.getDurability());
		}
		else p.sendMessage(blobcatraz + "Your inventory is too full to recieve items!");
	}
	//Blobcatraz Enchant
	public static void blobcatrazEnchant(Player p, String enchant, int level)
	{
		if (p == null || enchant == null || level == 0) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(is == null || is.getType() == Material.AIR)
		{
			p.sendMessage(blobcatraz + "You can't enchant Air");
			return;
		}
		
		ItemMeta meta = is.getItemMeta();
		
		String lvl = "";
		if(level == 1) lvl = "I";
		if(level == 2) lvl = "II";
		if(level == 3) lvl = "III";
		if(level == 4) lvl = "IV";
		if(level == 5) lvl = "V";
		if(level == 6) lvl = "VI";
		if(level == 7) lvl = "VII";
		if(level == 8) lvl = "VIII";
		if(level == 9) lvl = "IV";
		if(level == 10) lvl = "X";
		
		List<String> newLore = meta.getLore();
		if(newLore == null) setLore(p, "§7" + enchant + " " + lvl);
		else
		{
			newLore.add("§7" + enchant + " " + lvl);
			
			meta.setLore(newLore);
			is.setItemMeta(meta);
		}
		p.updateInventory();
		
		p.sendMessage(blobcatraz + "Attempted to enchant your §e" + is.getType() + " §rwith §b" + enchant + " " + lvl);
	}
	//Get list of enchantments
	public static HashMap<Enchantment, Integer> getAllOPEnchants()
	{
		HashMap<Enchantment, Integer> enchantList = new HashMap<Enchantment, Integer>();
		
		for(Enchantment e : Enchantment.values()) enchantList.put(e, 32767);
		
		return enchantList;
	}
	
//Play the sonic screwdriver sound
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
		if(l == null) return;
		
		Bukkit.getServer().getPluginManager().registerEvents(l, plugin);
	}
	
//Combine arguments
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
	@SuppressWarnings("deprecation")
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
		p.sendMessage(blobcatraz + "You were teleported to §5" + x + "§r,§5" + y + "§r,§5" + z + "§r!");
	}
	//Normal
	@SuppressWarnings("deprecation")
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
		p.sendMessage(blobcatraz + "You were teleported to §5" + x + "§r,§5" + y + "§r,§5" + z + "§r!");
	}
	//Far
	@SuppressWarnings("deprecation")
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
		p.sendMessage(blobcatraz + "You were teleported to §5" + x + "§r,§5" + y + "§r,§5" + z + "§r!");
	}
	
	public static void heal(Player p)
	{
		if(p == null) return;
		
		p.setHealth(p.getMaxHealth());
		for(PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
		p.setFoodLevel(20);
		p.setSaturation(20.0F);
		p.setFireTicks(0);
		p.sendMessage(blobcatraz + "You have been healed!");
	}
	
	public static void setMOTD(String motd)
	{
		ConfigBlobcatraz.config.set("motd", motd);
		ConfigBlobcatraz.saveConfig();
		ConfigBlobcatraz.loadConfig();
	}
}