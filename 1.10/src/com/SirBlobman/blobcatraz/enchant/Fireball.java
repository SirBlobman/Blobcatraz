package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Fireball implements Listener 
{
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		Action a = e.getAction();
		World w = p.getWorld();
		Location l = p.getLocation();
		Location loc = p.getEyeLocation().toVector().add(l.getDirection().multiply(2)).toLocation(w, l.getYaw(), l.getPitch());
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		if(held == null) held = pi.getItemInOffHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		
		Class<SmallFireball> sf = SmallFireball.class;
		Class<org.bukkit.entity.Fireball> f = org.bukkit.entity.Fireball.class;
		if(a.equals(Action.RIGHT_CLICK_AIR))
		{
			if(lore.contains("§7Fireball I")) w.spawn(loc, sf);
			if(lore.contains("§7Fireball II")) w.spawn(loc, f);
			if(lore.contains("§7Fireball III"))
			{
				w.spawn(loc, f);
				w.spawn(loc, f);
				w.spawn(loc, f);
				w.spawn(loc, f);
			}
		}
	}
}