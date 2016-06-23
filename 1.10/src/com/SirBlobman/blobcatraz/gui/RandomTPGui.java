package com.SirBlobman.blobcatraz.gui;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

@SuppressWarnings({"deprecation", "unused"})
public class RandomTPGui implements Listener
{
	public static void open(Player p)
	{
		ItemStack sFar = new ItemStack(Material.ENDER_PORTAL_FRAME);
		ItemStack sNormal = new ItemStack(Material.EYE_OF_ENDER);
		ItemStack sTiny = new ItemStack(Material.ENDER_PEARL);
		ItemMeta mFar = sFar.getItemMeta();
		ItemMeta mNormal = sNormal.getItemMeta();
		ItemMeta mTiny = sTiny.getItemMeta();
		
		mFar.setDisplayName("§1Far§r");
		mNormal.setDisplayName("§2Normal§r");
		mTiny.setDisplayName("§3Tiny§r");

		sFar.setItemMeta(mFar);
		sNormal.setItemMeta(mNormal);
		sTiny.setItemMeta(mTiny);
		
		
		Inventory TP = Bukkit.createInventory(null, 9, "Select the TP distance");
		TP.setItem(0, sTiny);
		TP.setItem(4, sNormal);
		TP.setItem(8, sFar);
		
		p.openInventory(TP);
	}
	
	/**
	 * @param e
	 */
	@EventHandler
	public void click(InventoryClickEvent e)
	{
		final Player p = (Player)e.getWhoClicked();
		ItemStack clicked = e.getCurrentItem();
		Inventory in = e.getInventory();
		try
		{
			if(in.getName().equals("Select the TP distance"))
			{
				if(clicked.getType() == Material.ENDER_PORTAL_FRAME)
				{
					p.setFallDistance(-100.0F);
					Location ol = p.getLocation();
					Random r = new Random();
					
					int x = r.nextInt(Blobcatraz.instance.getConfig().getInt("randomtp.maxFarDistance"));
					int y = 110;
					int z = r.nextInt(Blobcatraz.instance.getConfig().getInt("randomtp.maxFarDistance"));
					
					Location tl = new Location(p.getWorld(), x, y , z);
					tl.getWorld().refreshChunk(tl.getChunk().getX(), tl.getChunk().getZ());
					p.teleport(tl);
					p.setFallDistance(0.0F);
					p.sendMessage(Util.blobcatraz + "§rYou were teleported to §5" + x + "§r,§5" + y + "§r,§5" + z + "§r!");
					e.setCancelled(true);
					p.closeInventory();
				}
				if(clicked.getType() == Material.EYE_OF_ENDER)
				{
					p.setFallDistance(-100.0F);
					Location ol = p.getLocation();
					Random r = new Random();
					
					int x = r.nextInt(Blobcatraz.instance.getConfig().getInt("randomtp.maxNormalDistance"));
					int y = 110;
					int z = r.nextInt(Blobcatraz.instance.getConfig().getInt("randomtp.maxNormalDistance"));
					
					Location tl = new Location(p.getWorld(), x, y , z);
					tl.getWorld().refreshChunk(tl.getChunk().getX(), tl.getChunk().getZ());
					p.teleport(tl);
					p.setFallDistance(0.0F);
					p.sendMessage(Util.blobcatraz + "§rYou were teleported to §5" + x + "§r,§5" + y + "§r,§5" + z + "§r!");
					e.setCancelled(true);
					p.closeInventory();
				}
				if(clicked.getType() == Material.ENDER_PEARL)
				{
					p.setFallDistance(-100.0F);
					Location ol = p.getLocation();
					Random r = new Random();
					
					int x = r.nextInt(Blobcatraz.instance.getConfig().getInt("randomtp.maxTinyDistance"));
					int y = 110;
					int z = r.nextInt(Blobcatraz.instance.getConfig().getInt("randomtp.maxTinyDistance"));
					
					Location tl = new Location(p.getWorld(), x, y , z);
					tl.getWorld().refreshChunk(tl.getChunk().getX(), tl.getChunk().getZ());
					p.teleport(tl);
					p.setFallDistance(0.0F);
					p.sendMessage(Util.blobcatraz + "§rYou were teleported to §5" + x + "§r,§5" + y + "§r,§5" + z + "§r!");
					e.setCancelled(true);
					p.closeInventory();
				}
			}
		}
		catch (Exception ex) {}
	}
}