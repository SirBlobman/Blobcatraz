package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

public class XPDrain implements Listener
{
	public static final String steal = "§6XP Steal ";
	private final String steal1 = steal + "§fI";
	
	@EventHandler
	public void steal(DamageEnchantEvent e)
	{
		List<String> lore = e.getLore();
		LivingEntity damaged = e.getDamaged();
		LivingEntity damager = e.getDamager();
		if(!(damaged instanceof Player) || !(damager instanceof Player)) return;
		Player ded = (Player) damaged;
		Player der = (Player) damager;
		double chance = Math.random();
		if(lore.contains(steal1) && chance < 0.25)
		{
			if(ded.getLevel() >= 5)
			{
				ded.setExp(ded.getExp() - 5.0F);
				der.setExp(der.getExp() + 5.0F);
			}
		}
	}
}