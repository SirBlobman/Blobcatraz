package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigShop;

public class ShopSigns implements Listener
{
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onRightClickSign(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a.equals(Action.RIGHT_CLICK_BLOCK))
		{
			BlockState bs = e.getClickedBlock().getState();
			if(!(bs instanceof Sign)) return;
			Sign s = (Sign) bs;
			String[] line = s.getLines();
			if(line[0].equals("§1[Buy]"))
			{
				Material mat = Material.AIR;
				short data = 0;
				int amount = 0;
				try
				{
					String[] item = line[1].split(":");
					if(item.length == 1)
					{
						data = 0;
					}
					else
					{
						data = Short.parseShort(item[1]);
					}
					mat = Material.matchMaterial(item[0].toUpperCase());
					amount = Integer.parseInt(line[2]);
				} 
				catch(Exception ex) {p.sendMessage(Util.blobcatraz + "Invalid shop sign! contact an admin"); return;}
				ItemStack is = new ItemStack(mat, amount, data);
				ConfigShop.buyItemFromServer(p, is);
			}
			if(line[0].equals("§1[Sell]"))
			{
				Material mat = Material.AIR;
				short data = 0;
				int amount = 0;
				try
				{
					String[] item = line[1].split(":");
					if(item.length == 1)
					{
						data = 0;
					}
					else
					{
						data = Short.parseShort(item[1]);
					}
					mat = Material.matchMaterial(item[0]);
					amount = Integer.parseInt(line[2]);
				} 
				catch(Exception ex) {p.sendMessage(Util.blobcatraz + "Invalid shop sign! contact an admin"); return;}
				ItemStack is = new ItemStack(mat, amount, data);
				ConfigShop.sellItemToServer(p, is);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onSignPlace(SignChangeEvent e)
	{
		Player p = e.getPlayer();
		String[] lines = e.getLines();
		if(lines[0].equalsIgnoreCase("[buy]"))
		{
			String[] item = lines[1].split(":");
			Material mat = Material.AIR;
			short data = (short) 0;
			if(item.length == 1)
			{
				try
				{
					mat = Material.matchMaterial(item[0].toUpperCase());
					e.setLine(1, mat.toString());
					data = (short) 0;
				}
				catch(Exception ex)
				{
					p.sendMessage(Util.blobcatraz + "Invalid Item: " + lines[1]);
					return;
				}
			}
			if(item.length == 2)
			{
				try
				{
					mat = Material.matchMaterial(item[0].toUpperCase());
					data = Short.parseShort(item[1]);
					e.setLine(1, mat.toString() + ":" + data);
				}
				catch(Exception ex)
				{
					p.sendMessage(Util.blobcatraz + "Invalid Item: " + lines[1]);
					return;
				}
			}
			int amount = 0;
			try{amount = Integer.parseInt(lines[2]);} catch (Exception ex) {p.sendMessage(Util.blobcatraz + "Invalid price: " + lines[2]);}
			double price = ConfigShop.getBuyPrice(mat, amount);
			
			if(price > 0.0) {e.setLine(3, Double.toString(price)); e.setLine(0, "§1[Buy]");} else {p.sendMessage(Util.blobcatraz + "That item doesn't have a price.");}
			e.getBlock().getState().update(true);
		}
		if(lines[0].equalsIgnoreCase("[sell]"))
		{
			String[] item = lines[1].split(":");
			Material mat = Material.AIR;
			short data = (short) 0;
			if(item.length == 1)
			{
				try
				{
					mat = Material.matchMaterial(item[0].toUpperCase());
					e.setLine(1, mat.toString());
					data = (short) 0;
				}
				catch(Exception ex)
				{
					p.sendMessage(Util.blobcatraz + "Invalid Item: " + lines[1]);
					return;
				}
			}
			if(item.length == 2)
			{
				try
				{
					mat = Material.matchMaterial(item[0].toUpperCase());
					data = Short.parseShort(item[1]);
					e.setLine(1, mat.toString() + ":" + data);
				}
				catch(Exception ex)
				{
					p.sendMessage(Util.blobcatraz + "Invalid Item: " + lines[1]);
					return;
				}
			}
			int amount = 0;
			try{amount = Integer.parseInt(lines[2]);} catch (Exception ex) {p.sendMessage(Util.blobcatraz + "Invalid price: " + lines[2]);}
			if(amount > 64) {amount = 64; e.setLine(2, Integer.toString(64));}
			double price = ConfigShop.getSellPrice(mat, amount);
			
			if(price > 0.0) {e.setLine(3, Double.toString(price)); e.setLine(0, "§1[Sell]");} else {p.sendMessage(Util.blobcatraz + "That item doesn't have a price.");}
			e.getBlock().getState().update(true);
		}
	}
}