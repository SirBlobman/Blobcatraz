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

public class Glow implements Listener 
{
	@EventHandler
	public void attackWithGlowEnchant(EntityDamageByEntityEvent e)
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

		PotionEffect glow1 = new PotionEffect(PotionEffectType.GLOWING, 200, 1);
		PotionEffect glow2 = new PotionEffect(PotionEffectType.GLOWING, 400, 3);
		PotionEffect glow3 = new PotionEffect(PotionEffectType.GLOWING, 800, 7);

		if(lore.contains("§7Glow I")) damaged.addPotionEffect(glow1);
		if(lore.contains("§7Glow II")) damaged.addPotionEffect(glow2);
		if(lore.contains("§7Glow III")) damaged.addPotionEffect(glow3);
	}
}