package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import com.SirBlobman.blobcatraz.enchant.event.ArmorEnchantEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Strength implements Listener
{
	public static final String strong = Enchant.STRONG.getName();
	private final String strong1 = strong + " §fI";
	
	@EventHandler
	public void strong(ArmorEnchantEvent e)
	{
		LivingEntity le = e.getEntity();
		List<String> head = e.getHelmet();
		List<String> chest = e.getChestplate();
		List<String> legs = e.getLeggings();
		List<String> feet = e.getBoots();
		
		PotionEffectType strength = PotionEffectType.INCREASE_DAMAGE;
		PotionEffect s1 = new PotionEffect(strength, 200, 1);
		if
		(
			head.contains(strong1)
			&& chest.contains(strong1)
			&& legs.contains(strong1)
			&& feet.contains(strong1)
		) le.addPotionEffect(s1);
	}
}