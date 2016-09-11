package com.SirBlobman.blobcatraz.enchant.event;

import java.util.List;

import org.bukkit.entity.LivingEntity;

public class DamageEnchantEvent extends EnchantEvent 
{
	private LivingEntity damaged,damager;
	private double damage;
	
	public DamageEnchantEvent(List<String> lore, LivingEntity damaged, LivingEntity damager, double damage)
	{
		super(lore);
		this.damaged = damaged;
		this.damager = damager;
	}
	
	public LivingEntity getDamaged()
	{
		return damaged;
	}
	
	public LivingEntity getDamager()
	{
		return damager;
	}
	
	public double getDamage()
	{
		return damage;
	}
}
