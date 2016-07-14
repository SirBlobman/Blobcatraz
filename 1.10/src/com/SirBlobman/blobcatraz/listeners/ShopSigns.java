package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
			Block b = e.getClickedBlock();
			BlockState bs = b.getState();
			if(bs instanceof Sign)
			{
				Sign s = (Sign) bs;
				String line1 = s.getLine(0);
				String line2 = s.getLine(1);
				String line3 = s.getLine(2);
				String line4 = s.getLine(3);
				if(line1 == null || line2 == null || line3 == null) return;
				Material mat = null;
				int amount = 0;
				short data = 0;

				if(line1.equals("§1[Buy]"))
				{
					try
					{
						String[] item = line2.split(":");
						mat = Material.matchMaterial(item[0]);
						data = Short.parseShort(item[1]);
						amount = Integer.parseInt(line3);
					}catch(Exception ex) {return;}

					if(mat == null || amount == 0) return;
					if(amount > 64) {amount = 64;}

					ItemStack is = new ItemStack(mat, amount, data);
					if(Double.parseDouble(line4) != ConfigShop.getBuyPrice(mat, amount)) 
					{
						Bukkit.getServer().getPluginManager().callEvent(new SignChangeEvent(b, p, new String[] {line1, line2, line3, Double.toString(ConfigShop.getBuyPrice(mat, amount))}));
						return;
					}
					
					ConfigShop.buyItemFromServer(p, is);
				}
				if(line1.equals("§1[Sell]"))
				{
					try
					{
						String[] item = line2.split(":");
						mat = Material.matchMaterial(item[0]);
						data = Short.parseShort(item[1]);
						amount = Integer.parseInt(line3);
					}catch(Exception ex) {return;}

					if(mat == null || amount == 0) return;
					if(amount > 64) {amount = 64;}

					ItemStack is = new ItemStack(mat, amount, data);
					if(Double.parseDouble(line4) != ConfigShop.getSellPrice(mat, amount)) 
					{
						Bukkit.getServer().getPluginManager().callEvent(new SignChangeEvent(b, p, new String[] {line1, line2, line3, Double.toString(ConfigShop.getSellPrice(mat, amount))}));
						return;
					}
					ConfigShop.sellItemToServer(p, is);
				}
			}
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlaceSign(SignChangeEvent e)
	{
		Block b = e.getBlock();
		BlockState bs = b.getState();
		Player p = e.getPlayer();
		if(bs instanceof Sign)
		{
			if(e.getLine(0).contains("[sell]") && p.hasPermission("blobcatraz.sign.create.sell"))
			{
				String[] item = e.getLine(1).split(":");
				Material mat = Material.matchMaterial(item[0]);
				if(mat == null) {p.sendMessage(Util.blobcatraz + item[0] + " §eis not an item!"); return;}
				int amount = 0;
				try{amount = Integer.parseInt(e.getLine(2));} catch(Exception ex) {p.sendMessage(e.getLine(2) + "is not a number"); return;}
				if(amount == 0) return;
				if(amount > 64) {amount = 64; e.setLine(2, Integer.toString(64));}
				double price = ConfigShop.getSellPrice(mat, amount);

				e.setLine(0, "§1[Sell]");
				e.setLine(3, Double.toString(price));
				bs.update();
			}
			if(e.getLine(0).contains("[buy]") && p.hasPermission("blobcatraz.sign.create.buy"))
			{
				String[] item = e.getLine(1).split(":");
				Material mat = Material.matchMaterial(item[0]);
				if(mat == null) {p.sendMessage(Util.blobcatraz + item[0] + " §eis not an item!"); return;}
				int amount = 0;
				try{amount = Integer.parseInt(e.getLine(2));} catch(Exception ex) {p.sendMessage(e.getLine(2) + "is not a number"); return;}
				if(amount == 0) return;
				if(amount > 64) {amount = 64; e.setLine(2, Integer.toString(64));}
				double price = ConfigShop.getBuyPrice(mat, amount);

				e.setLine(0, "§1[Buy]");
				e.setLine(3, Double.toString(price));
				bs.update(true);
			}
		}
	}
}