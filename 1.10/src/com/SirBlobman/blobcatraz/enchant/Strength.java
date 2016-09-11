package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SirBlobman.blobcatraz.enchant.event.ArmorEnchantEvent;

public class Strength implements Listener
{
	public static final String strong = "§7Strength ";
	private final String strong1 = strong + "I";
	
	@EventHandler
	public void strength(ArmorEnchantEvent e)
	{
		Player p = e.getPlayer();
		List<String> head = e.getHelmetLore();
		List<String> chest = e.getChestplateLore();
		List<String> legs = e.getLeggingsLore();
		List<String> feet = e.getBootsLore();
		
		PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1);
		if(head.contains(strong1) && chest.contains(strong1) && legs.contains(strong1) && feet.contains(strong1)) p.addPotionEffect(strength);
	}
}