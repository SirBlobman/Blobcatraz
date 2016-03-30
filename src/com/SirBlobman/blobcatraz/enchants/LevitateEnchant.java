package com.SirBlobman.blobcatraz.enchants;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("deprecation")
public class LevitateEnchant implements Listener
{
	@EventHandler
	public void onHitWithLevitateEnchant(EntityDamageByEntityEvent e)
	{
		Player der = (Player) e.getDamager();
		LivingEntity ded = (LivingEntity) e.getEntity();
		
		PotionEffect levitate1 = new PotionEffect(PotionEffectType.LEVITATION, 200, 1);
		//Wither 4 for 20 seconds
		PotionEffect levitate2 = new PotionEffect(PotionEffectType.LEVITATION, 400, 3);
		//Wither 8 for 40 seconds
		PotionEffect levitate3 = new PotionEffect(PotionEffectType.LEVITATION, 800, 7);		
		if(der.getItemInHand().getItemMeta().getLore().contains("§7Levitate I"))
		{
			ded.addPotionEffect(levitate1);
		}		
		if(der.getItemInHand().getItemMeta().getLore().contains("§7Levitate II"))
		{
			ded.addPotionEffect(levitate2);
		}		
		if(der.getItemInHand().getItemMeta().getLore().contains("§7Levitate III"))
		{
			ded.addPotionEffect(levitate3);
		}
	}
}
