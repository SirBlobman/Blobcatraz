package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class Protection implements Listener 
{
	@EventHandler
	public void breakBlock(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Material m = b.getType();
		String mat = m.name();
		String permission = "blobcatraz.break." + mat;
		
		boolean can = PlayerUtil.hasPermission(p, permission);
		if(!can) {e.setCancelled(true); Util.broadcast("test");}
	}
	
	@EventHandler
	public void placeBlock(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		Block b = e.getBlockPlaced();
		Material m = b.getType();
		String mat = m.name();
		String permission = "blobcatraz.place." + mat;
		
		boolean can = PlayerUtil.hasPermission(p, permission);
		if(!can) {e.setCancelled(true); Util.broadcast("test");}
	}
}