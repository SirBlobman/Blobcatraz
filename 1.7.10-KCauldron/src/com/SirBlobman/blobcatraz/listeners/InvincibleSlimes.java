package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
		Entity en = e.getEntity();
		if(e.getEntityType() != EntityType.SLIME) return;
		Slime slime = (Slime) en;
		if(slime == null) return;
		
		e.setCancelled(true);
		if(slime.getSize() <= 20) slime.setSize(slime.getSize() + 1);
		else
		{
			Player p = (Player) e.getDamager();
			if(p == null) return;
			
			p.sendMessage(Util.blobcatraz + "Stop hitting the slime! It is invincible! (obviously)");
		}
	}
	
	@EventHandler
	public void onSlimeSplit(SlimeSplitEvent e) {e.setCancelled(true);}
}