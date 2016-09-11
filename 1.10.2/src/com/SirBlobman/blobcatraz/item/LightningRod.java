package com.SirBlobman.blobcatraz.item;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LightningRod implements Listener
{
	@EventHandler
	public void strike(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		World w = p.getWorld();
		ItemStack rod = BItems.lightningRod();
		ItemStack held = ItemUtil.getHeld(p);
		if(held.equals(rod))
		{
			Location l = PlayerUtil.getPlayerLookLocation(p);
			w.strikeLightningEffect(l);
		}
	}
}