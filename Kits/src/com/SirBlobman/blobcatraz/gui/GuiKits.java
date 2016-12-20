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

import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.config.Kit;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class GuiKits implements Listener
{
	private static final String TITLE1 = Util.option("gui.kits.main");
	private static final String TITLE2 = Util.option("gui.kits.preview");
	private static final int KITS = 26;
	private static final int SIZE = 45;
	private static int page;
	
	public Inventory gui(int pg)
	{
		if(pg < 1)
		{
			String error = Util.option("error.kits.invalid page");
			IllegalArgumentException IAE = new IllegalArgumentException(error);
			throw IAE;
		}
		
		int start = ((pg * 27) - 27);
		int end = start + KITS;
		page = pg;
		
		Inventory in = Bukkit.createInventory(null, SIZE, TITLE1);
		int item = 0;
		List<Kit> list = ConfigKits.kits();
		for(int i = start; i < list.size(); i++)
		{
			if(i > end) break;
			if(list.size() < i) break;
			
			Kit kit = list.get(i);
			String name = kit.name();
			ItemStack is = kit.icon();
			ItemUtil.rename(is, "&2" + name);
			in.setItem(item, is);
			item++;
		}
		
		if(page > 1) in.setItem(36, back());
		in.setItem(40, page(pg));
		in.setItem(44, next());
		return in;
	}
	
	public Inventory preview(List<ItemStack> kit)
	{
		Inventory in = Bukkit.createInventory(null, 54, TITLE2);
		ItemStack[] items = new ItemStack[kit.size()];
		items = kit.toArray(items);
		in.setContents(items);
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
		ItemStack pg = new ItemStack(Material.PAPER);
		ItemUtil.rename(pg, "&9Page: &r" + page);
		pg.setAmount(page);
		return pg;
	}
	
	@EventHandler
	public void kit(InventoryClickEvent e)
	{
		HumanEntity he = e.getWhoClicked();
		Inventory i = e.getInventory();
		if(i == null) return;
		String title = i.getTitle();
		if(title == null) return;
		if(title.equals(TITLE1))
		{
			ItemStack is = e.getCurrentItem();
			if(is == null) return;
			String name = ItemUtil.name(is);
			String un = ItemUtil.uncolor(name);
			if(name == null) return;
			if(ConfigKits.exists(un))
			{
				List<ItemStack> kit = ConfigKits.kit(un);
				Inventory prev = preview(kit);
				he.openInventory(prev);
			}
			
			if(he instanceof Player)
			{
				Player p = (Player) he;
				if(is.equals(next()))
				{
					he.closeInventory();
					int next = page + 1;
					p.performCommand("kits " + next);
					return;
				}
				
				if(is.equals(back()))
				{
					p.closeInventory();
					int back = page - 1;
					p.performCommand("kits " + back);
					return;
				}
			}
		}
		if(title.equals(TITLE2)) e.setCancelled(true);
	}
}