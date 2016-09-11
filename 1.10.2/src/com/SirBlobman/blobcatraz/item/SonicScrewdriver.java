package com.SirBlobman.blobcatraz.item;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

public class SonicScrewdriver implements Listener
{
	@EventHandler
	public void sonic(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		ItemStack held = e.getItem();
		if(ItemUtil.isAir(held)) return;
		ItemStack sonic = BItems.sonicScrewdriver();
		Action a = e.getAction();
		Action use = Action.RIGHT_CLICK_BLOCK;
		if(a.equals(use) && held.equals(sonic))
		{
			String permission = "blobcatraz.sonic.use";
			if(!PlayerUtil.hasPermission(p, permission)) return;
			
			Block b = e.getClickedBlock();
			BlockState bs = b.getState();
			MaterialData data = bs.getData();
			Block ab = b.getRelative(BlockFace.UP);
			//Block bb = b.getRelative(BlockFace.DOWN);
			Material mat = b.getType();
			Location l = b.getLocation();
			World w = l.getWorld();
			if(data instanceof Openable)
			{
				Openable o = (Openable) data;
				boolean open = o.isOpen();
				o.setOpen(!open);
				bs.setData((MaterialData) o);
				bs.update();
				Util.playSonic(p);
			}
			if(mat == Material.TNT)
			{
				b.setType(Material.AIR);
				p.playSound(l, Sound.ENTITY_TNT_PRIMED, 1000, 1);
				w.spawn(l, TNTPrimed.class);
				Util.playSonic(p);
			}
			if(mat == Material.LADDER)
			{
				b.breakNaturally(held);
				Util.playSonic(p);
			}
			if(ItemUtil.isGlass(b))
			{
				ItemStack glass = data.toItemStack();
				b.breakNaturally();
				PlayerInventory pi = p.getInventory();
				pi.addItem(glass);
				Util.playSonic(p);
			}
			if(mat == Material.OBSIDIAN && ab.getType() == Material.AIR)
			{
				ab.setType(Material.FIRE);
				Util.playSonic(p);
			}
			if(mat == Material.WEB)
			{
				b.breakNaturally();
				Util.playSonic(p);
			}
		}
	}
}