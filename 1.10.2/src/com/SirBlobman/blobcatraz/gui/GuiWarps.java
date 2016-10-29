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
	private final String TITLE = Util.message("gui.warps");
	
	private final int warpSize = 26;
	private final int invSize = 45;
	
	static int page;
	
	public Inventory warps(int page)
	{
		if(page < 1) throw new IllegalArgumentException("Page Number must be greater than 1!");
		int start = (page * 27) - 27;
		final int end = start + warpSize;
		GuiWarps.page = page;
		Inventory i = Bukkit.createInventory(null, invSize, TITLE);
		int item = 0;
		List<Warp> warps = ConfigWarps.getWarps();
		for(int in = start; in < warps.size(); in++)
		{
			if(in > end) break;
			if(warps.size() < in) break;
			Warp warp = warps.get(in);
			String name = warp.getName();
			ItemStack icon = warp.getItem();
			ItemMeta meta = icon.getItemMeta();
			meta.setDisplayName("§2" + name);
			icon.setItemMeta(meta);
			i.setItem(item, icon);
			item++;
		}
		
		if(page > 1) i.setItem(36, back());
		i.setItem(40, page(page));
		i.setItem(44, next());
		return i;
	}
	
	@EventHandler
	public void warp(InventoryClickEvent e)
	{
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player)
		{
			Inventory i = e.getInventory();
			String title = i.getTitle();
			if(title.contains(TITLE))
			{
				e.setCancelled(true);
				Player p = (Player) he;
				ItemStack is = e.getCurrentItem();
				String display = ItemUtil.getName(is);
				String name = Util.uncolor(display);
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
				if(ConfigWarps.exists(name))
				{
					String perm = "blobcatraz.warps." + name;
					if(!PlayerUtil.hasPermission(p, perm))
					{
						p.closeInventory();
						return;
					}
					
					p.performCommand("warp " + name);
				}
			}
		}	
	}
	
	public final ItemStack next()
	{
		ItemStack next = new ItemStack(Material.FEATHER);
		ItemMeta meta = next.getItemMeta();
		meta.setDisplayName("§fNext");
		next.setItemMeta(meta);
		return next;
	}
	
	public final ItemStack back()
	{
		ItemStack next = new ItemStack(Material.ARROW);
		ItemMeta meta = next.getItemMeta();
		meta.setDisplayName("§fBack");
		next.setItemMeta(meta);
		return next;
	}
	
	public final ItemStack page(int page)
	{
		ItemStack paper = new ItemStack(Material.PAPER);
		ItemUtil.rename(paper, "§9Page: §r" + page);
		return paper;
	}
}