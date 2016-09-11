package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class Wither implements Listener 
{
	public static final String wither = "§7Wither ";
	private final String wither1 = wither + "I";
	private final String wither2 = wither1 + "I";
	private final String wither3 = wither2 + "I";
	
	@EventHandler
	public void wither(DamageEnchantEvent e)
	{
		LivingEntity le = e.getDamaged();
		List<String> lore = e.getLore();
		PotionEffect w1 = new PotionEffect(PotionEffectType.WITHER, 100, 0);
		PotionEffect w2 = new PotionEffect(PotionEffectType.WITHER, 200, 2);
		PotionEffect w3 = new PotionEffect(PotionEffectType.WITHER, 400, 4);
		if(lore.contains(wither1)) le.addPotionEffect(w1, true);
		if(lore.contains(wither2)) le.addPotionEffect(w2, true);
		if(lore.contains(wither3)) le.addPotionEffect(w3, true);
	}
}