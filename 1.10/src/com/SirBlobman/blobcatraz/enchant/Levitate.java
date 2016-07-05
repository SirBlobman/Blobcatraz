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

public class Levitate implements Listener 
{
	@EventHandler
	public void attackWithLevitateEnchant(EntityDamageByEntityEvent e)
	{
		if(!(e.getEntity() instanceof LivingEntity) || !(e.getDamager() instanceof LivingEntity)) return;
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

		PotionEffect lev1 = new PotionEffect(PotionEffectType.LEVITATION, 200, 1);
		PotionEffect lev2 = new PotionEffect(PotionEffectType.LEVITATION, 400, 3);
		PotionEffect lev3 = new PotionEffect(PotionEffectType.LEVITATION, 800, 7);

		if(lore.contains("§7Levitate I")) damaged.addPotionEffect(lev1);
		if(lore.contains("§7Levitate II")) damaged.addPotionEffect(lev2);
		if(lore.contains("§7Levitate III")) damaged.addPotionEffect(lev3);
	}
}