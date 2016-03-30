package com.SirBlobman.blobcatraz.enchants;

import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class FireballEnchant implements Listener
{
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		ItemStack held_item = p.getItemInHand();
		Action a = e.getAction();
		
		Location loc = p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
		
		
		if(a.equals(Action.RIGHT_CLICK_AIR) && held_item.getItemMeta().getLore().contains("§7Fireball I"))
		{
			p.getWorld().spawn(loc, SmallFireball.class);
		}
		if(a.equals(Action.RIGHT_CLICK_AIR) && held_item.getItemMeta().getLore().contains("§7Fireball II"))
		{
			p.getWorld().spawn(loc, Fireball.class);
		}
		if(a.equals(Action.RIGHT_CLICK_AIR) && held_item.getItemMeta().getLore().contains("§7Fireball III"))
		{
			p.getWorld().spawn(loc, Fireball.class);
			p.getWorld().spawn(loc, Fireball.class);
			p.getWorld().spawn(loc, Fireball.class);
			p.getWorld().spawn(loc, Fireball.class);
		}
	}
}