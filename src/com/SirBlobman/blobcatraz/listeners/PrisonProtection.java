package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PrisonProtection implements Listener
{
	@EventHandler 
	public void onBlockBreakEvent(BlockBreakEvent e)
	{
		if(e.getBlock().getType() == Material.IRON_FENCE)
		{
			if(e.getPlayer().hasPermission("blobcatraz.break.iron_fence") == false)
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage("§1[§6Blobcatraz§1]§r §cI'm sorry, but you are not allowed to break this block. Someone might escape from prison!");
			}
		}
	}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent e)
	{
		if(e.getBlock().getType() == Material.IRON_FENCE)
		{
			if(e.getPlayer().hasPermission("blobcatraz.place.iron_fence") == false)
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage("§1[§6Blobcatraz§1]§r §cI'm sorry, but you are not allowed to create prison cells");
				e.getPlayer().sendMessage("Instead, you can give this block to an admin, and he will give you $100 for each cell bar");
			}
		}
		
		if(e.getBlock().getType() == Material.PISTON_BASE || e.getBlock().getType() == Material.PISTON_STICKY_BASE)
		{
			if(e.getPlayer().hasPermission("blobcatraz.place.piston") == false)
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage("§1[§6Blobcatraz§1]§r &cPistons help you escape, so we blocked them!");
			}
		}
	}
}
