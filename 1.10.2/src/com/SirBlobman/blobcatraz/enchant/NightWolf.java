package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class NightWolf implements Listener
{
	public static final String WOLF = Enchant.NIGHT_WOLF.getName();
	private final String wolf1 = WOLF + " §fI";
	private final String wolf2 = wolf1 + "I";
	private final String wolf3 = wolf2 + "I";
	private final String wolf4 = wolf1 + "V";
	private final String wolf5 = WOLF + "V";
	
	@EventHandler
	public void wolf(DamageEnchantEvent e)
	{
		LivingEntity ded = e.getDamaged();
		double dam = e.getDamage();
		List<String> lore = e.getLore();
		
		if(ded.getWorld().getTime() >= 13500)
		{
			double damage = 0;
			double chance = Math.random();
			if(lore.contains(wolf1) && chance <= 0.10) damage = dam * 0.25;
			if(lore.contains(wolf2) && chance <= 0.15) damage = dam * 0.25;
			if(lore.contains(wolf3) && chance <= 0.25) damage = dam * 0.25;
			if(lore.contains(wolf4) && chance <= 0.35) damage = dam * 0.25;
			if(lore.contains(wolf5) && chance <= 0.45) damage = dam * 0.25;
			ded.damage(damage);
		}	
	}
}