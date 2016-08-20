package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class ConfigKits 
{
	private static File kitsFolder = new File(Blobcatraz.instance.getDataFolder(), File.separator + "kits");
	
	public static YamlConfiguration load(String kitName)
	{
		File kit = new File(kitsFolder, kitName + ".kit");
		YamlConfiguration yc = YamlConfiguration.loadConfiguration(kit);
		if(!kit.exists()) save(kitName, yc);
		try
		{
			yc.load(kit);
			return yc;
		} catch(Exception ex)
		{
			Util.print("Error loading kit: " + kitName + ": " + ex.getCause());
			return yc;
		}
	}
	
	public static void save(String kitName, FileConfiguration fc)
	{
		if(kitName == null || fc == null) return;
		if(!kitsFolder.exists()) kitsFolder.mkdir();
		File kit = new File(kitsFolder, kitName + ".kit");
		if(!kit.exists())
		{
			try
			{
				kit.createNewFile();
			} catch (Exception ex)
			{
				Util.print("Couldn't create " + kitName + ".yml: " + ex.getCause());
				return;
			}
		}
		try
		{
			fc.save(kit);
		} catch(Exception ex)
		{
			Util.print("Failed to sabe " + kitName + ".yml: " + ex.getCause());
			return;
		}
	}
	
	public static List<ItemStack> getItemsInKit(String kitName)
	{
		if(kitName == null) return null;
		FileConfiguration kit = load(kitName);
		List<ItemStack> items = new ArrayList<ItemStack>();
		try
		{
			for(String s : kit.getConfigurationSection("items").getValues(false).keySet())
			{
				try
				{
					int item = Integer.parseInt(s);
					ItemStack is = kit.getItemStack("items." + item);
					items.add(is);
				} catch (Exception ex)
				{
					Util.print("Invalid kit: " + kitName + ": " + ex.getCause());
					return null;
				}
			}
		} catch(Exception ex) {Util.print("Kit" + kitName + " doesn't have any items!!!");}
		
		return items;
	}
	
	public static void giveKitToPlayer(Player p, String kitName)
	{
		if(p == null || kitName == null) return;
		PlayerInventory pi = p.getInventory();
		List<ItemStack> items = getItemsInKit(kitName);
		for(ItemStack item : items)
		{
			HashMap<Integer, ItemStack> leftover = pi.addItem(item);
			if(!leftover.isEmpty())
			{
				Util.sendTitle(p, ConfigLanguage.getMessage("title.inventoryFull"), "");
				break;
			}
		}
	}
	
	public static boolean createKit(Inventory i, String kitName)
	{
		if(i == null || kitName == null) return false;
		FileConfiguration kit = load(kitName);
		kit.set("name", kitName);
		int item = 0;
		for(ItemStack is : i.getStorageContents())
		{
			if(is!=null && is.getType() != Material.AIR)
			{
				kit.set("items." + item, is);
				item++;
			}
		}
		save(kitName, kit);
		return true;
	}
	
	public static List<String> getKits()
	{
		if(!kitsFolder.exists()) kitsFolder.mkdir();
		List<String> kits = new ArrayList<String>();
		for(File f : kitsFolder.listFiles())
		{
			FileConfiguration fc = load(FilenameUtils.getBaseName(f.getName()));
			String name = fc.getString("name");
			kits.add(name);
		}
		Collections.sort(kits);
		return kits;
	}
	
	public static void deleteKit(String kitName)
	{
		File f = new File(kitsFolder, kitName + ".kit");
		f.delete();
	}
	
	public static boolean doesKitExist(String kitName)
	{
		return getKits().contains(kitName);
	}

	public static void putKitInChest(String kitName, Chest chest)
	{
		Inventory i = chest.getInventory();
		List<ItemStack> kit = ConfigKits.getItemsInKit(kitName);
		i.clear();
		for(ItemStack is : kit)
		{
			HashMap<Integer, ItemStack> leftover = i.addItem(is);
			if(!leftover.isEmpty()) break;
		}
		chest.update(true);
	}
}