package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class ListenCobbleOres implements Listener
{
	@EventHandler
	public void gen(BlockFormEvent e)
	{
		Block b = e.getBlock();
		Material mat = b.getType();
		if(mat == Material.LAVA)
		{
			e.setCancelled(true);
			Location l = b.getLocation();
			World w = b.getWorld();
			Block b2 = w.getBlockAt(l);
			Material mat2 = type();
			b2.setType(mat2);
		}
	}
	
	private Material type()
	{
		double chance = Math.random();
		Material mat = Material.COBBLESTONE;
		if(chance < 1.00) mat = Material.COBBLESTONE;
		if(chance < 0.55) mat = Material.COAL_ORE;
		if(chance < 0.50) mat = Material.IRON_ORE;
		if(chance < 0.45) mat = Material.LAPIS_ORE;
		if(chance < 0.30) mat = Material.GOLD_ORE;
		if(chance < 0.30) mat = Material.REDSTONE_ORE;
		if(chance < 0.20) mat = Material.EMERALD_ORE;
		if(chance < 0.10) mat = Material.DIAMOND_ORE;
		return mat;
	}
}