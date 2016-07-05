package com.SirBlobman.blobcatraz.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.Util;

public class GuiRandomTP implements Listener
{
	public void open(Player p)
	{
		ItemStack tiny = new ItemStack(Material.ENDER_PEARL);
		ItemStack normal = new ItemStack(Material.EYE_OF_ENDER);
		ItemStack far = new ItemStack(Material.ENDER_PORTAL_FRAME);
		ItemMeta mTiny = tiny.getItemMeta();
		ItemMeta mNormal = normal.getItemMeta();
		ItemMeta mFar = far.getItemMeta();
		
		mTiny.setDisplayName("§1Tiny§r");
		mNormal.setDisplayName("§2Normal§r");
		mFar.setDisplayName("§3Far§r");
		
		tiny.setItemMeta(mTiny);
		normal.setItemMeta(mNormal);
		far.setItemMeta(mFar);
		
		Inventory TP = Bukkit.createInventory(null, 9, "Select the TP distance");
		TP.setItem(0, tiny);
		TP.setItem(4, normal);
		TP.setItem(8, far);
		
		p.openInventory(TP);
	}
	
	@EventHandler
	public void click(InventoryClickEvent e)
	{
		final Player p = (Player) e.getWhoClicked();
		ItemStack clicked = e.getCurrentItem();
		if(clicked == null) return;
		Material m = clicked.getType();
		if(m == null) return;
		Inventory in = e.getInventory();
		
		try
		{
			if(in.getName().equals("Select the TP distance"))
			{
				if(m == Material.ENDER_PEARL)
				{
					Util.tinyRandomTP(p);
					e.setCancelled(true);
					p.closeInventory();
				}
				if(m == Material.EYE_OF_ENDER)
				{
					Util.normalRandomTP(p);
					e.setCancelled(true);
					p.closeInventory();
				}
				if(m == Material.ENDER_PORTAL_FRAME)
				{
					Util.farRandomTP(p);
					e.setCancelled(true);
					p.closeInventory();
				}
			}
		}
		catch (Exception ex) {};
	}
}