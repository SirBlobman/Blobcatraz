package com.SirBlobman.blobcatraz.listener;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

public class InvincibleSlime implements Listener
{
	@EventHandler
	public void attacked(EntityDamageByEntityEvent e)
	{
		if(e.isCancelled()) return;
		Entity ent = e.getEntity();
		Entity der = e.getDamager();
		EntityType et = e.getEntityType();
		if(et == EntityType.SLIME)
		{
			FileConfiguration config = ConfigBlobcatraz.load();
			int max = config.getInt("invincible slimes.max size");
			
			Slime s = (Slime) ent;
			int size = s.getSize();
			if(size < max) s.setSize(size + 1);
			else der.sendMessage(Util.blobcatraz + Util.message("slime.isInvincible"));
			
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void split(SlimeSplitEvent e)
	{
		EntityType et = e.getEntityType();
		if(et == EntityType.SLIME) e.setCancelled(true);
	}
}