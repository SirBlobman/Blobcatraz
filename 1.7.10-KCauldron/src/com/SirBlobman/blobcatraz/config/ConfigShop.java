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
	static Map<String, Double> buyPrices = new TreeMap<String, Double>();
	static Map<String, Double> sellPrices = new TreeMap<String, Double>();

	private static final File shopFile = new File(Blobcatraz.instance.getDataFolder(), "prices.cfg");
	private static final FileConfiguration shopConfig = YamlConfiguration.loadConfiguration(shopFile);

	public static void savePrices()
	{
		if(!shopFile.exists())
		{
			try
			{
				shopFile.createNewFile();
			}
			catch (Exception ex)
			{
				Util.print("Could not create 'prices.cfg'. Items will not sell and buy correctly");
				ex.printStackTrace();
				return;
			}
		}

		for(Entry<String, Double> e : buyPrices.entrySet())
		{
			shopConfig.set("buyPrices." + e.getKey(), e.getValue());
		}
		for(Entry<String, Double> e : sellPrices.entrySet())
		{
			shopConfig.set("sellPrices." + e.getKey(), e.getValue());
		}

		try
		{
			shopConfig.save(shopFile);
		}
		catch (Exception ex)
		{
			Util.print("Failed to save prices.cfg");
			ex.printStackTrace();
			return;
		}
	}

	public static void loadPrices()
	{
		try
		{
			shopConfig.load(shopFile);
		}
		catch (Exception ex)
		{
			Util.print("Failed to load prices.cfg, attempting to create");
			savePrices();
		}

		try
		{
			for(String key : shopConfig.getConfigurationSection("buyPrices").getKeys(false))
			{
				Material m = Material.getMaterial(key);
				buyPrices.put(m.toString(), shopConfig.getDouble("buyPrices." + m.toString()));
			}
			for(String key : shopConfig.getConfigurationSection("sellPrices").getKeys(false))
			{
				Material m = Material.getMaterial(key);
				sellPrices.put(m.toString(), shopConfig.getDouble("sellPrices." + m.toString()));
			}
		}
		catch (Exception ex)
		{
			setDefaultPrices();
			Util.print("prices.cfg is empty or null, attempting to fix");
			ex.printStackTrace();
		}
	}

	public static void setSellPrice(Material m, double price)
	{
		loadPrices();
		sellPrices.put(m.toString(), price);
		buyPrices.put(m.toString(), price * 2);
		savePrices();
	}

	public static double getBuyPrice(Material m)
	{
		loadPrices();
		if(m != null && buyPrices.containsKey(m.toString()))
		{
			return buyPrices.get(m.toString());
		}

		return 0.0;
	}

	public static double getSellPrice(Material m)
	{
		loadPrices();
		if(m != null && sellPrices.containsKey(m.toString()))
		{
			return sellPrices.get(m.toString());
		}

		return 0.0;
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
			if(!buyPrices.containsKey(m))
			{
				buyPrices.put(m, 0.0);
				Util.print("Buy value for " + m + " is now $0");
			}
			if(!sellPrices.containsKey(m))
			{
				sellPrices.put(m, 0.0);
				Util.print("Sell value for " + m + " is now $0");
			}
		}

		savePrices();
	}

	public static void sellItemToServer(Player p, ItemStack is, int amount)
	{
		loadPrices();
		Material m = is.getType();
		ItemStack remove = is;
		is.setAmount(amount);

		if(p.getInventory().contains(remove))
		{
			double sellPrice = getSellPrice(m);
			p.getInventory().removeItem(remove);
			ConfigDatabase.addToPlayerBalance(p.getUniqueId(), sellPrice);
			p.sendMessage(Util.blobcatraz + "You have recieved §5$" + sellPrice + "§r for selling §e" + amount + "§r of §e" + m.toString());
		}
		else
		{
			p.sendMessage(Util.blobcatraz + "You can't sell what you don't have!");
		}
	}

	public static ArrayList<String> getItemList()
	{
		ArrayList<String> ilist = new ArrayList<String>();
		for(Material m : Material.values())
		{
			ilist.add(m.toString());
		}
		Collections.sort(ilist);

		return ilist;
	}
}