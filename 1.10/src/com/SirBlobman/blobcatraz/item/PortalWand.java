package com.SirBlobman.blobcatraz.item;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.Util;

public class PortalWand implements Listener
{
	public static HashMap<UUID, Location> pos1 = new HashMap<UUID, Location>();
	public static HashMap<UUID, Location> pos2 = new HashMap<UUID, Location>();
	
	@EventHandler
	public void onUsePortalWand(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		PlayerInventory pi = p.getInventory();
		Action a  = e.getAction();
		ItemStack wand = Items.portalWand();
		
		if(!p.hasPermission("blobcatraz.wand.use") && !p.isOp()) return;
		if(!pi.getItemInMainHand().equals(wand)) return;
		
		if(a.equals(Action.LEFT_CLICK_BLOCK))
		{
			Block b = e.getClickedBlock();
			Location bl = b.getLocation();
			
			pos1.put(uuid, bl);
			
			int x1 = bl.getBlockX();
			int y1 = bl.getBlockY();
			int z1 = bl.getBlockZ();
			String coords1 = x1 + " " + y1 + " " + z1;
			p.sendMessage(Util.blobcatraz + "Position 1 set to §5" + coords1 + "§r!");
		}
		if(a.equals(Action.RIGHT_CLICK_BLOCK))
		{
			Block b = e.getClickedBlock();
			Location bl = b.getLocation();
			
			pos2.put(uuid, bl);
			
			int x2 = bl.getBlockX();
			int y2 = bl.getBlockY();
			int z2 = bl.getBlockZ();
			String coords2 = x2 + " " + y2 + " " + z2;
			
			p.sendMessage(Util.blobcatraz + "Position 2 set to §5" + coords2 + "§r!");
		}
		
		e.setCancelled(true);
	}
}