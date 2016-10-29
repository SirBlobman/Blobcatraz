package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Glow implements Listener
{
	public static final String GLOW = Enchant.GLOW.getName();
	private final String glow1 = GLOW + " §fI";
	private final String glow2 = glow1 + "I";
	private final String glow3 = glow2 + "I";
	
	@EventHandler
	public void glow(DamageEnchantEvent e)
	{
		List<String> lore = e.getLore();
		LivingEntity damaged = e.getDamaged();
		PotionEffectType glow = PotionEffectType.GLOWING;
		PotionEffect glowing = new PotionEffect(glow, 200, 1);
		
		double chance = Math.random();
		if(lore.contains(glow1) && chance <= 0.35) damaged.addPotionEffect(glowing);
		if(lore.contains(glow2) && chance <= 0.45) damaged.addPotionEffect(glowing);
		if(lore.contains(glow3) && chance <= 0.50) damaged.addPotionEffect(glowing);
	}
}