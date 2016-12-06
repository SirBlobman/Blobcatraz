package com.SirBlobman.blobcatraz.listener;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenGiantLoot implements Listener
{
	@EventHandler
	public void giant(CreatureSpawnEvent e)
	{
		if(e.isCancelled()) return;
		
		LivingEntity le = e.getEntity();
		EntityType et = le.getType();
		
		if(et == EntityType.GIANT)
		{
			Location l = le.getLocation();
			World world = l.getWorld();
			String w = world.getName();
			int x = l.getBlockX();
			int y = l.getBlockY();
			int z = l.getBlockZ();
			
			String msg = Util.option("giant.spawn", w, x, y, z);
			Util.broadcast(msg);
		}
	}
	
	@EventHandler
	public void giant(EntityDeathEvent e)
	{
		LivingEntity le = e.getEntity();
		EntityType et = le.getType();
		if(et == EntityType.GIANT)
		{
			YamlConfiguration config = ConfigBlobcatraz.load();
			String m = config.getString("giant loot.prize.item");
			Material mat = Material.matchMaterial(m);
			if(mat == null) {Util.print("Giant Prize is null! Check the config"); return;}
			
			int me = config.getInt("giant loot.prize.meta");
			short meta = ((short) me);
			
			int amount = config.getInt("giant loot.prize.amount");
			
			ItemStack drop = new ItemStack(mat, amount, meta);
			List<ItemStack> drops = e.getDrops();
			drops.add(drop);
			
			String msg = Util.option("giant.death");
			Util.broadcast(msg);
		}
	}
}