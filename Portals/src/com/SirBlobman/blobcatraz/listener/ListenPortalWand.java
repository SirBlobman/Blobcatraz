package com.SirBlobman.blobcatraz.listener;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.item.BItems;
import com.SirBlobman.blobcatraz.utility.Util;
import com.google.common.collect.Maps;

public class ListenPortalWand implements Listener
{
	private static Map<UUID, Location> p1 = Maps.newHashMap();
	private static Map<UUID, Location> p2 = Maps.newHashMap();
	
	@EventHandler
	public void wand(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		ItemStack wand = BItems.portalWand();
		ItemStack held = e.getItem();
		if(held == null) return;
		if(held.equals(wand))
		{
			Action a = e.getAction();
			Action a1 = Action.LEFT_CLICK_BLOCK;
			Action a2 = Action.RIGHT_CLICK_BLOCK;
			if(a.equals(a1))
			{
				Block b = e.getClickedBlock();
				Location l = b.getLocation();
				p1.put(uuid, l);
				
				int x = l.getBlockX();
				int y = l.getBlockY();
				int z = l.getBlockZ();
				
				String coords = x + " " + y + " " + z;
				String msg = Util.prefix + Util.option("portal wand.position 1.set", coords);
				p.sendMessage(msg);
			}
			if(a.equals(a2))
			{
				Block b = e.getClickedBlock();
				Location l = b.getLocation();
				p2.put(uuid, l);
				
				int x = l.getBlockX();
				int y = l.getBlockY();
				int z = l.getBlockZ();
				
				String coords = x + " " + y + " " + z;
				String msg = Util.prefix + Util.option("portal wand.position 2.set", coords);
				p.sendMessage(msg);
			}
			e.setCancelled(true);
		}
	}
	
	public static Location p1(UUID uuid)
	{
		boolean b1 = p1.containsKey(uuid);
		if(b1) return p1.get(uuid);
		return null;
	}
	
	public static Location p2(UUID uuid)
	{
		boolean b2 = p2.containsKey(uuid);
		if(b2) return p2.get(uuid);
		return null;
	}
}