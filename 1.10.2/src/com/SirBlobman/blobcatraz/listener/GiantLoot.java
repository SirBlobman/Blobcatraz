package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class GiantLoot implements Listener
{
	@EventHandler
	public void spawn(CreatureSpawnEvent e)
	{
		if(e.isCancelled()) return;
		LivingEntity le = e.getEntity();
		EntityType et = e.getEntityType();
		
		if(et == EntityType.GIANT)
		{
			Location l = le.getLocation();
			World w = l.getWorld();
			String world = w.getName();
			int x = l.getBlockX();
			int y = l.getBlockY();
			int z = l.getBlockZ();

			Util.broadcast(Util.message("giant.spawn", world, x, y, z));
		}	
	}
	
	@EventHandler
	public void death(EntityDeathEvent e)
	{
		EntityType et = e.getEntityType();
		if(et == EntityType.GIANT)
		{
			FileConfiguration config = ConfigBlobcatraz.load();
			String mat = config.getString("giant loot.prize");
			Material prize = Material.matchMaterial(mat);
			if(mat == null) {Util.broadcast("Giant loot prize is null! Check the config"); return;}
			short meta = (short) config.getInt("giant loot.meta");
			int amount = config.getInt("giant loot.amount");
			
			ItemStack drop = new ItemStack(prize, amount, meta);
			e.getDrops().add(drop);
			Util.broadcast(Util.message("giant.death"));
		}
	}
}