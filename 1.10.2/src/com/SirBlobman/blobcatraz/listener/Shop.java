package com.SirBlobman.blobcatraz.listener;

import com.SirBlobman.blobcatraz.config.ConfigShop;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Shop implements Listener
{
	private static final String TITLE = Util.message("gui.sellall");
	public static final Inventory SELL_ALL = Bukkit.createInventory(null, 54, TITLE);
	
	@EventHandler
	public void clickSellBuy(PlayerInteractEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		Action a = e.getAction();
		Action sign = Action.RIGHT_CLICK_BLOCK;
		if(a == sign)
		{
			Block b = e.getClickedBlock();
			BlockState bs = b.getState();
			if(bs instanceof Sign)
			{
				Sign s = (Sign) bs;
				String[] lines = s.getLines();
				String buy = Util.color("&1[Buy]");
				String sell = Util.color("&1[Sell]");
				
				Material mat = Material.AIR;
				short data = 0;
				int amount = 0;
				try
				{
					String[] item = lines[1].split(":");
					if(item.length == 1) data = 0;
					else data = Short.parseShort(item[1]);
					
					mat = Material.matchMaterial(item[0].toUpperCase());
					amount = Integer.parseInt(lines[2]);
				} catch(Exception ex)
				{
					p.sendMessage(Util.blobcatraz + Util.message("shop.invalidSign"));
					return;
				}
				ItemStack is = new ItemStack(mat, amount, data);
				
				if(lines[0].equals(buy)) {ConfigShop.buy(p, is);}
				if(lines[0].equals(sell)) {ConfigShop.sell(p, is);}
			}
		}
	}
	
	@EventHandler
	public void sellBuy(SignChangeEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		String[] lines = e.getLines();
		if(isShopSign(lines))
		{
			String buy = "[buy]", buy2 = Util.color("&1[Buy]");
			String sell = "[sell]", sell2 = Util.color("&1[Sell]");
			ItemStack is = getItem(lines[1]);
			if(is == null) {p.sendMessage(Util.blobcatraz + Util.message("shop.invalidItem", lines[1])); return;}
			int amount = 0;
			try{amount = Integer.parseInt(lines[2]);} catch(Exception ex) {p.sendMessage(Util.blobcatraz + Util.message("shop.invalidAmount", lines[2])); return;}
			is.setAmount(amount);
			if(amount > 64) amount = 64;
			if(amount < 1) amount = 1;
			if(lines[0].equalsIgnoreCase(buy))
			{
				e.setLine(0, buy2);
				e.setLine(1, ItemUtil.getName(is));
				e.setLine(2, "" + amount);
				ItemStack toBuy = new ItemStack(is.getType(), amount, is.getDurability());
				e.setLine(3, "" + ConfigShop.getPrice(toBuy) * 2);
			}
			if(lines[0].equalsIgnoreCase(sell))
			{
				e.setLine(0, sell2);
				e.setLine(1, ItemUtil.getName(is));
				e.setLine(2, "" + amount);
				ItemStack toSell = new ItemStack(is.getType(), amount, is.getDurability());
				e.setLine(3, "" + ConfigShop.getPrice(toSell));
			}
		}
	}
	
	@EventHandler
	public void clickSellAll(PlayerInteractEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		Action a = e.getAction();
		Action sign = Action.RIGHT_CLICK_BLOCK;
		if(a == sign)
		{
			Block b = e.getClickedBlock();
			BlockState bs = b.getState();
			if(bs instanceof Sign)
			{
				Sign s = (Sign) bs;
				String[] lines = s.getLines();
				if(lines.length == 0) return;
				String sellall = Util.message("shop.sign.sellall");
				if(lines[0].equalsIgnoreCase(sellall))
				{
					p.openInventory(SELL_ALL);
				}
			}
		}
	}
	
	@EventHandler
	public void sellAll(SignChangeEvent e)
	{
		
	}
	
	@EventHandler
	public void sellAll(InventoryCloseEvent e)
	{
		HumanEntity he = e.getPlayer();
		if(he instanceof Player)
		{
			Player p = (Player) he;
			Inventory i = e.getInventory();
			if(i.getTitle().equals(TITLE)) ConfigShop.sell(p, i);
		}
	}

	private boolean isShopSign(String[] lines)
	{
		String buy = "[buy]", buy2 = Util.color("&1[Buy]");
		String sell = "[sell]", sell2 = Util.color("&1[Sell]");
		if(lines.length != 4) return false;
		String type = lines[0];
		if(type.equalsIgnoreCase(buy) || type.equalsIgnoreCase(buy2)) return true;
		if(type.equalsIgnoreCase(sell) || type.equalsIgnoreCase(sell2)) return true;
		return false;
	}
	
	private ItemStack getItem(String s)
	{
		Material mat = Material.AIR;
		short meta = 0;
		try
		{
			String[] item = s.split(":");
			mat = Material.matchMaterial(item[0]);
			if(item.length > 1) meta = Short.parseShort(item[1]);
			return new ItemStack(mat, 1, meta);
		} catch(Exception ex) {return null;}
	}
}