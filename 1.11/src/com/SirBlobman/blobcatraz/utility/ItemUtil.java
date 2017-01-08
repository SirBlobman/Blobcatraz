package com.SirBlobman.blobcatraz.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.SirBlobman.blobcatraz.enchant.Enchant;
import com.google.common.collect.Maps;

public class ItemUtil extends Util
{
	public static boolean air(ItemStack is)
	{
		if(is == null) return true;
		Material m = is.getType();
		Material a = Material.AIR;
		if(m == a) return true;
		return false;
	}
	
	public static String name(ItemStack is)
	{
		if(air(is)) return "AIR";
		ItemMeta meta = is.getItemMeta();
		boolean b = meta.hasDisplayName();
		if(b) return meta.getDisplayName();
		Material m = is.getType();
		short d = is.getDurability();
		boolean b1 = (d == 0);
		boolean b2 = (d == Short.MAX_VALUE);
		if(b1 || b2) return m.name();
		return m.name() + ":" + d;
	}
	
	public static List<String> lore(ItemStack is)
	{
		if(air(is)) return null;
		ItemMeta meta = is.getItemMeta();
		boolean b = meta.hasLore();
		if(b) return meta.getLore();
		return null;
	}
	
	public static ItemStack held(Player p)
	{
		if(p == null) return null;
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		if(held == null) held = pi.getItemInOffHand();
		return held;
	}
	
	public static void rename(ItemStack is, String name)
	{
		if(air(is)) return;
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(format(name));
		is.setItemMeta(meta);
	}
	
	public static void setLore(ItemStack is, String lore)
	{
		if(air(is) || lore == null) return;
		ItemMeta meta = is.getItemMeta();
		String[] split = lore.split("/n");
		List<String> nlore = new ArrayList<String>();
		for(String s : split) nlore.add(format(s));
		meta.setLore(nlore);
		is.setItemMeta(meta);
	}
	
	public static void addLore(ItemStack is, String lore)
	{
		if(air(is) || lore == null) return;
		ItemMeta meta = is.getItemMeta();
		String[] split = lore.split("/n");
		List<String> nlore = meta.getLore();
		if(nlore == null) {setLore(is, lore); return;}
		
		for(String s : split) nlore.add(format(s));
		meta.setLore(nlore);
		is.setItemMeta(meta);
	}
	
	public static void clearLore(ItemStack is)
	{
		if(air(is)) return;
		ItemMeta meta = is.getItemMeta();
		List<String> empty = new ArrayList<String>();
		meta.setLore(empty);
		is.setItemMeta(meta);
	}
	
	public static void removeLore(ItemStack is, int l)
	{
		if(air(is)) return;
		ItemMeta meta = is.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(Math.max(0, l - 1));
		meta.setLore(lore);
		is.setItemMeta(meta);
	}
	
	public static void reset(ItemStack is)
	{
		if(air(is)) return;
		is.setItemMeta(null);
	}
	
	public static void repair(ItemStack is)
	{
		if(air(is)) return;
		is.setDurability((short) 0);
	}
	
	public static List<String> items()
	{
		Material[] mats = Material.values();
		List<String> list = new ArrayList<String>();
		for(Material mat : mats)
		{
			String name = mat.name();
			list.add(name);
		}
		return list;
	}
	
	public static void give(Player p, ItemStack is)
	{
		if(p == null || air(is)) return;
		PlayerInventory pi = p.getInventory();
		if(pi.firstEmpty() != -1)
		{
			String name = name(is);
			int amount = is.getAmount();
			String msg = prefix + option("command.item.recieve item", amount, name);
			pi.addItem(is);
			p.sendMessage(msg);
			return;
		}
		p.sendMessage(prefix + option("player.inventory.full"));
	}
	
	public static void bEnchant(ItemStack is, Enchant e, int level)
	{
		if(air(is) || e == null || level <= 0 || level >= 32768) return;
		String enchant = format(e.getName() + " &f" + numberToRoman(level));
		addLore(is, enchant);
	}
	
	public static void enchant(ItemStack is, Enchantment e, int level)
	{
		if(air(is) || e == null || level < 0 || level >= 32768) return;
		ItemMeta meta = is.getItemMeta();
		if(level == 0)
		{
			meta.removeEnchant(e);
			is.setItemMeta(meta);
			return;
		}
		meta.addEnchant(e, level, true);
		is.setItemMeta(meta);
	}
	
	public static boolean unbreakable(ItemStack is)
	{
		if(air(is)) return false;
		try
		{
			ItemMeta meta = is.getItemMeta();
			return meta.isUnbreakable();
		} catch(Exception ex) {return false;}
	}
	
	public static void toggleUnbreakable(ItemStack is)
	{
		if(air(is)) return;
		try
		{
			ItemMeta meta = is.getItemMeta();
			boolean u = unbreakable(is);
			meta.setUnbreakable(!u);
			is.setItemMeta(meta);
		} catch(Exception ex) {return;}
	}
	
	public static Map<Enchantment, Integer> overpowered()
	{
		HashMap<Enchantment, Integer> map = Maps.newHashMap();
		Enchantment[] all = Enchantment.values();
		for(Enchantment e : all) map.put(e, 32767);
		map.remove(Enchantment.LOOT_BONUS_BLOCKS);
		map.remove(Enchantment.LOOT_BONUS_MOBS);
		return map;
	}
	
	public static ItemStack color16(ItemStack is, boolean d)
	{
		if(air(is)) return is;
		short dam = (short) R.nextInt(15);
		String name = option("item.disco.name");
		if(d) rename(is, name);
		is.setDurability(dam);
		return is;
	}
	
	public static ItemStack[] leather256(boolean d)
	{
		int r = R.nextInt(256);
		int g = R.nextInt(256);
		int b = R.nextInt(256);
		Color c = Color.fromRGB(r, g, b);
		
		ItemStack he = new ItemStack(Material.LEATHER_HELMET);
		ItemStack ch = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack le = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack bo = new ItemStack(Material.LEATHER_BOOTS);

		LeatherArmorMeta head = (LeatherArmorMeta) he.getItemMeta();
		LeatherArmorMeta ches = (LeatherArmorMeta) ch.getItemMeta();
		LeatherArmorMeta legs = (LeatherArmorMeta) le.getItemMeta();
		LeatherArmorMeta feet = (LeatherArmorMeta) bo.getItemMeta();
		
		head.setColor(c);
		ches.setColor(c);
		legs.setColor(c);
		feet.setColor(c);
		
		he.setItemMeta(head);
		ch.setItemMeta(ches);
		le.setItemMeta(legs);
		bo.setItemMeta(feet);
		
		ItemStack[] armor = new ItemStack[] {bo, le, ch, he};
		if(d)
		{
			String name = option("item.disco.name");
			for(ItemStack is : armor) rename(is, name);
		}
		return armor;
	}
	
	public static boolean glass(Block b)
	{
		Material mat = b.getType();
		ItemStack is = new ItemStack(mat);
		return glass(is);
	}
	
	public static boolean glass(ItemStack is)
	{
		if(air(is)) return false;
		Material mat = is.getType();
		boolean b1 = (mat == Material.GLASS);
		boolean b2 = (mat == Material.THIN_GLASS);
		boolean b3 = (mat == Material.STAINED_GLASS);
		boolean b4 = (mat == Material.STAINED_GLASS_PANE);
		boolean b5 = (b1 || b2 || b3 || b4);
		return b5;
	}
}