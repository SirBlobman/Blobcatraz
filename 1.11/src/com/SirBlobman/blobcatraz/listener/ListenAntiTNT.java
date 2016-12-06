package com.SirBlobman.blobcatraz.listener;

import java.util.List;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class ListenAntiTNT implements Listener
{
	@EventHandler
	public void tnt(ExplosionPrimeEvent e)
	{
		Entity en = e.getEntity();
		World w = en.getWorld();
		String world = w.getName();
		YamlConfiguration config = ConfigBlobcatraz.load();
		List<String> worlds = config.getStringList("tnt.disabled worlds");
		if(worlds.contains(world)) e.setCancelled(true);
	}
}