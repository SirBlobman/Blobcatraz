package com.SirBlobman.blobcatraz.enchants;

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
public class GlowEnchant implements Listener
{
	@EventHandler
	public void attackWithGlowEnchant(EntityDamageByEntityEvent e)
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
		
		//Glow 2 for 10 seconds
		PotionEffect glow1 = new PotionEffect(PotionEffectType.GLOWING, 200, 1);
		//Glow 4 for 20 seconds
		PotionEffect glow2 = new PotionEffect(PotionEffectType.GLOWING, 400, 3);
		//Glow 8 for 40 seconds
		PotionEffect glow3 = new PotionEffect(PotionEffectType.GLOWING, 800, 7);
		
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
		
		if(lore.contains("§7Glow I"))
		{
			ded.addPotionEffect(glow1);
		}		
		if(lore.contains("§7Glow II"))
		{
			ded.addPotionEffect(glow2);
		}		
		if(lore.contains("§7Glow III"))
		{
			ded.addPotionEffect(glow3);
		}
	}
}