package com.SirBlobman.blobcatraz.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class GiantDropsPrize implements Listener 
{
	@EventHandler
	public void onGiantSpawn(CreatureSpawnEvent e)
	{
		LivingEntity le = e.getEntity();
		EntityType et = le.getType();
		
		if(et == EntityType.GIANT)
		{
			Location l = le.getLocation();
			World w = l.getWorld();
			String worldName = w.getName();
			int x = (int) l.getX();
			int y = (int) l.getY();
			int z = (int) l.getZ();
			
			Util.broadcast("A Giant has been spawned:\n §o" + worldName + ": " + x + ", " + y + ", " + z);
			Util.broadcast("Go kill it for a prize");
		}
	}
	
	@EventHandler
	public void onGiantDeath(EntityDeathEvent e)
	{
		LivingEntity le = e.getEntity();
		EntityType et = le.getType();
		if(et == EntityType.GIANT)
		{
			Material mat = Material.getMaterial(ConfigBlobcatraz.config.getString("random.giantDropsPrize.prize"));
			short meta = (short) ConfigBlobcatraz.config.getInt("random.giantDropsPrize.meta");
			int amount = ConfigBlobcatraz.config.getInt("random.giantDropsPrize.amount");
			ItemStack drop = new ItemStack(mat);
			drop.setDurability(meta);
			drop.setAmount(amount);
			
			e.getDrops().add(drop);
			Util.broadcast("§4The Giant has been slain!");
		}
	}
}