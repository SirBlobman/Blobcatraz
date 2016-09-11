package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Wither implements Listener
{
	public static final String WITHER = Enchant.WITHER.getName();
	private final String wither1 = WITHER + " §fI";
	private final String wither2 = wither1 + "I";
	private final String wither3 = wither2 + "I";
	
	@EventHandler
	public void wither(DamageEnchantEvent e)
	{
		List<String> lore = e.getLore();
		LivingEntity le = e.getDamaged();
		PotionEffectType wither = PotionEffectType.WITHER;
		PotionEffect w1 = new PotionEffect(wither, 100, 0);
		PotionEffect w2 = new PotionEffect(wither, 200, 2);
		PotionEffect w3 = new PotionEffect(wither, 400, 4);
		if(lore.contains(wither1)) le.addPotionEffect(w1);
		if(lore.contains(wither2)) le.addPotionEffect(w2);
		if(lore.contains(wither3)) le.addPotionEffect(w3);
	}
}