package com.SirBlobman.blobcatraz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.listener.Emojis;

/**
 * Utility class for Blobcatraz
 * <br/>Contains many useful utilities
 * @author SirBlobman
 */
public class Util 
{
//Plugin
	static Blobcatraz plugin = Blobcatraz.instance;
	
//Message
	public static final String blobcatraz = color("&1[&6Blobcatraz&1]&r ");
	public static final String blobcatrazUnformatted = uncolor(blobcatraz);
	public static final String pluginEnabled = "This plugin has been §2§lEnabled§r! " + Emojis.getString(Emojis.smiley);
	public static final String pluginDisabled = "This plugin has been §4§lDisabled§r! " + Emojis.getString(Emojis.sad);
	public static final String notEnoughArguments = blobcatraz + "§4Not Enough Arguments!";
	public static final String tooManyArguments = blobcatraz + "§4Too Many Arguments!";
	public static final String invalidArguments = blobcatraz + "§4Invalid Arguments!";
	public static final String noPermission = blobcatraz + "§4You don't have permission: ";
	public static final String commandExecutorNonPlayer = blobcatraz + "This command must be used by a Player";
	public static final String commandExecutorNonLiving = blobcatraz + "This command must be used by a Living Entity";
	public static final String notEnabledInWorld = blobcatraz + "You can't do that in this world!";
	public static final String banned = blobcatraz + "§4You have been banned :\n§r";
	
/**
 * This formats the message with colored chat.
 * @param msg Message that you want to be colored
 * @return String with color codes. <br/>Example: &7 = ChatColor.GRAY
 * @see String
 * @see ChatColor
 */
	public static String color(String msg)
	{
		if(msg == null) return "";
		
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
/**
 * This removes all color codes from a message
 * @param msg Message to remove colors
 * @return String without color. Example: §7Hi = Hi
 * @see String
 * @see ChatColor
 */
	public static String uncolor(String msg)
	{
		if(msg == null) return "";
		return ChatColor.stripColor(msg);
	}
	
/**
 * Broadcasts a message to all player's and to the console
 * @param msg Message to broadcast
 */
	public static void broadcast(String msg)
	{
		if(msg == null) return;
		
		Bukkit.broadcastMessage(blobcatraz + msg);
	}
	
//Item Editing
	/**
	 * Renames the item that is held by the player
	 * @param p Player holding the item
	 * @param name New name for the item
	 * @see Player
	 * @see String
	 */
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
	
	/**
	 * Sets the lore of an item
	 * @param p Player holding the item
	 * @param lore Lore to set
	 * @see Player
	 * @see String
	 */
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
	
	/**
	 * Adds a line of lore to an item
	 * @param p Player holding the item
	 * @param lore Lore to add
	 * @see Player
	 * @see String
	 */
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
	
	/**
	 * Removes the lore of an item
	 * @param p Player holding the item
	 * @see Player
	 */
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
	
	/**
	 * Removes a specific line of lore from an item
	 * @param p Player holding the item
	 * @param line line of lore to remove
	 * @see Player
	 * @see Integer
	 */
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
	
	/**
	 * Reset an item back to its vanilla default
	 * @param p Player holding the item
	 * @see Player
	 */
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
	
	/**
	 * Repair an item back to full durability
	 * Items with metadata will be set to 0
	 * @param p {@link Player} holding the item
	 * @see Player
	 */
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
	
	/**
	 * Gives an item to a Player
	 * @param p {@link Player} which will recieve the item
	 * @param is {@link ItemStack} of the given item
	 * @see Player
	 * @see ItemStack
	 */
	public static void giveItem(Player p, ItemStack is)
	{
		if(p == null || is == null) return;
		int amount = is.getAmount();
		
		PlayerInventory pi = p.getInventory();
		
		if(pi.firstEmpty() != -1) 
		{
			pi.addItem(is);
			if(is.getItemMeta().hasDisplayName())
			{
				p.sendMessage(blobcatraz + "You have been given §5" + amount + " §rof §6" + is.getItemMeta().getDisplayName());
			}
			else
			{
				p.sendMessage(blobcatraz + "You have been given §5" + amount + " §rof §6" + is.getType().toString() + ":" + is.getDurability());
			}
		}
		else p.sendMessage(blobcatraz + "Your inventory is too full to recieve items!");
	}
	
	/**
	 * Used only in this plugin, enchants with the custom made enchantments
	 * @param p {@link Player} holding the item that will be enchanted
	 * @param enchant Name of the enchantment
	 * @param level Level of the enchantment, can go up to 10
	 * @see String
	 * @see Integer
	 * @see Player
	 */
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
	
	/**
	 * Vanilla enchant with a twist! 
	 * Allows enchantments to go up to level 32767
	 * @param p {@link Player} holding the item
	 * @param e {@link Enchantment} to be given
	 * @param lvl level of the enchantment, from 0 to 32767
	 * @see Integer
	 * @see Player
	 * @see Enchantment
	 */
	public static void enchant(Player p, Enchantment e, int lvl)
	{
		if(p == null || e == null) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		if(held == null || held.getType() == Material.AIR) {p.sendMessage(blobcatraz + "You can't enchant Air"); return;}
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		meta.addEnchant(e, lvl, true);
		
		held.setItemMeta(meta);
		p.updateInventory();
		p.sendMessage(blobcatraz + "You enchanted your §6" + held.getType().toString() + " §rwith §7" + e.getName() + "§r:§7" + lvl);
	}
	
	/**
	 * Remove an enchantment from an item
	 * @param p {@link Player} holding the item
	 * @param e {@link Enchantment} to remove
	 * @see Player
	 * @see Enchantment
	 */
	public static void unEnchant(Player p, Enchantment e)
	{
		if(p == null || e == null) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		if(held == null || held.getType() == Material.AIR) {p.sendMessage(blobcatraz + "Air doesn't have any enchantments"); return;}
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		if(meta.getEnchants().containsKey(e))
		{
			meta.removeEnchant(e);
			held.setItemMeta(meta);
			p.updateInventory();
			p.sendMessage(blobcatraz + "Removed §7" + e.getName() + " §rfrom your §6" + held.getType().toString());
		}
		else
		{
			p.sendMessage(blobcatraz + "Your §6" + held.getType().toString() + " §rdoesn't have that enchantment!");
			return;
		}
	}
	
	/**
	 * If you use a Spigot build, this will make an item unbreakable
	 * @param p {@link Player} holding the item
	 * @see Player
	 */
	public static void toggleUnbreakable(Player p)
	{
		if(p == null) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		if(held == null || held.getType() == Material.AIR) {p.sendMessage(blobcatraz + "Air cannot be unbreakable"); return;}
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		try
		{
			if (meta.spigot().isUnbreakable())
			{
				meta.spigot().setUnbreakable(false);
				p.sendMessage(blobcatraz + "Your §6" + held.getType().toString() + " §ris no longer Unbreakable");
			}
			else
			{
				meta.spigot().setUnbreakable(true);
				p.sendMessage(blobcatraz + "Your §6" + held.getType().toString() + " §ris now Unbreakable");
			}
		}
		catch (Exception ex)
		{
			p.sendMessage(blobcatraz + "Items can only be set Unbreakable if the server is SPIGOT");
			return;
		}

		held.setItemMeta(meta);
		p.updateInventory();
	}
	
	/**
	 * Overpowered Enchantment HashMap
	 * @return {@link HashMap} with all enchantments with level 32767
	 * @see HashMap
	 * @see Integer
	 * @see Enchantment
	 */
	public static HashMap<Enchantment, Integer> getAllOPEnchants()
	{
		HashMap<Enchantment, Integer> enchantList = new HashMap<Enchantment, Integer>();
		
		for(Enchantment e : Enchantment.values()) enchantList.put(e, 32767);
		
		return enchantList;
	}
	
	/**
	 * Clears a {@link Player}'s inventory
	 * Keeps their armor and shield (off hand item)
	 * @param p Player to clear
	 * @see Player
	 */
	public static void clearInventory(Player p)
	{
		if(p == null) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack[] armor = pi.getArmorContents().clone();
		ItemStack off = pi.getItemInOffHand().clone();
		
		pi.clear();
		if(armor != null) pi.setArmorContents(armor);
		if(off != null) pi.setItemInOffHand(off);
		p.sendMessage(blobcatraz + "Your inventory has been cleared, but we kept your armor and shield!");
	}
	
	/**
	 * Clear a specific item from a Player's inventory
	 * @param p {@link Player} to clear
	 * @param i {@link Inventory} type that will be cleared (Enderchest?)
	 * @param is {@link ItemStack} to clear from the inventory
	 * @see Player
	 * @see Inventory
	 * @see ItemStack
	 */
	public static void clearItem(Player p, Inventory i, ItemStack is)
	{
		if(i == null || is == null) return;
		
		if(i.contains(is.getType()))
		{
			i.remove(is.getType());
			p.sendMessage(Util.blobcatraz + "Deleted all of §6" + is.getType() + " §rfrom your inventory");
		}
		else
		{
			p.sendMessage(Util.blobcatraz + "You don't have any of §6" + is.getType() + " §rto delete!");
			return;
		}
	}
	
	/**
	 * Clears a Player's armor and off hand item
	 * Does not clear their inventory
	 * @param p Player to clear
	 * @see Player
	 */
	public static void clearArmor(Player p)
	{
		if(p == null) return;
		
		PlayerInventory pi = p.getInventory();
		ItemStack[] blankArmor = new ItemStack[] {};
		pi.setArmorContents(blankArmor);
		pi.setItemInOffHand(new ItemStack(Material.AIR));
		p.sendMessage(blobcatraz + "Your armor has been cleared, but we kept your inventory!");
	}
	
	/**
	 * Clears a Player's Enderchest
	 * Why would someone that isn't evil do this?
	 * @param p Player to clear
	 * @see Player
	 */
	public static void clearEnderChest(Player p)
	{
		if(p == null) return;
		
		Inventory ec = p.getEnderChest();
		ec.clear();
		p.sendMessage(blobcatraz + "Your enderchest has been reset!");
	}
	
	/**
	 * Color an item with its metadata value
	 * Used for Clay, Wool, and Glass
	 * @param is ItemStack to color
	 * @see ItemStack
	 */
	public static ItemStack colorRandom16(ItemStack is)
	{
		Random r = new Random();
		if(is == null) return null;
		short meta = (short) r.nextInt(15);
		is.setDurability(meta);
		return is;
	}
	
	/**
	 * Get a randomly colored leather armor set
	 * @see ItemStack
	 */
	public static ItemStack[] getLeatherArmorRandom()
	{
		Random r = new Random();
		int red = r.nextInt(256), green = r.nextInt(256), blue = r.nextInt(256);
		Color color = Color.fromRGB(red, green, blue);
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET), chestplate = new ItemStack(Material.LEATHER_CHESTPLATE), leggings = new ItemStack(Material.LEATHER_LEGGINGS), boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta head = (LeatherArmorMeta) helmet.getItemMeta(), chest = (LeatherArmorMeta) chestplate.getItemMeta(), legs = (LeatherArmorMeta) leggings.getItemMeta(), feet = (LeatherArmorMeta) boots.getItemMeta();
		head.setColor(color); chest.setColor(color); legs.setColor(color); feet.setColor(color);
		helmet.setItemMeta(head); chestplate.setItemMeta(chest); leggings.setItemMeta(legs); boots.setItemMeta(feet);
		return new ItemStack[] {boots, leggings, chestplate, helmet};
	}
	
/**
 * Play the sonic screwdriver sound
 * @param p Player to play the sound to
 * @see Player
 * @see Player#playSound(Location, String, float, float)
 */
	public static void playSonicSound(Player p)
	{
		if(p == null) return;
		
		Location l = p.getLocation();
		
		p.playSound(l, "sonic-screwdriver", 1, 1);
	}
	
/**
 * Print a message to the system
 * @param msg Message to print
 * @see String
 * @see System#out
 */
	public static void print(String msg)
	{
		System.out.print(blobcatrazUnformatted + msg);
	}
	
/**
 * Register an entire Listener class full of EventHandlers
 * @param l Listener class. Example: new Listener()
 * @see Listener
 */
	public static void regEvent(Listener l)
	{
		if(l == null) return;
		Bukkit.getServer().getPluginManager().registerEvents(l, plugin);
	}
	
/**
 * Combine multiple arguments into one
 * Stolen from the Essentials /msg code
 * @param args Arguments to combine
 * @param start Starting point
 * @return Combined Arguments
 * @see String
 * @see Integer
 */
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
	/**
	 * Tiny random teleportation
	 * Distance is set in the blobcatraz.yml config
	 * Default is 1000 blocks 
	 * @param p Player to teleport
	 * @see Player
	 */
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
	
