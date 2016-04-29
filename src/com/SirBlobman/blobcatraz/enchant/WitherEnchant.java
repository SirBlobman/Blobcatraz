package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("deprecation")
public class WitherEnchant implements Listener
{
	@EventHandler
	public void attackWithWitherEnchant(EntityDamageByEntityEvent e)
	{
		if(!(e.getDamager().getType() == EntityType.PLAYER))
		{
			return;
		}
		Player der = (Player) e.getDamager();
		if(der == null)
		{
			return;
		}
		
		LivingEntity ded = (LivingEntity) e.getEntity();
		if(ded == null)
		{
			return;
		}
		
		//Wither 2 for 10 seconds
		PotionEffect wither1 = new PotionEffect(PotionEffectType.WITHER, 200, 1);
		//Wither 4 for 20 seconds
		PotionEffect wither2 = new PotionEffect(PotionEffectType.WITHER, 400, 3);
		//Wither 8 for 40 seconds
		PotionEffect wither3 = new PotionEffect(PotionEffectType.WITHER, 800, 7);
		
		ItemStack held_item = der.getItemInHand();
		if(held_item == null)
		{
			return;
		}
		ItemMeta meta = held_item.getItemMeta();
		if(meta == null)
		{
			return;
		}
		List<String> lore = meta.getLore();
		if(lore == null)
		{
			return;
		}
		
		if(lore.contains("§7Wither I"))
		{
			ded.addPotionEffect(wither1);
		}		
		if(lore.contains("§7Wither II"))
		{
			ded.addPotionEffect(wither2);
		}		
		if(lore.contains("§7Wither III"))
		{
			ded.addPotionEffect(wither3);
		}
	}
}