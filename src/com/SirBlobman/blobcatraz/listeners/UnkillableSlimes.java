package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

public class UnkillableSlimes implements Listener
{
	@EventHandler
	public void onSlimeAttacked(EntityDamageByEntityEvent e)
	{
		EntityType type = e.getEntityType();
		if(type == EntityType.SLIME)
		{
			e.setCancelled(true);
			Slime slime = (Slime) e.getEntity();
			if(slime.getSize() <= 20)
			{
				slime.setSize(slime.getSize() + 1);
			}
			else
			{
				e.getDamager().sendMessage("§1[§6Blobcatraz§1]§r Stop hitting the slime! It is invincible! (obviously)");
			}
		}
		
	}
	
	@EventHandler
	public void onSlimeSplit(SlimeSplitEvent e)
	{
		e.setCancelled(true);
	}
}
