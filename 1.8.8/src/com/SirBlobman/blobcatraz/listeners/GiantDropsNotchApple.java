package com.SirBlobman.blobcatraz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class GiantDropsNotchApple implements Listener
{
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent e)
	{
		if(e.getEntityType() == EntityType.GIANT)
		{
			e.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 1, (short)1));
			Bukkit.broadcastMessage("§1[§6Blobcatraz§1]§r §4The Giant has been slain!");
		}
	}
	
	@EventHandler
	public void onEntitySpawnEvent(EntitySpawnEvent e)
	{
		if(e.getEntityType() == EntityType.GIANT)
		{
			String w = e.getEntity().getLocation().getWorld().getName();
			int x = (int) e.getEntity().getLocation().getX();
			int y = (int) e.getEntity().getLocation().getY();
			int z = (int) e.getEntity().getLocation().getZ();
			Bukkit.broadcastMessage("§1[§6Blobcatraz§1]§r A Giant has been spawned in §o§l" + w + " §rat §l" + x + " " + y + " " + z + "§r!");
			Bukkit.broadcastMessage("             Go kill it for a prize");
		}
	}
}
