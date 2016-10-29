package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class Slow implements Listener
{
	public static final String SLOW = Enchant.SLOW.getName();
	private final String slow1 = SLOW + " §fI";
	private final String slow2 = slow1 + "I";
	private final String slow3 = slow2 + "I";
	
	@EventHandler
	public void slow(DamageEnchantEvent e)
	{
		LivingEntity ded = e.getDamaged();
		List<String> lore = e.getLore();
		PotionEffectType SLIME = PotionEffectType.SLOW;
		PotionEffect slime = new PotionEffect(SLIME, 200, 5, false, false);
		
		double chance = Math.random();
		if(lore.contains(slow1) && chance <= 0.15) ded.addPotionEffect(slime);
		if(lore.contains(slow2) && chance <= 0.25) ded.addPotionEffect(slime);
		if(lore.contains(slow3) && chance <= 0.35) ded.addPotionEffect(slime);
		boolean b1 = lore.contains(slow1);
		boolean b2 = lore.contains(slow2);
		boolean b3 = lore.contains(slow3);
		boolean b4 = (b1 || b2 || b3);
		if(b4 && chance >= 0.50)
		{
			Location l = ded.getLocation();
			World w = l.getWorld();
			Class<Slime> s = Slime.class;
			Slime slime1 = w.spawn(l, s);
			slime1.setSize(1);
		}
	}
}