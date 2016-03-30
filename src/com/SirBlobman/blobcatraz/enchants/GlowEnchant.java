package com.SirBlobman.blobcatraz.enchants;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("deprecation")
public class GlowEnchant implements Listener
{
	@EventHandler
	public void attackWithGlowEnchant(EntityDamageByEntityEvent e)
	{
		Player der = (Player) e.getDamager();
		LivingEntity ded = (LivingEntity) e.getEntity();
		//Glow 2 for 10 seconds
		PotionEffect glow1 = new PotionEffect(PotionEffectType.GLOWING, 200, 1);
		//Glow 4 for 20 seconds
		PotionEffect glow2 = new PotionEffect(PotionEffectType.GLOWING, 400, 3);
		//Glow 8 for 40 seconds
		PotionEffect glow3 = new PotionEffect(PotionEffectType.GLOWING, 800, 7);		
		if(der.getItemInHand().getItemMeta().getLore().contains("§7Glow I"))
		{
			ded.addPotionEffect(glow1);
		}		
		if(der.getItemInHand().getItemMeta().getLore().contains("§7Glow II"))
		{
			ded.addPotionEffect(glow2);
		}		
		if(der.getItemInHand().getItemMeta().getLore().contains("§7Glow III"))
		{
			ded.addPotionEffect(glow3);
		}
	}
}