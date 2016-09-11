package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class Levitate implements Listener 
{
	public static final String levitate = "§7Levitate ";
	private final String levitate1 = levitate + "I";
	private final String levitate2 = levitate1 + "I";
	private final String levitate3 = levitate2 + "I";
	
	@EventHandler
	public void levitate(DamageEnchantEvent e)
	{
		LivingEntity damaged = e.getDamaged();
		List<String> lore = e.getLore();

		PotionEffect lev1 = new PotionEffect(PotionEffectType.LEVITATION, 200, 1);
		PotionEffect lev2 = new PotionEffect(PotionEffectType.LEVITATION, 400, 3);
		PotionEffect lev3 = new PotionEffect(PotionEffectType.LEVITATION, 800, 7);

		if(lore.contains(levitate1)) damaged.addPotionEffect(lev1);
		if(lore.contains(levitate2)) damaged.addPotionEffect(lev2);
		if(lore.contains(levitate3)) damaged.addPotionEffect(lev3);
	}
}