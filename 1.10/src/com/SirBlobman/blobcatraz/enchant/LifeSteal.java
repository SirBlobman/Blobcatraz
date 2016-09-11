package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class LifeSteal implements Listener
{
	public static final String steal = "§8§lLife Steal ";
	private final String steal1 = steal + "§fI";
	private final String steal2 = steal1 + "I";
	private final String steal3 = steal2 + "I";
	
	@EventHandler
	public void lifesteal(DamageEnchantEvent e)
	{
		List<String> lore = e.getLore();
		LivingEntity damaged = e.getDamaged();
		LivingEntity damager = e.getDamager();
		double damage = e.getDamage();
		double chance = Math.random();
		if(lore.contains(steal1) && chance <= 0.25) steal(damaged, damager, chance, damage);
		if(lore.contains(steal2) && chance <= 0.5) steal(damaged, damager, chance, damage);
		if(lore.contains(steal3)) steal(damaged, damager, 1.0, damage);
	}
	
	private void steal(LivingEntity damaged, LivingEntity damager, double chance, double damage)
	{
		double hded = damaged.getHealth();
		double hder = damager.getHealth();
		damager.setHealth(Math.min(hder + (damage * chance), damager.getMaxHealth()));
		damaged.setHealth(Math.max(0, hded - (damage * chance)));
	}
}