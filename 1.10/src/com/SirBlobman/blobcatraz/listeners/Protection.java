package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.SirBlobman.blobcatraz.Util;

public class Protection implements Listener 
{
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Material m = b.getType();
		
		if(m == Material.IRON_FENCE)
		{
			if(!p.hasPermission("blobcatraz.break." + m.toString()))
			{
				e.setCancelled(true);
				p.sendMessage(Util.blobcatraz + "§cYou are not allowed to break " + m.toString() + ". Someone might escape!");
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Material m = b.getType();
		
		if(m == Material.IRON_FENCE)
		{
			if(!p.hasPermission("blobcatraz.place." + m.toString()))
			{
				e.setCancelled(true);
				p.sendMessage("§cYou are not allowed to place " + m.toString() + ". Someone might get trapped!");
			}
		}
		
		if(m == Material.PISTON_BASE || m == Material.PISTON_STICKY_BASE)
		{
			if(!p.hasPermission("blobcatraz.place." + m.toString()))
			{
				e.setCancelled(true);
				p.sendMessage("§cYou are not allowed to place " + m.toString() + ". Someone might escape!");
			}
		}
	}
}