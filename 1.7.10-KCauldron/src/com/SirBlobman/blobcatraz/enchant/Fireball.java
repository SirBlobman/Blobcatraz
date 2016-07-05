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

public class Fireball implements Listener 
{
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		if(p == null) return;
		ItemStack held = p.getItemInHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		Location l = p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
		Action a = e.getAction();
		
		if(a.equals(Action.RIGHT_CLICK_AIR))
		{
			Class<SmallFireball> sfireball = SmallFireball.class;
			Class<org.bukkit.entity.Fireball> fireball = org.bukkit.entity.Fireball.class;
			
			if(lore.contains("§7Fireball I")) p.getWorld().spawn(l, sfireball);
			if(lore.contains("§7Fireball II")) p.getWorld().spawn(l, fireball);
			if(lore.contains("§7Fireball III"))
			{
				p.getWorld().spawn(l, fireball);
				p.getWorld().spawn(l, fireball);
				p.getWorld().spawn(l, fireball);
				p.getWorld().spawn(l, fireball);
			}
		}
	}
}