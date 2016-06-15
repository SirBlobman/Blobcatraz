package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class Fireball implements Listener
{
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		ItemStack held_item = p.getItemInHand();
		if(held_item == null)
		{
			return;
		}
		ItemMeta meta = held_item.getItemMeta();
		if(meta == null)
		{
			return;
		}
		List<String> lore = meta.getLore();
		if(lore == null)
		{
			return;
		}
		
		Action a = e.getAction();
		
		Location loc = p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
		
		
		if(a.equals(Action.RIGHT_CLICK_AIR) && lore.contains("§7Fireball I"))
		{
			p.getWorld().spawn(loc, SmallFireball.class);
		}
		if(a.equals(Action.RIGHT_CLICK_AIR) && lore.contains("§7Fireball II"))
		{
			p.getWorld().spawn(loc, org.bukkit.entity.Fireball.class);
		}
		if(a.equals(Action.RIGHT_CLICK_AIR) && lore.contains("§7Fireball III"))
		{
			p.getWorld().spawn(loc, org.bukkit.entity.Fireball.class);
			p.getWorld().spawn(loc, org.bukkit.entity.Fireball.class);
			p.getWorld().spawn(loc, org.bukkit.entity.Fireball.class);
			p.getWorld().spawn(loc, org.bukkit.entity.Fireball.class);
		}
	}
}