package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import com.SirBlobman.blobcatraz.enchant.event.DamageEnchantEvent;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class XPDrain implements Listener
{
	public static final String STEAL = Enchant.DRAIN_XP.getName();
	private final String steal1 = STEAL + " §fI";
	
	@EventHandler
	public void steal(DamageEnchantEvent e)
	{
		List<String> lore = e.getLore();
		LivingEntity ded = e.getDamaged();
		LivingEntity der = e.getDamager();
		if(ded instanceof Player && der instanceof Player)
		{
			Player p1 = (Player) ded;
			Player p2 = (Player) der;
			double chance = Math.random();
			if(lore.contains(steal1) && chance < 0.25)
			{
				if(p1.getLevel() >= 5)
				{
					p1.setLevel(p1.getLevel() - 5);
					p2.setLevel(p2.getLevel() + 5);
				}
			}
		}
	}
}