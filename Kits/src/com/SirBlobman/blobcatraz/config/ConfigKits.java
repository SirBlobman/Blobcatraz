package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigKits
{
	private static File folder = new File(Blobcatraz.folder, "kits");
	
	public static YamlConfiguration load(String name)
	{
		if(name == null) return null;
		File file = new File(folder, name + ".kit");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if(!file.exists()) save(name, config);
		try{config.load(file); return config;}
		catch(Exception ex) {Util.print("Failed to load kit: " + file + ": " + ex.getMessage()); return null;}
	}
	
	public static void save(String name, YamlConfiguration config)
	{
		if(name == null || config == null) return;
		File file = new File(folder, name + ".kit");
		if(!file.exists())
		{
			try
			{
				folder.mkdirs();
				file.createNewFile();
			} catch(Exception ex)
			{
				Util.print("Failed to create " + file + ": " + ex.getMessage());
				return;
			}
		}
		
		try{config.save(file);}
		catch(Exception ex)
		{
			Util.print("Failed to save " + file + ": " + ex.getMessage());
			return;
		}
	}
	
	public static boolean exists(String name)
	{
		List<String> kits = skits();
		return kits.contains(name);
	}
	
	public static List<String> skits()
	{
		List<String> list = new ArrayList<String>();
		if(!folder.exists()) folder.mkdirs();
		File[] files = folder.listFiles();
		for(File file : files)
		{
			String name = file.getName();
			String end = ".kit";
			if(name.endsWith(end))
			{
				String f = FilenameUtils.getBaseName(name);
				YamlConfiguration config = load(f);
				String kit = config.getString("name");
				if(kit != null) list.add(kit);
			}
		}
		
		if(list != null) Collections.sort(list);
		return list;
	}
	
	public static List<Kit> kits()
	{
		List<Kit> list = new ArrayList<Kit>();
		List<String> slist = skits();
		for(String s : slist)
		{
			List<ItemStack> items = kit(s);
			ItemStack icon = items.get(0);
			Kit kit = new Kit(s, icon, items);
			list.add(kit);
		}
		return list;
	}
	
	public static List<ItemStack> kit(String name)
	{
		if(name == null) return null;
		YamlConfiguration config = load(name);
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		try
		{
			ConfigurationSection section = config.getConfigurationSection("items");
			Map<String, Object> map = section.getValues(false);
			Set<String> set = map.keySet();
			for(String s : set)
			{
				try
				{
					int i = Integer.parseInt(s);
					ItemStack is = config.getItemStack("items." + i);
					list.add(is);
				} catch(Exception ex)
				{
					Util.print("Invalid kit: " + name + ": " + ex.getMessage());
					return null;
				}
			}
		} catch(Exception ex) {Util.print(name + " doesn't have any items!");}
		return list;
	}
	
	public static void give(Player p, String name)
	{
		if(p == null || name == null) return;
		if(!exists(name)) return;
		PlayerInventory pi = p.getInventory();
		List<ItemStack> kit = kit(name);
		for(ItemStack is : kit)
		{
			HashMap<Integer, ItemStack> left = pi.addItem(is);
			if(!left.isEmpty())
			{
				String full = Util.option("title.inventory full");
				PlayerUtil.title(p, full, "");
				break;
			}
		}
	}
	
	public static void create(Inventory i, String name)
	{
		if(i == null || name == null) return;
		YamlConfiguration kit = load(name);
		kit.set("name", name);
		kit.set("icon", new ItemStack(Material.DIAMOND_SWORD));
		int o = 0;
		ItemStack[] items = i.getStorageContents();
		for(ItemStack is : items)
		{
			if(!ItemUtil.air(is))
			{
				kit.set("items." + o, is);
				o++;
			}
		}
		save(name, kit);
	}
	
	public static void delete(String name)
	{
		if(name == null) return;
		File file = new File(folder, name + ".kit");
		file.delete();
	}
	
	public static void kitToChest(String name, Chest chest)
	{
		if(name == null || chest == null) return;
		if(!exists(name)) return;
		Inventory i = chest.getInventory();
		List<ItemStack> kit = kit(name);
		i.clear();
		
		for(ItemStack is : kit)
		{
			HashMap<Integer, ItemStack> left = i.addItem(is);
			if(!left.isEmpty()) break;
		}
		chest.update();
	}
}