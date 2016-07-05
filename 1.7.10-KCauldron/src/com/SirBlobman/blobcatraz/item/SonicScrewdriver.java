package com.SirBlobman.blobcatraz.item;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.Util;

public class SonicScrewdriver implements Listener 
{
	public static ItemStack sonicScrewdriver()
	{
		ItemStack sonic = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = sonic.getItemMeta();
		
		meta.setDisplayName("§fSonic Screwdriver");
		
		sonic.setItemMeta(meta);
		sonic.setAmount(1);
		
		return sonic;
	}
	
	@SuppressWarnings({"deprecation", "unused"})
	@EventHandler
	public void onSonicUse(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		ItemStack sonic = sonicScrewdriver();
		Action a = e.getAction();
		
		if(!p.hasPermission("blobcatraz.sonic.use")) return;
		if(!p.getItemInHand().equals(sonic)) return;
		
		if(a.equals(Action.RIGHT_CLICK_BLOCK))
		{
			Block b = e.getClickedBlock();
			Block above = b.getRelative(BlockFace.UP, 1);
			Location l = p.getLocation();
			
			//Sonic devices can open Iron Doors
			if(b.getType() == Material.IRON_DOOR_BLOCK)
			{
				if(b.getData() >= 8) b = b.getRelative(BlockFace.DOWN);
				if(b.getData() < 4)
				{
					b.setData((byte) (b.getData() + 4));
					b.getWorld().playEffect(b.getLocation(), Effect.DOOR_TOGGLE, 0);
				}
				else
				{
					b.setData((byte) (b.getData() - 4));
					b.getWorld().playEffect(b.getLocation(), Effect.DOOR_TOGGLE, 0);
				}
				
				Util.playSonicSound(p);
			}
			
			//They can also turn off lamps
			if(b.getType() == Material.REDSTONE_LAMP_ON)
			{
				b.setType(Material.REDSTONE_LAMP_OFF);
				Util.playSonicSound(p);
			}
			
			//They can also make TNT Explode
			if(b.getType() == Material.TNT)
			{
				b.setType(Material.AIR);
				p.playSound(l, Sound.FUSE, 1000, 1);
				TNTPrimed tnt = b.getWorld().spawn(new Location(b.getWorld(), b.getX() + 0.5, b.getY() + 0.5, b.getZ() + 0.5), TNTPrimed.class);
				Util.playSonicSound(p);
			}
			
			//In one episode, it broke a ladder
			if(b.getType() == Material.LADDER)
			{
				b.breakNaturally();
				Util.playSonicSound(p);
			}
			
			//It can also break glass
			if(b.getType() == Material.GLASS || b.getType() == Material.THIN_GLASS || b.getType() == Material.STAINED_GLASS || b.getType() == Material.STAINED_GLASS_PANE)
			{
				ItemStack type = new ItemStack(b.getType());
				type.setDurability(b.getData());
				Util.giveItem(p, type);
				Util.playSonicSound(p);
			}
			
			//It can attempt to light a nether portal
			if(b.getType() == Material.OBSIDIAN && above.getType() == Material.AIR)
			{
				above.setType(Material.FIRE);
				Util.playSonicSound(p);
			}
			
			//Break webs instantly
			if(b.getType() == Material.WEB)
			{
				b.breakNaturally();
				Util.playSonicSound(p);
			}
		}
	}
}