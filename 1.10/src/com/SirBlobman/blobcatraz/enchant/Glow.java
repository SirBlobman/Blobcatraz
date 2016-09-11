package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class Glow implements Listener 
{
	public static final String glow = "§a§lGlow ";
	private final String glow1 = glow + "§fI";
	private final String glow2 = glow1 + "I";
	private final String glow3 = glow2 + "I";
	
	@EventHandler
	public void attackWithGlowEnchant(DamageEnchantEvent e)
	{
		LivingEntity damaged = e.getDamaged();
		List<String> lore = e.getLore();
		PotionEffect pglow1 = new PotionEffect(PotionEffectType.GLOWING, 200, 1);
		PotionEffect pglow2 = new PotionEffect(PotionEffectType.GLOWING, 400, 3);
		PotionEffect pglow3 = new PotionEffect(PotionEffectType.GLOWING, 800, 7);

		if(lore.contains(glow1)) damaged.addPotionEffect(pglow1);
		if(lore.contains(glow2)) damaged.addPotionEffect(pglow2);
		if(lore.contains(glow3)) damaged.addPotionEffect(pglow3);
	}
}