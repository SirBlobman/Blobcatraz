package com.SirBlobman.blobcatraz.gui;

import com.SirBlobman.blobcatraz.utility.TeleportUtil.Teleport;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.TeleportUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiRandomTP implements Listener
{
	private final String TITLE = Util.message("gui.randomtp");
	
	private final ItemStack AIR = new ItemStack(Material.AIR);
	private final ItemStack TINY = RandomTPItems.tp(Teleport.TINY, Material.ENDER_PEARL);
	private final ItemStack NORMAL = RandomTPItems.tp(Teleport.NORMAL, Material.EYE_OF_ENDER);
	private final ItemStack FAR = RandomTPItems.tp(Teleport.FAR, Material.ENDER_PORTAL_FRAME);
	
	public Inventory randomtp()
	{
		ItemStack[] tp = new ItemStack[]
		{
			TINY, AIR, AIR, AIR, NORMAL, AIR, AIR, AIR, FAR//1st Row
		};
		Inventory TP = Bukkit.createInventory(null, tp.length, TITLE);
		TP.setContents(tp);
		return TP;
	}
	
	@EventHandler
	public void click(InventoryClickEvent e)
	{
		HumanEntity he = e.getWhoClicked();
		if(!(he instanceof Player)) return;
		Player p = (Player) he;
		ItemStack clicked = e.getCurrentItem();
		if(ItemUtil.isAir(clicked)) return;
		Inventory i = e.getInventory();
		try
		{
			if(i.getName().equals(TITLE))
			{
				e.setCancelled(true);
				if(clicked.equals(TINY))
				{
					TeleportUtil.randomTP(p, Teleport.TINY);
					p.closeInventory();
				}
				if(clicked.equals(NORMAL))
				{
					TeleportUtil.randomTP(p, Teleport.NORMAL);
					p.closeInventory();
				}
				if(clicked.equals(FAR))
				{
					TeleportUtil.randomTP(p, Teleport.FAR);
					p.closeInventory();
				}
			}
		} catch(Exception ex) {}
	}
}