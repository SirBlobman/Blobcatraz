package com.SirBlobman.blobcatraz.listener;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenSlime implements Listener
{
	@EventHandler
	public void a(EntityDamageByEntityEvent e)
	{
		if(e.isCancelled()) return;
		
		Entity en = e.getEntity();
		Entity de = e.getDamager();
		if(en instanceof Slime)
		{
			YamlConfiguration config = ConfigBlobcatraz.load();
			int max = config.getInt("invincible slimes.max size");
			
			Slime s = (Slime) en;
			int size = s.getSize();
			if(size < max) s.setSize(size + 1);
			else
			{
				String msg = Util.prefix + Util.option("slime.invincible");
				de.sendMessage(msg);
			}
			
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void split(SlimeSplitEvent e)
	{
		e.setCancelled(true);
	}
}