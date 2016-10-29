package com.SirBlobman.blobcatraz.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.SirBlobman.blobcatraz.enchant.Enchant;
import com.google.common.collect.Maps;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemUtil 
{
	private static Random r = new Random();
	
	/**
	 * Checks if an ItemStack is air
	 * @param is ItemStack to check
	 * @return <b>true</b> if the ItemStack's type is {@link Material#AIR}
	 * <br><b>true</b> if the ItemStack is null
	 * <br><b>false</b> if the ItemStack's type is not {@link Material#AIR}
	 */
	public static boolean isAir(ItemStack is)
	{
		if(is == null) return true;
		Material mat = is.getType();
		if(mat == Material.AIR) return true;
		return false;
	}
	
	/**
	 * Gets the name of an ItemStack
	 * @param is ItemStack to get the name of
	 * @return {@link ItemMeta#getDisplayName()} if the ItemStack has one
	 * <br> otherwise, the ItemStack's type name is returned
	 * @see Material#name()
	 */
	public static String getName(ItemStack is)
	{
		if(isAir(is)) return Material.AIR.name();
		ItemMeta meta = is.getItemMeta();
		if(meta.hasDisplayName()) return meta.getDisplayName();
		Material mat = is.getType();
		short damage = is.getDurability();
		if(damage == 0 || damage == Short.MAX_VALUE) return mat.name();
		return mat.name() + ":" + damage;
	}
	
	/**
	 * Get the item that a Player is holding
	 * @param p Player
	 * @return ItemStack that the Player is holding
	 * @see PlayerInventory#getItemInMainHand()
	 * @see PlayerInventory#getItemInOffHand()
	 */
	public static ItemStack getHeld(Player p)
	{
		if(p == null) return null;
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		if(held == null) held = pi.getItemInOffHand();
		if(held == null) return null;
		return held;
	}
	
	/**
	 * Rename an item
	 * @param is ItemStack to rename
	 * @param name Name to use
	 * @return <b>true</b> if the ItemStack was successfully renamed
	 * <br><b>false</b> if the ItemStack was null
	 * <br><b>false</b> if the ItemStack was {@link Material#AIR}
	 * <br><b>false</b> if there were any failures
	 */
	public static boolean rename(ItemStack is, String name)
	{
		if(isAir(is)) return false;
		try
		{
			ItemMeta meta = is.getItemMeta();
			meta.setDisplayName(Util.format(name));
			is.setItemMeta(meta);
			return true;
		} catch(Exception ex) {return false;}
	}
	
	/**
	 * Set the lore of an item
	 * @param is ItemStack to set
	 * @param lore lore to use
	 * @return <b>true</b> if the lore was successfully set
	 * <br><b>false</b> if the ItemStack was null
	 * <br><b>false</b> if the ItemStack was {@link Material#AIR}
	 * <br><b>false</b> if there were any failures
	 */
	public static boolean setLore(ItemStack is, String lore)
	{
		if(isAir(is) || lore == null) return false;
		try
		{
			ItemMeta meta = is.getItemMeta();
			String[] split = lore.split("/n");
			List<String> nlore = new ArrayList<String>();
			for(String s : split) nlore.add(Util.format(s));
			meta.setLore(nlore);
			is.setItemMeta(meta);
			return true;
		} catch(Exception ex) {return false;}
	}
	
	public static boolean addLore(ItemStack is, String lore)
	{
		if(isAir(is) || lore == null) return false;
		try
		{
			ItemMeta meta = is.getItemMeta();
			String[] split = lore.split(":");
			List<String> nlore = meta.getLore();
			if(nlore == null) return setLore(is, lore);
			
			for(String s : split) nlore.add(Util.format(s));
			meta.setLore(nlore);
			is.setItemMeta(meta);
			return true;
		} catch(Exception ex) {return false;}
	}
	
	public static boolean clearLore(ItemStack is)
	{
		if(isAir(is)) return false;
		try
		{
			ItemMeta meta = is.getItemMeta();
			List<String> empty = Arrays.asList();
			meta.setLore(empty);
			is.setItemMeta(meta);
			return true;
		} catch(Exception ex) {return false;}
	}
	
	public static boolean removeLore(ItemStack is, int line)
	{
		if(isAir(is)) return false;
		try
		{
			ItemMeta meta = is.getItemMeta();
			List<String> lore = meta.getLore();
			lore.remove(Math.min(0, line - 1));
			meta.setLore(lore);
			is.setItemMeta(meta);
			return true;
		} catch(Exception ex) {return false;}
	}
	
	public static List<String> getLore(ItemStack is)
	{
		if(isAir(is)) return null;
		try
		{
			ItemMeta meta = is.getItemMeta();
			List<String> lore = meta.getLore();
			return lore;
		} catch(Exception ex) {return null;}
	}
	
	public static void reset(ItemStack is)
	{
		if(isAir(is)) return;
		is.setItemMeta(null);
	}
	
	public static boolean repair(ItemStack is)
	{
		if(isAir(is)) return false;
		try
		{
			is.setDurability((short) 0);
			return true;
		} catch(Exception ex) {return false;}
	}
	
	public static void give(Player p, ItemStack is)
	{
		if(p == null || isAir(is)) return;
		PlayerInventory pi = p.getInventory();
		if(pi.firstEmpty() != -1)
		{
			String name = getName(is);
			int amount = is.getAmount();
			String msg = Util.blobcatraz + Util.message("player.inventory.recieveItem", amount, name);
			pi.addItem(is);
			p.sendMessage(msg);
		}
		else p.sendMessage(Util.blobcatraz + Util.message("player.inventory.full"));
	}
	
	public static boolean blobcatrazEnchant(ItemStack is, Enchant e, int level)
	{
		if(isAir(is) || e == null || level <= 0 || level >= 32768) return false;
		try
		{
			String enchant = e.getName() + " §f" + Util.numberToRoman(level);
			return addLore(is, enchant);
		} catch(Exception ex) {return false;}
	}
	
	public static boolean enchant(ItemStack is, Enchantment e, int level)
	{
		if(isAir(is) || e == null || level <= 0 || level >= 32768) return false;
		try
		{
			ItemMeta meta = is.getItemMeta();
			meta.addEnchant(e, level, true);
			is.setItemMeta(meta);
			return true;
		} catch(Exception ex) {return false;}
	}
	
	public static boolean unEnchant(ItemStack is, Enchantment e)
	{
		if(isAir(is) || e == null) return false;
		try
		{
			ItemMeta meta = is.getItemMeta();
			meta.removeEnchant(e);
			is.setItemMeta(meta);
			return true;
		} catch(Exception ex) {return false;}
	}
	
	public static boolean isUnbreakable(ItemStack is)
	{
		if(isAir(is)) return false;
		try
		{
			ItemMeta meta = is.getItemMeta();
			return meta.spigot().isUnbreakable();
		} catch(Exception ex) {Util.print("Could not get unbreakable status of " + getName(is) + ": " + ex.getCause()); return false;}
	}
	
	public static boolean toggleUnbreakable(ItemStack is)
	{
		if(isAir(is)) return false;
		try
		{
			ItemMeta meta = is.getItemMeta();
			boolean u = isUnbreakable(is);
			meta.spigot().setUnbreakable(!u);
			is.setItemMeta(meta);
			return true;
		} catch(Exception ex) {Util.print("Could not get unbreakable status of " + getName(is) + ": " + ex.getCause()); return false;} 
	}
	
	public static Map<Enchantment, Integer> getOPEnchants()
	{
		HashMap<Enchantment, Integer> enchants = Maps.newHashMap();
		for(Enchantment e : Enchantment.values()) enchants.put(e, 32767);
		enchants.remove(Enchantment.LOOT_BONUS_BLOCKS);
		enchants.remove(Enchantment.LOOT_BONUS_MOBS);
		return enchants;
	}
	
	public static ItemStack color16(ItemStack is, boolean disco)
	{
		if(isAir(is)) return is;
		short meta = (short) r.nextInt(15);
		String discon = Util.message("item.disco.name");
		if(disco) rename(is, discon);
		is.setDurability(meta);
		return is;
	}
	
	public static ItemStack[] getLeatherArmor256(boolean disco)
	{
		int red = r.nextInt(256), green = r.nextInt(256), blue = r.nextInt(256);
		Color color = Color.fromRGB(red, green, blue);
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET), chestplate = new ItemStack(Material.LEATHER_CHESTPLATE), leggings = new ItemStack(Material.LEATHER_LEGGINGS), boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta head = (LeatherArmorMeta) helmet.getItemMeta(), chest = (LeatherArmorMeta) chestplate.getItemMeta(), legs = (LeatherArmorMeta) leggings.getItemMeta(), feet = (LeatherArmorMeta) boots.getItemMeta();
		head.setColor(color); chest.setColor(color); legs.setColor(color); feet.setColor(color);
		helmet.setItemMeta(head); chestplate.setItemMeta(chest); leggings.setItemMeta(legs); boots.setItemMeta(feet);
		ItemStack[] armor = new ItemStack[] {boots, leggings, chestplate, helmet};
		if(disco)
		{
			String discon = Util.message("item.disco.name");
			for(ItemStack is : armor) rename(is, discon);
		}
		return armor;
	}

	public static boolean isGlass(Block b)
	{
		return isGlass(new ItemStack(b.getType()));
	}
	
	public static boolean isGlass(ItemStack is)
	{
		if(isAir(is)) return false;
		Material mat = is.getType();
		if(mat == Material.GLASS) return true;
		if(mat == Material.THIN_GLASS) return true;
		if(mat == Material.STAINED_GLASS) return true;
		if(mat == Material.STAINED_GLASS_PANE) return true;
		return false;
	}
}