	/**
	 * Normal random teleportation
	 * Distance is set in the blobcatraz.yml config
	 * Default is 3000 blocks
	 * @param p Player to teleport
	 * @see Player
	 */
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
	
	/**
	 * Far random teleportation
	 * Distance is set in the blobcatraz.yml config
	 * Default is 6000 blocks
	 * @param p
	 * @see Player
	 */
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
	
/**
 * Heal
 * Sets health to 20.0
 * Sets food to 20.0
 * Sets saturation to 20.0
 * Stops the player from burning
 * Removes ALL potion effects
 * @param p Player to heal
 * @see Player
 */
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
	
/**
 * Set the MOTD of the server
 * Saves the value to the config
 * @param motd Message to set
 * @see String
 */
	public static void setMOTD(String motd)
	{
		ConfigBlobcatraz.config.set("motd", motd);
		ConfigBlobcatraz.saveConfig();
		ConfigBlobcatraz.loadConfig();
	}
	
//Time
	/**
	 * Gets the year of the Server's Calendar
	 * @return Current Year. <br/><b>Example:</b> 2016
	 * @see Integer
	 * @see Calendar
	 * @see Calendar#YEAR
	 */
	public static int getYear() {Calendar c = Calendar.getInstance(); return c.get(Calendar.YEAR);}
	/**
	 * Gets the current month as a number
	 * @return Current Month. <br/><b>Example:</b> December = 12
	 * @see Integer
	 * @see Calendar
	 * @see Calendar#MONTH
	 */
	public static int getMonth() {Calendar c = Calendar.getInstance(); return c.get(Calendar.MONTH) + 1;}
	/**
	 * Gets the current day as a number
	 * @return Current Day. <br/><b>Example:</b> 31
	 * @see Integer
	 * @see Calendar
	 * @see Calendar#DAY_OF_MONTH
	 */
	public static int getDay() {Calendar c = Calendar.getInstance(); return c.get(Calendar.DAY_OF_MONTH);}
	/**
	 * Gets the current hour on the 24 clock
	 * @return Current Hour. <br/><b>Example:</b> 1pm = 13
	 * @see Integer
	 * @see Calendar
	 * @see Calendar#HOUR_OF_DAY
	 */
	public static int getHour24() {Calendar c = Calendar.getInstance(); return c.get(Calendar.HOUR_OF_DAY);}
	/**
	 * Gets the current hour on the 12 clock
	 * @return Current Hour. <br/></b>Example:</b> 11
	 * @see Integer
	 * @see Calendar
	 * @see Calendar#HOUR
	 */
	public static int getHour12() 
	{
		Calendar c = Calendar.getInstance();
		if(c.get(Calendar.HOUR) == 0) return 12;
		
		return c.get(Calendar.HOUR);
	}
	/**
	 * Gets the current minute
	 * @return Current Minute.
	 * @see Integer
	 * @see Calendar
	 * @see Calendar#MINUTE
	 */
	public static int getMinute() {Calendar c = Calendar.getInstance(); return c.get(Calendar.MINUTE);}
	/**
	 * Gets the current second
	 * @return Current Second.
	 * @see Integer
	 * @see Calendar
	 * @see Calendar#SECOND
	 */
	public static int getSecond() {Calendar c = Calendar.getInstance(); return c.get(Calendar.SECOND);}
	/**
	 * Gets the current millisecond
	 * @return Current Millisecond.
	 * @see Integer
	 * @see Calendar
	 * @see Calendar#MILLISECOND
	 */
	public static int getMillisecond() {Calendar c = Calendar.getInstance(); return c.get(Calendar.MILLISECOND);}
	/**
	 * Gets the current meridiem of time
	 * @return AM or PM
	 * @see Integer
	 * @see Calendar
	 * @see Calendar#AM_PM
	 */
	public static String getMeridiem()
	{
		Calendar c = Calendar.getInstance();
		String meridiem = c.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
		return meridiem;
	}
	
