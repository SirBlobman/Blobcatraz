package com.SirBlobman.blobcatraz.enchants;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("deprecation")
public class WitherEnchant implements Listener
{
	@EventHandler
	public void attackWithWitherEnchant(EntityDamageByEntityEvent e)
	{
		Player der = (Player) e.getDamager();
		LivingEntity ded = (LivingEntity) e.getEntity();
		//Wither 2 for 10 seconds
		PotionEffect wither1 = new PotionEffect(PotionEffectType.WITHER, 200, 1);
		//Wither 4 for 20 seconds
		PotionEffect wither2 = new PotionEffect(PotionEffectType.WITHER, 400, 3);
		//Wither 8 for 40 seconds
		PotionEffect wither3 = new PotionEffect(PotionEffectType.WITHER, 800, 7);		
		if(der.getItemInHand().getItemMeta().getLore().contains("§7Wither I"))
		{
			ded.addPotionEffect(wither1);
		}		
		if(der.getItemInHand().getItemMeta().getLore().contains("§7Wither II"))
		{
			ded.addPotionEffect(wither2);
		}		
		if(der.getItemInHand().getItemMeta().getLore().contains("§7Wither III"))
		{
			ded.addPotionEffect(wither3);
		}
	}
}