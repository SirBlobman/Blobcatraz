package com.SirBlobman.blobcatraz.listener;

import java.util.List;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class AntiTNT implements Listener
{
	@EventHandler
	public void spawn(ExplosionPrimeEvent e)
	{
		Entity ent = e.getEntity();
		World w = ent.getWorld();
		String world = w.getName();
		YamlConfiguration yc = ConfigBlobcatraz.load();
		List<String> worlds = yc.getStringList("tnt.disabled worlds");
		if(worlds.contains(world)) e.setCancelled(true);
	}
}