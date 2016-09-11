package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ConfigShop 
{
	private static File file = new File(Blobcatraz.folder, "shop.yml");
	private static YamlConfiguration shop = YamlConfiguration.loadConfiguration(file);
	
	public static YamlConfiguration load()
	{
		if(!file.exists()) save();
		try{shop.load(file); writeDefaults();} 
		catch(Exception ex) {Util.print("Failed to load " + file + ": " + ex.getCause());}
		return shop;
	}
	
	public static void save()
	{
		if(!file.exists())
		{
			try{file.createNewFile(); writeDefaults();} 
			catch(Exception ex) {Util.print("Failed to create " + file + ": " + ex.getCause());}
			return;
		}
		try {shop.save(file);}
		catch(Exception ex) {Util.print("Failed to save " + file + ": " + ex.getCause());}
	}
	
	public static void reload()
	{
		load();
		save();
	}
	
	public static void writeDefaults()
	{
		final List<String> items = getMaterialNames();
		for(String s : items) set(s + ".0", 0.0D);
		save();
	}
	
	private static void set(String key, double price)
	{
		if(shop.get(key) == null) shop.set(key, price);
	}
	
	public static void setPrice(ItemStack item, double price)
	{
		if(ItemUtil.isAir(item) || Double.isNaN(price)) return;
		Material type = item.getType();
		String mat = type.name();
		short meta = item.getDurability();
		
		FileConfiguration fc = load();
		fc.set(mat + "." + meta, price);
		save();
	}
	
	public static double getPrice(ItemStack item)
	{
		if(ItemUtil.isAir(item)) return 0.0D;
		Material type = item.getType();
		String mat = type.name();
		short meta = item.getDurability();
		int amount = item.getAmount();
		FileConfiguration fc = load();
		double price = fc.getDouble(mat + "." + meta);
		price *= amount;
		return price;
	}
	
	public static void buy(Player p, ItemStack toBuy)
	{
		if(p == null || ItemUtil.isAir(toBuy)) return;
		PlayerInventory pi = p.getInventory();
		double balance = ConfigDatabase.getBalance(p);
		double buy = getPrice(toBuy) * 2;
		if(balance >= buy)
		{
			if(pi.firstEmpty() != -1)
			{
				pi.addItem(toBuy);
				p.sendMessage(Util.blobcatraz + Util.message("shop.successfulBuy", toBuy.getAmount(), ItemUtil.getName(toBuy), Util.monetize(buy)));
			}
			else p.sendMessage(Util.blobcatraz + Util.message("player.inventory.full"));
			return;
		}
		p.sendMessage(Util.blobcatraz + Util.message("shop.notEnoughMoney"));
	}
	
	public static void sell(Player p, ItemStack toSell)
	{
		if(p == null || ItemUtil.isAir(toSell)) return;
		PlayerInventory pi = p.getInventory();
		double sell = getPrice(toSell);
		if(pi.contains(toSell))
		{
			pi.remove(toSell);
			ConfigDatabase.addMoney(p, sell);
			p.sendMessage(Util.blobcatraz + Util.message("shop.successfulSell", toSell.getAmount(), ItemUtil.getName(toSell), Util.monetize(sell)));
		}
		else
		{
			p.sendMessage(Util.blobcatraz + Util.message("shop.missingItem", ItemUtil.getName(toSell)));
			return;
		}
	}
	
	public static void sell(Player p, Inventory i)
	{
		if(p == null || i == null) return;
		ItemStack[] toSell = i.getContents().clone();
		i.clear();
		for(ItemStack item : toSell)
		{
			double sell = getPrice(item);
			ConfigDatabase.addMoney(p, sell);
			int amount = item.getAmount();
			String name = ItemUtil.getName(item);
			String price = Util.monetize(sell);
			p.sendMessage(Util.blobcatraz + Util.message("shop.successfulSell", amount, name, price));
		}
	}
	
	public static List<String> getMaterialNames()
	{
		List<String> list = new ArrayList<String>();
		for(Material m : Material.values()) list.add(m.name());
		Collections.sort(list);
		return list;
	}
}