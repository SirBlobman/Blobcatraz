package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class PoisonIvy implements Listener
{
	public static final String IVY = Enchant.POISON.getName();
	private final String ivy1 = IVY + " §fI";
	private final String ivy2 = ivy1 + "I";
	private final String ivy3 = ivy2 + "I";
	
	@EventHandler
	public void ivy(DamageEnchantEvent e)
	{
		List<String> lore = e.getLore();
		LivingEntity ded = e.getDamaged();
		PotionEffectType POISON = PotionEffectType.POISON;
		PotionEffect poison = new PotionEffect(POISON, 200, 0, true, false);
		
		double chance = Math.random();
		if(lore.contains(ivy1) && chance <= 0.15) ded.addPotionEffect(poison);
		if(lore.contains(ivy2) && chance <= 0.25) ded.addPotionEffect(poison);
		if(lore.contains(ivy3) && chance <= 0.40) ded.addPotionEffect(poison);
	}
}