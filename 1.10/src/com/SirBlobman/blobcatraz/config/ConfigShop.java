package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class ConfigShop 
{
	static Map<String, Double> sellPrices = new TreeMap<String, Double>();
	
	private static final File shopFile = new File(Blobcatraz.instance.getDataFolder(), "prices.cfg");
	private static FileConfiguration shopConfig = YamlConfiguration.loadConfiguration(shopFile);
	
	public static void savePrices()
	{
		if(!shopFile.exists())
		{
			try{shopFile.createNewFile();}
			catch (Exception ex)
			{
				Util.print("Could not create " + shopFile);
				ex.printStackTrace();
				return;
			}
		}
		
		for(Entry<String, Double> e : sellPrices.entrySet()) shopConfig.set("sellPrices." + e.getKey(), e.getValue());
		
		try{shopConfig.save(shopFile);}
		catch (Exception ex)
		{
			Util.print("Failed to save " + shopFile);
			ex.printStackTrace();
			return;
		}
	}
	
	public static void loadPrices()
	{
		try{shopConfig.load(shopFile);}
		catch(Exception ex)
		{
			Util.print("Failed to load " + shopFile + ", attempting to create");
			savePrices();
		}
		
		try
		{
			for(String key : shopConfig.getConfigurationSection("sellPrices").getKeys(false))
			{
				sellPrices.put(key, shopConfig.getDouble("sellPrices." + key));
			}
		}
		catch (Exception ex)
		{
			Util.print(shopFile + " is empty or null, attempting to fix");
			ex.printStackTrace();
			setDefaultPrices();
		}
	}
	
	public static void setDefaultPrices()
	{
		savePrices();
		
		ArrayList<String> materials = new ArrayList<String>();
		for(Material m : Material.values()) 
		{
			materials.add(m.toString());
		}
		Collections.sort(materials);
		
		for(String m : materials)
		{
			if(!sellPrices.containsKey(m))
			{
				sellPrices.put(m, 0.0);
				Util.print("Sell value for " + m + " is now $0.0");
			}
		}
		
		savePrices();
	}
	
	public static void setSellPrice(Material m, double price)
	{
		loadPrices();
		sellPrices.put(m.toString(), price);
		savePrices();
	}
	
	public static double getSellPrice(Material m)
	{
		if(m == null) return 0.0;
		loadPrices();
		if(!sellPrices.containsKey(m.toString())) return 0.0;
		
		return sellPrices.get(m.toString());
	}
	
	public static double getBuyPrice(Material m)
	{
		if(m == null) return 0.0;
		loadPrices();
		double buyPrice = getSellPrice(m) * 2;
		
		return buyPrice;
	}
	
	public static void sellItemToServer(Player p, ItemStack is, int amount)
	{
		if(p == null || is == null) return;
		Material m = is.getType();
		is.setAmount(amount);
		
		if(p.getInventory().contains(is))
		{
			loadPrices();
			double sellPrice = getSellPrice(m);
			p.getInventory().removeItem(is);
			p.updateInventory();
			ConfigDatabase.addToBalance(p, sellPrice);
			p.sendMessage(Util.blobcatraz + "You have recieved §5$" + sellPrice + " §rfor selling §e" + amount + " §rof §e" + m.toString());
		}
		else
		{
			p.sendMessage(Util.blobcatraz + "You can't sell what you don't have!");
		}
	}
	
	public static ArrayList<String> getItemList()
	{
		ArrayList<String> itemList = new ArrayList<String>();
		for(Material m : Material.values()) itemList.add(m.toString());
		Collections.sort(itemList);
		
		return itemList;
	}
}