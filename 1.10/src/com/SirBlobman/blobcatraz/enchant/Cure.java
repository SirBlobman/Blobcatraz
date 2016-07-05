package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.Util;

public class Cure implements Listener
{
	String success = Util.blobcatraz + "Cured the Villager!";
	String failure = Util.blobcatraz + "§4Failed to cure the Zombie!";
	
	@EventHandler
	public void playerHitZombieVillager(EntityDamageByEntityEvent e)
	{
		
		if(!(e.getDamager() instanceof LivingEntity) || !(e.getEntity() instanceof LivingEntity)) return;
		LivingEntity damaged = (LivingEntity) e.getEntity();
		LivingEntity damager = (LivingEntity) e.getDamager();
		
		EntityEquipment ee = damager.getEquipment();
		ItemStack held = ee.getItemInMainHand();
		if(held == null) held = ee.getItemInOffHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		if(damaged.getType() != EntityType.ZOMBIE) return;
		Zombie z = (Zombie) damaged;
		Location l = z.getLocation();
		World w = z.getWorld();
		
		double chance = Math.random();
		
		if(z.isVillager())
		{
			if(lore.contains("§7Cure I") && chance >= .8)
			{
				z.setHealth(0);
				w.spawn(l, Villager.class);
				damager.sendMessage(success);
				return;
			}
			if(lore.contains("§7Cure II") && chance >= .5)
			{
				z.setHealth(0);
				w.spawn(l, Villager.class);
				damager.sendMessage(success);
				return;
			}
			if(lore.contains("§7Cure III"))
			{
				z.setHealth(0);
				w.spawn(l, Villager.class);
				damager.sendMessage(success);
				return;
			}
			else
			{
				damager.sendMessage(failure);
			}
		}
	}
}