package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LifeSteal implements Listener
{
	public static final String STEAL = Enchant.DRAIN_HP.getName();
	private final String steal1 = STEAL + " §fI";
	private final String steal2 = steal1 + "I";
	private final String steal3 = steal2 + "I";
	
	@EventHandler
	public void steal(DamageEnchantEvent e)
	{
		List<String> lore = e.getLore();
		LivingEntity ded = e.getDamaged();
		LivingEntity der = e.getDamager();
		double damage = e.getDamage();
		double chance = Math.random();
		if(lore.contains(steal1) && chance < 0.25) steal(ded, der, chance, damage);
		if(lore.contains(steal2) && chance < 0.50) steal(ded, der, chance, damage);
		if(lore.contains(steal3)) steal(ded, der, chance, damage);
	}
	
	private void steal(LivingEntity ded, LivingEntity der, double chance, double damage)
	{
		double h1 = ded.getHealth();
		double h2 = der.getHealth();
		double mh2 = der.getMaxHealth();
		double life = h2 + (damage * chance);
		double life2 = h1 - (damage * chance);
		double health = Math.min(mh2, life);
		double health2 = Math.max(0, life2);
		der.setHealth(health);
		ded.setHealth(health2);
	}
}