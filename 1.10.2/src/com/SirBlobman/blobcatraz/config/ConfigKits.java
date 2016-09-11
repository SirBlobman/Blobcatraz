package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ConfigKits 
{
	private static File folder = new File(Blobcatraz.folder, "kits");
	
	public static YamlConfiguration load(String name)
	{
		if(name == null) return null;
		File kit = new File(folder, name + ".kit");
		YamlConfiguration yc = YamlConfiguration.loadConfiguration(kit);
		if(!kit.exists()) save(name, yc);
		try{yc.load(kit);}
		catch(Exception ex) {Util.print("Error loading kit: " + kit + ": " + ex.getCause());}
		return yc;
	}
	
	public static void save(String name, YamlConfiguration yc)
	{
		if(name == null || yc == null) return;
		if(!folder.exists()) folder.mkdir();
		File kit = new File(folder, name + ".kit");
		if(!kit.exists())
		{
			try{kit.createNewFile();}
			catch(Exception ex) 
			{
				Util.print("Failed to create " + kit + ": " + ex.getCause()); 
				return;
			}
		}
		
		try{yc.save(kit);}
		catch(Exception ex)
		{
			Util.print("Failed to save " + kit + ": " + ex.getCause());
			return;
		}
	}
	
	public static void reload()
	{
		for(String s : ConfigKits.getKits()) 
		{
			YamlConfiguration yc = load(s);
			save(s, yc);
		}
	}
	
	public static List<ItemStack> getKit(String name)
	{
		if(name == null) return null;
		YamlConfiguration kit = load(name);
		List<ItemStack> items = new ArrayList<ItemStack>();
		try
		{
			ConfigurationSection cs = kit.getConfigurationSection("items");
			Map<String, Object> mso = cs.getValues(false);
			Set<String> ss = mso.keySet();
			for(String s : ss)
			{
				try
				{
					int i = Integer.parseInt(s);
					ItemStack is = kit.getItemStack("items." + i);
					items.add(is);
				} catch(Exception ex)
				{
					Util.print("Invalid kit: " + name + ": " + ex.getCause());
					return null;
				}
			}
		} catch(Exception ex) {Util.print("Kit " + name + " doesn't have any items!");}
		return items;
	}
	
	public static void giveKit(Player p, String name)
	{
		if(p == null || name == null) return;
		if(!exists(name)) return;
		PlayerInventory pi = p.getInventory();
		List<ItemStack> items = getKit(name);
		for(ItemStack item : items)
		{
			HashMap<Integer, ItemStack> leftover = pi.addItem(item);
			if(!leftover.isEmpty())
			{
				String full = Util.message("title.inventoryFull");
				PlayerUtil.sendTitle(p, full, "");
				break;
			}
		}
	}
	
	public static boolean create(Inventory i, String name)
	{
		if(i == null || name == null) return false;
		YamlConfiguration kit = load(name);
		kit.set("name", name);
		int o = 0;
		ItemStack[] items = i.getStorageContents();
		for(ItemStack item : items)
		{
			if(!ItemUtil.isAir(item))
			{
				kit.set("items." + o, item);
				o++;
			}
		}
		save(name, kit);
		return true;
	}
	
	public static List<String> getKits()
	{
		if(!folder.exists()) folder.mkdir();
		List<String> kits = new ArrayList<String>();
		File[] files = folder.listFiles();
		for(File kit : files)
		{
			String name = kit.getName();
			String end = ".kit";
			if(name.endsWith(end))
			{
				String file = FilenameUtils.getBaseName(name);
				YamlConfiguration yc = load(file);
				String name2 = yc.getString("name");
				kits.add(name2);
			}
		}
		Collections.sort(kits);
		return kits;
	}
	
	public static void delete(String name)
	{
		if(name == null) return;
		File f = new File(folder, name + ".kit");
		f.delete();
	}
	
	public static boolean exists(String name)
	{
		return getKits().contains(name);
	}
	
	public static void kitToChest(String name, Chest chest)
	{
		if(name == null || chest == null) return;
		if(!exists(name)) return;
		Inventory i = chest.getInventory();
		List<ItemStack> kit = getKit(name);
		i.clear();
		
		for(ItemStack item : kit)
		{
			HashMap<Integer, ItemStack> left = i.addItem(item);
			if(!left.isEmpty()) break;
		}
		chest.update();
	}
}