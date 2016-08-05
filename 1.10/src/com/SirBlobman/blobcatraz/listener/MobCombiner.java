package com.SirBlobman.blobcatraz.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.material.Colorable;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class MobCombiner implements Listener, Runnable
{
	private FileConfiguration config = ConfigBlobcatraz.config;
	private List<String> disabledWorlds = config.getStringList("mobmerge.disableWorlds");;
	private List<String> mobs = config.getStringList("mobmerge.mobs");
	private int radius = config.getInt("mobmerge.radius");
	private int max = config.getInt("mobmerge.limit");
	private ChatColor color = ChatColor.valueOf(config.getString("mobmerge.color"));
	
	@Override
	public void run()
	{
		for(World w : Bukkit.getServer().getWorlds())
		{
			if(!disabledWorlds.contains(w.getName()))
			{
				process(w);
			}
		}
	}
	
	private void process(World w)
	{
		for(Entity e : w.getEntities())
		{
			if(e instanceof LivingEntity && e.isValid())
			{
				if(mobs.contains(e.getType().name()))
				{
					LivingEntity le = (LivingEntity)e;
					int originalCount = getEntityCount(le);
					int removedCount = 0;
					for(Entity other : e.getNearbyEntities(radius, radius, radius))
					{
						if(match(e, other))
						{
							LivingEntity lother = (LivingEntity)other;
							int otherCount = getEntityCount(lother);
							if(originalCount + removedCount + otherCount < max)
							{
								lother.remove();
								removedCount += otherCount;
							}
						}
					}
					if(removedCount > 0)
					{
						le.setCustomName(color + Integer.toString(originalCount + removedCount));
					}
				}
			}
		}
	}
	
	private boolean match(Entity a, Entity b)
	{
		if(a.getType() == b.getType())
		{
			if(a instanceof Ageable && b instanceof Ageable)
			{
				Ageable aa = (Ageable) a;
				Ageable bb = (Ageable) b;
				if(aa.isAdult() != bb.isAdult()) return false;
			}
			if(a instanceof Colorable && b instanceof Colorable)
			{
				Colorable aa = (Colorable) a;
				Colorable bb = (Colorable) b;
				if(aa.getColor() != bb.getColor()) return false;
			}
			return true;
		}
		return false;
	}
	
	@EventHandler(ignoreCancelled=true)
	public void entityDeath(EntityDeathEvent e)
	{
		LivingEntity le = e.getEntity();
		int count = getEntityCount(le);
		if(count > 1)
		{
			LivingEntity clone = (LivingEntity) le.getWorld().spawnEntity(le.getLocation(), le.getType());
			if(count > 2)
			{
				clone.setCustomName(color + Integer.toString(count - 1));
				clone.getEquipment().setArmorContents(le.getEquipment().getArmorContents());
			}
		}
	}
	
	private int getEntityCount(LivingEntity le)
	{
		int count = 1;
		String name = le.getCustomName();
		if(name != null && name.startsWith(color.toString()))
		{
			try
			{
				count = Integer.parseInt(ChatColor.stripColor(name));
			} catch(NumberFormatException ex) {}
		}
		
		return count;
	}
}