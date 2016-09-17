package com.SirBlobman.blobcatraz.item;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WorldGuardUtil;

public class SandWand implements Listener
{
	@EventHandler
	public void sand(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		World w = p.getWorld();
		Action a = e.getAction();
		Action sand = Action.RIGHT_CLICK_BLOCK;
		ItemStack wand = BItems.sandWand();
		ItemStack uwand = BItems.uSandWand();
		ItemStack held = e.getItem();
		if(held == null) return;
		if(a == sand)
		{
			
			if(held.equals(wand))
			{
				Block b = e.getClickedBlock();
				try{if(!WorldGuardUtil.canBreak(p, b)) return;}
				catch(Exception | Error ex) {}
				sand(b);
			}
			if(held.equals(uwand))
			{
				Block b = e.getClickedBlock();
				Location l1 = b.getLocation();
				int x = l1.getBlockX() + 4;
				int z = l1.getBlockZ() + 4;
				Location l2 = new Location(w, x, l1.getY(), z);
				List<Block> blocks = Util.getBlocks(l1, l2);
				for(Block b2 : blocks) 
				{
					try{if(!WorldGuardUtil.canBreak(p, b2)) return;}
					catch(Exception | Error ex) {}
					sand(b2);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void sand(Block b)
	{
		Location l = b.getLocation();
		World w = b.getWorld();
		BlockState bs = b.getState();
		MaterialData md = bs.getData();
		Material mat = md.getItemType();
		byte meta = md.getData();
		
		FallingBlock fb = w.spawnFallingBlock(l, mat, meta);
		Vector up = new Vector(0.0D, 1.5D, 0.0D);
		fb.setVelocity(up);
		b.setType(Material.AIR);
	}
}