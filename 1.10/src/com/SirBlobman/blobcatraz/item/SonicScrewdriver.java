package com.SirBlobman.blobcatraz.item;

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
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import com.SirBlobman.blobcatraz.Util;

public class SonicScrewdriver implements Listener
{
	@SuppressWarnings({"deprecation", "unused"})
	@EventHandler
	public void onSonicUse(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		PlayerInventory pi = p.getInventory();
		Location l = p.getLocation();
		Action a = e.getAction();
		ItemStack sonic = Items.sonicScrewdriver();
		
		if(!p.hasPermission("blobcatraz.sonic.use")) return;
		if(!pi.getItemInMainHand().equals(sonic) && !pi.getItemInOffHand().equals(sonic)) return;
		
		if(a.equals(Action.RIGHT_CLICK_BLOCK))
		{
			Block b = e.getClickedBlock();
			World bw = b.getWorld();
			Location bl = b.getLocation();
			Material mat = b.getType();
			Block above = b.getRelative(BlockFace.UP);
			Block below = b.getRelative(BlockFace.DOWN);
			
			if(mat == Material.IRON_DOOR_BLOCK)
			{
				BlockState bs = b.getState();
			    if (((bs.getData() instanceof Door)) 
			    		&& (((Door)bs.getData()).isTopHalf())) bs = below.getState();
			    Openable d = (Openable)bs.getData();
			    d.setOpen(!d.isOpen());
			    bs.setData((MaterialData)d);
			    bs.update();
			    Util.playSonicSound(p);
			    p.playSound(bl, Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
			}
			if(mat == Material.IRON_TRAPDOOR)
			{
				BlockState bs = b.getState();
			    Openable d = (Openable)bs.getData();
			    d.setOpen(!d.isOpen());
			    bs.setData((MaterialData)d);
			    bs.update();
			    Util.playSonicSound(p);
			    p.playSound(bl, Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1, 1);
			}
			if(mat == Material.TNT)
			{
				b.setType(Material.AIR);
				p.playSound(l, Sound.ENTITY_TNT_PRIMED, 1000, 1);
				TNTPrimed tnt = b.getWorld().spawn(b.getLocation(), TNTPrimed.class);
				Util.playSonicSound(p);
			}
			if(mat == Material.LADDER)
			{
				b.breakNaturally();
				Util.playSonicSound(p);
			}
			if(mat == Material.GLASS || mat == Material.THIN_GLASS || mat == Material.STAINED_GLASS || mat == Material.STAINED_GLASS_PANE)
			{
				ItemStack glass = new ItemStack(mat);
				glass.setDurability(b.getData());
				b.breakNaturally();
				pi.addItem(glass);
				Util.playSonicSound(p);
			}
			if(mat == Material.OBSIDIAN && above.getType() == Material.AIR)
			{
				above.setType(Material.FIRE);
				Util.playSonicSound(p);
			}
			if(mat == Material.WEB)
			{
				b.breakNaturally();
				Util.playSonicSound(p);
			}
		}
	}
}