	/**
	 * Gets the full data
	 * @return Actual Date. <br/><b>Example:</b>8/1/2016
	 * @see String
	 * @see Calendar
	 * @see String#format(String, Object...)
	 */
	public static String getDate()
	{
		String date = String.format("%02d/%02d/%04d", getMonth(), getDay(), getYear());
		return date;
	}
	
	/**
	 * Gets the full time with a 24-hour clock
	 * @return Full Time. Example: 13:39:29
	 * @see String
	 * @see Calendar
	 * @see String#format(String, Object...)
	 */
	public static String getTime24()
	{
		return String.format("%02d:%02d:%02d", getHour24(), getMinute(), getSecond());
	}
	
	/**
	 * Gets the full time with a 12-hour clock
	 * @return Full Time. Example: 12:39:21 AM
	 * @see String
	 * @see Calendar
	 * @see String#format(String, Object...)
	 */
	public static String getTime12()
	{
		return String.format("%02d:%02d:%02d %s", getHour12(), getMinute(), getSecond(), getMeridiem());
	}
	
	/**
	 * Gets a list of matching strings from an original list and an argument
	 * @param original Original list to pull from
	 * @param arg Argument to check against the list
	 * @return List with Strings that match
	 * @see String
	 * @see List
	 * @see ArrayList
	 */
	public static List<String> getMatchingStrings(List<String> original, String arg)
	{
		if(original == null || arg == null) return null;
		List<String> n = new ArrayList<String>();
		
		for(String s : original)
		{
			if(s.toLowerCase().startsWith(arg.toLowerCase()))
			{
				n.add(s);
			}
		}
		
		return n;
	}
	
/**
 * Get the join message
 * @param p Player to get the message for
 * @return Message formatted from the config
 */
	public static String getJoinMessage(Player p)
	{
		ConfigBlobcatraz.loadConfig();
		String msg = ConfigBlobcatraz.config.getString("player.joinMessage");
		msg = msg.replace("%username%", p.getName());
		msg = msg.replace("%displayname%", p.getDisplayName());
		return color(msg);
	}
	
/**
 * Get the join message
 * @param p Player to get the message for
 * @return Message formatted from the config
 */
	public static String getQuitMessage(Player p)
	{
		ConfigBlobcatraz.loadConfig();
		String msg = ConfigBlobcatraz.config.getString("player.quitMessage");
		msg = msg.replace("%username%", p.getName());
		msg = msg.replace("%displayname%", p.getDisplayName());
		return color(msg);
	}
	
/**
 * Gets the UUID of the owner of the server, set from the config
 * @return UUID of the owner
 */
	public static UUID getOwner()
	{
		ConfigBlobcatraz.loadConfig();
		String uuid = ConfigBlobcatraz.config.getString("random.owner");
		if(uuid == null) return null;
		return UUID.fromString(uuid);
	}
	
	public static void pingPlayer(Player p)
	{
		if(p == null) return;
		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
	}
}