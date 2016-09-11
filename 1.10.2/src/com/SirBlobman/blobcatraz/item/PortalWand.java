package com.SirBlobman.blobcatraz.item;

import java.util.Map;
import java.util.UUID;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.google.common.collect.Maps;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PortalWand implements Listener
{
	public static Map<UUID, Location> pos1 = Maps.newHashMap();
	public static Map<UUID, Location> pos2 = Maps.newHashMap();
	
	@EventHandler
	public void wand(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		ItemStack wand = BItems.portalWand();
		ItemStack held = ItemUtil.getHeld(p);
		Action a = e.getAction();
		Action p1 = Action.LEFT_CLICK_BLOCK;
		Action p2 = Action.RIGHT_CLICK_BLOCK;
		if(held.equals(wand))
		{
			if(a.equals(p1))
			{
				Block b = e.getClickedBlock();
				Location l = b.getLocation();
				pos1.put(uuid, l);
				
				int x = l.getBlockX();
				int y = l.getBlockY();
				int z = l.getBlockZ();
				String coords = x + " " + y + " " + z;
				p.sendMessage(Util.blobcatraz + "Position 1 set to §5" + coords + "§r!");
			}
			if(a.equals(p2))
			{
				Block b = e.getClickedBlock();
				Location l = b.getLocation();
				pos2.put(uuid, l);
				
				int x = l.getBlockX();
				int y = l.getBlockY();
				int z = l.getBlockZ();
				String coords = x + " " + y + " " + z;
				p.sendMessage(Util.blobcatraz + "Position 2 set to §5" + coords + "§r!");
			}
			e.setCancelled(true);
		}
	}
}