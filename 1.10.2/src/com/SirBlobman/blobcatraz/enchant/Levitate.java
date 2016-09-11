package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Levitate implements Listener
{
	public static final String LEVITATE = Enchant.FLOAT.getName();
	private final String levitate1 = LEVITATE + " §fI";
	private final String levitate2 = levitate1 + "I";
	private final String levitate3 = levitate2 + "I";
	
	@EventHandler
	public void lev(DamageEnchantEvent e)
	{
		List<String> lore = e.getLore();
		LivingEntity damaged = e.getDamaged();
		PotionEffectType fly = PotionEffectType.LEVITATION;
		PotionEffect l1 = new PotionEffect(fly, 200, 1);
		PotionEffect l2 = new PotionEffect(fly, 400, 3);
		PotionEffect l3 = new PotionEffect(fly, 800, 7);
		
		if(lore.contains(levitate1)) damaged.addPotionEffect(l1);
		if(lore.contains(levitate2)) damaged.addPotionEffect(l2);
		if(lore.contains(levitate3)) damaged.addPotionEffect(l3);
	}
}