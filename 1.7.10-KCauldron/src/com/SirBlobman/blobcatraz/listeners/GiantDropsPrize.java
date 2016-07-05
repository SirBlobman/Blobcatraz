package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
	public void onGiantDeath(EntityDeathEvent e)
	{
		if(e.getEntity().getType() == EntityType.GIANT)
		{
			ConfigBlobcatraz.loadConfig();
			
			Material mat = Material.getMaterial(ConfigBlobcatraz.config.getString("random.giantDropsPrize.prize"));
			short meta = (short) ConfigBlobcatraz.config.getInt("random.giantDropsPrize.meta");
			int amount = ConfigBlobcatraz.config.getInt("random.giantDropsPrize.amount");
			ItemStack drop = new ItemStack(mat, amount, meta);
			
			e.getDrops().add(drop);
			Util.broadcast("§4The Giant has been slain!");
		}
	}
	
	@EventHandler
	public void onGiantSpawn(CreatureSpawnEvent e)
	{
		Entity entity = e.getEntity();
		EntityType et = entity.getType();
		
		if(et == EntityType.GIANT)
		{
			String world = entity.getWorld().getName();
			int x = (int) entity.getLocation().getX();
			int y = (int) entity.getLocation().getY();
			int z = (int) entity.getLocation().getZ();
			
			Util.broadcast("A Giant has been spawned, go kill it for a prize:");
			Util.broadcast(world + ":" + x + " " + y + " " + z);
		}
	}
}