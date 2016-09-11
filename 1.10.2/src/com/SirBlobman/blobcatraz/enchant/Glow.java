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
		PotionEffect g1 = new PotionEffect(glow, 200, 1);
		PotionEffect g2 = new PotionEffect(glow, 400, 3);
		PotionEffect g3 = new PotionEffect(glow, 800, 7);
		
		if(lore.contains(glow1)) damaged.addPotionEffect(g1);
		if(lore.contains(glow2)) damaged.addPotionEffect(g2);
		if(lore.contains(glow3)) damaged.addPotionEffect(g3);
	}
}