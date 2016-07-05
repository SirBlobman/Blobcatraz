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
		Block b = e.getBlock();
		Material m = b.getType();
		Player p = e.getPlayer();
		
		if(m == Material.IRON_FENCE)
		{
			if(!p.hasPermission("blobcatraz.break.IRON_FENCE") == false)
			{
				e.setCancelled(true);
				p.sendMessage(Util.blobcatraz + "§cI'm sorry, but you are not allowed to break IRON_FENCE. Someone might escape");
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		Block b = e.getBlock();
		Material m = b.getType();
		Player p = e.getPlayer();
		
		if(m == Material.IRON_FENCE)
		{
			if(!p.hasPermission("blobcatraz.place.IRON_FENCE"))
			{
				e.setCancelled(true);
				p.sendMessage(Util.blobcatraz + "§cI'm sorry, but you can't place IRON_FENCE. Someone might get trapped");
			}
		}
		
		if(m == Material.PISTON_BASE || m == Material.PISTON_STICKY_BASE)
		{
			if(!p.hasPermission("blobcatraz.place.PISTON"))
			{
				e.setCancelled(true);
				p.sendMessage(Util.blobcatraz + "§cI'm sorry, but you can't place PISTON. Someone might escape");
			}
		}
	}
}