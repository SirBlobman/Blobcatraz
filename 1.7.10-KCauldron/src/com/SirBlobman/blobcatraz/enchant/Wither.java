package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Wither implements Listener 
{
	@EventHandler
	public void attackWithWitherEnchant(EntityDamageByEntityEvent e)
	{
		if(!(e.getEntity() instanceof LivingEntity) || !(e.getDamager() instanceof LivingEntity)) return;
		LivingEntity damaged = (LivingEntity)e.getEntity();
		LivingEntity damager = (LivingEntity)e.getDamager();
		
		PotionEffectType wither = PotionEffectType.WITHER;
		PotionEffect wither1 = new PotionEffect(wither, 200, 1);
		PotionEffect wither2 = new PotionEffect(wither, 400, 3);
		PotionEffect wither3 = new PotionEffect(wither, 800, 7);
		
		EntityEquipment ee = damager.getEquipment();
		ItemStack held = ee.getItemInHand();
		if(held == null) return;
		ItemMeta meta = held.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		if(lore.contains("§7Wither I")) damaged.addPotionEffect(wither1);
		if(lore.contains("§7Wither II")) damaged.addPotionEffect(wither2);
		if(lore.contains("§7Wither III")) damaged.addPotionEffect(wither3);
	}
}