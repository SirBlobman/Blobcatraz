package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.Util;

public class Cure implements Listener 
{
	static String fail = Util.blobcatraz + "§4Failed to cure the Zombie";
	static String success = Util.blobcatraz + "Cure the Villager!";
	
	@EventHandler
	public static void playerHitZombieVillager(EntityDamageByEntityEvent e)
	{
		if(e.getEntityType() != EntityType.ZOMBIE) return;
		if(e.getDamager().getType() != EntityType.PLAYER) return;
		
		Player p = (Player) e.getDamager();
		
		ItemStack held = p.getItemInHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		Zombie z = (Zombie) e.getEntity();
		
		Location zombieLocation = z.getLocation();
		World zombieWorld = zombieLocation.getWorld();
		
		double chance = Math.random();
		
		if(z.isVillager())
		{
			if(lore.contains("§7Cure I"))
			{
				if(chance >= .8)
				{
					z.setHealth(0.0D);
					zombieWorld.spawn(zombieLocation, Villager.class);
					p.sendMessage(success);
				}
				else
				{
					p.sendMessage(fail);
				}
			}
			if(lore.contains("§7Cure II"))
			{
				if (chance >= .5) 
				{
					z.setHealth(0.0D);
					zombieWorld.spawn(zombieLocation, Villager.class);
					p.sendMessage(success);
				}	 
				else 
				{
					p.sendMessage(fail);
				}
			}
			if(lore.contains("§7Cure II"))
			{
				z.setHealth(0.0D);
				zombieWorld.spawn(zombieLocation, Villager.class);
				p.sendMessage(success);
			}
		}
	}
}