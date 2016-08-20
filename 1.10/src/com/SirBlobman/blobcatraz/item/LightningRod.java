package com.SirBlobman.blobcatraz.item;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.Util;

public class LightningRod implements Listener
{
	@EventHandler
	public void onUseLightningRod(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		PlayerInventory pi = p.getInventory();
		World w = p.getWorld();
		ItemStack lrod = Items.lightningRod();
		if(!pi.getItemInMainHand().equals(lrod) && !pi.getItemInOffHand().equals(lrod)) return;
		
		Location strike = Util.getPlayerLook(p);
		w.strikeLightningEffect(strike);
	}
}