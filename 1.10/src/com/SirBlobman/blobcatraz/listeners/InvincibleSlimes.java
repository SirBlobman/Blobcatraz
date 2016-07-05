package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

import com.SirBlobman.blobcatraz.Util;

public class InvincibleSlimes implements Listener
{
	@EventHandler
	public void onSlimeAttacked(EntityDamageByEntityEvent e)
	{
		EntityType type = e.getEntityType();
		if(type != EntityType.SLIME) return;
		
		Slime slime = (Slime) e.getEntity();
		
		if(slime.getSize() <= 20) slime.setSize(slime.getSize() + 1);
		else e.getDamager().sendMessage(Util.blobcatraz + "Stop hitting the slime! It is obviously invincible!");
	}
	
	@EventHandler
	public void onSlimeSplit(SlimeSplitEvent e)
	{
		EntityType type = e.getEntityType();
		if(type != EntityType.SLIME) return;
		
		e.setCancelled(true);
	}
}