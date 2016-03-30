package com.SirBlobman.blobcatraz.enchants;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@SuppressWarnings("deprecation")
public class XPStealEnchant implements Listener
{
	
	@EventHandler
	public void onPlayerHitWithXPStealEnchant(EntityDamageByEntityEvent e)
	{
		Player der = (Player) e.getDamager();
		Player ded = (Player) e.getEntity();
		
		if(der.getItemInHand().getItemMeta().getLore().contains("§66XP Drain I"))
		{
			if(ded.getLevel() >= 5)
			{
				ded.setLevel(ded.getLevel() - 5);
				der.setLevel(der.getLevel() + 5);
			}
		}
	}
}
