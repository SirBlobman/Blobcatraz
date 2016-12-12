package com.SirBlobman.blobcatraz.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.config.ConfigWarps;
import com.SirBlobman.blobcatraz.config.Warp;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class GuiWarps implements Listener
{
	private static final String TITLE = Util.option("gui.warps");
	private static final int WARPS = 26;
	private static final int SIZE = 45;
	private static int page;
	
	public Inventory warps(int page)
	{
		if(page < 1)
		{
			String error = Util.option("error.warps.invalid page");
			IllegalArgumentException IAE = new IllegalArgumentException(error);
			throw IAE;
		}
		
		int start = ((page * 27) - 27);
		int end = start + WARPS;
		GuiWarps.page = page;
		
		Inventory in = Bukkit.createInventory(null, SIZE, TITLE);
		int item = 0;
		List<Warp> list = ConfigWarps.warps();
		for(int i = start; i < list.size(); i++)
		{
			if(i > end) break;
			if(list.size() < i) break;
			Warp warp = list.get(i);
			String name = warp.name();
			ItemStack icon = warp.icon();
			ItemMeta meta = icon.getItemMeta();
			meta.setDisplayName("§2" + name);
			icon.setItemMeta(meta);
			in.setItem(item, icon);
			item++;
		}
		
		if(page > 1) in.setItem(36, back());
		in.setItem(40, page(page));
		in.setItem(44, next());
		return in;
	}
	
	private final ItemStack next()
	{
		ItemStack next = new ItemStack(Material.FEATHER);
		ItemUtil.rename(next, "&fNext");
		return next;
	}
	
	private final ItemStack back()
	{
		ItemStack back = new ItemStack(Material.ARROW);
		ItemUtil.rename(back, "&fBack");
		return back;
	}
	
	private final ItemStack page(int p)
	{
		ItemStack page = new ItemStack(Material.PAPER);
		ItemUtil.rename(page, "&9Page: &r" + GuiWarps.page);
		page.setAmount(GuiWarps.page);
		return page;
	}
	
	@EventHandler
	public void warp(InventoryClickEvent e)
	{
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player)
		{
			Player p = (Player) he;
			Inventory i = e.getInventory();
			String t = i.getTitle();
			if(t.contains(TITLE))
			{
				e.setCancelled(true);
				ItemStack is = e.getCurrentItem();
				String name = ItemUtil.name(is);
				String un = Util.uncolor(name);
				if(is == null) return;
				if(is.equals(next()))
				{
					p.closeInventory();
					int next = page + 1;
					p.performCommand("warps " + next);
					return;
				}
				if(is.equals(back()))
				{
					p.closeInventory();
					int back = page - 1;
					p.performCommand("warps " + back);
					return;
				}
				if(ConfigWarps.exists(un))
				{
					String perm = "blobcatraz.warps." + un;
					if(!PlayerUtil.hasPermission(p, perm))
					{
						p.closeInventory();
						return;
					}
					
					p.performCommand("warp " + un);
				}
			}
		}
	}
}