package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class Cure implements Listener 
{
	String fail = "§1[§6Blobcatraz§1]§r §4Failed to cure the Zombie";
	String success = "§1[§6Blobcatraz§1]§r Cured the Villager!";
	
	@EventHandler
	public void playerHitZombieVillager(EntityDamageByEntityEvent e) 
	{
		Entity ded = e.getEntity();
		if (e.getDamager().getType() != EntityType.PLAYER) 
		{
			return;
		}

		Player der = (Player) e.getDamager();

		ItemStack held_item = der.getItemInHand();
		if (held_item == null) 
		{
			return;
		}
		ItemMeta meta = held_item.getItemMeta();
		if (meta == null) 
		{
			return;
		}
		List<String> lore = meta.getLore();
		if (lore == null) 
		{
			return;
		}

		if (ded.getType() == EntityType.ZOMBIE) 
		{
			Zombie z = (Zombie) e.getEntity();
			if (e.getDamager().getType() != EntityType.PLAYER) 
			{
				return;
			}
			
			Location l = z.getLocation();
			World w = z.getWorld();

			double chance = Math.random();
			
			if (lore.contains("§7Cure I") && z.isVillager()) 
			{
				if (chance >= .8) 
				{
					z.setHealth(0);
					w.spawn(l, Villager.class);
					der.sendMessage(success);
				}	 
				else 
				{
					der.sendMessage(fail);
				}
			}
			
			if (lore.contains("§7Cure II") && z.isVillager()) 
			{
				if (chance >= .5) 
				{
					z.setHealth(0);
					w.spawn(l, Villager.class);
					der.sendMessage(success);
				}	 
				else 
				{
					der.sendMessage(fail);
				}
			}
			
			if (lore.contains("§7Cure III") && z.isVillager()) 
			{
				z.setHealth(0);
				w.spawn(l, Villager.class);
				der.sendMessage(success);
			}
		}
	}
}