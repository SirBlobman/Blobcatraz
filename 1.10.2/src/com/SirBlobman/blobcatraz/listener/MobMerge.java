package com.SirBlobman.blobcatraz.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Colorable;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class MobMerge implements Listener,Runnable
{
	private FileConfiguration config = ConfigBlobcatraz.load();
	private List<String> disabled = config.getStringList("mob merge.disabled worlds");
	private List<String> mobs = config.getStringList("mob merge.mobs");
	private int radius = config.getInt("mob merge.radius");
	private int max = config.getInt("mob merge.limit");
	private String c = config.getString("mob merge.color");
	private ChatColor color = ChatColor.valueOf(c);
	
	@EventHandler
	public void death(EntityDeathEvent e)
	{
		LivingEntity le = e.getEntity();
		EntityType et = le.getType();
		int count = getCount(le);
		if(count > 1)
		{
			World w = le.getWorld();
			Location l = le.getLocation();
			LivingEntity clone = (LivingEntity) w.spawnEntity(l, et);
			if(count > 2)
			{
				EntityEquipment ee = le.getEquipment();
				ItemStack[] armor = ee.getArmorContents();
				clone.setCustomName(color + Integer.toString(count - 1));
				EntityEquipment ec = clone.getEquipment();
				ec.setArmorContents(armor);
			}
		}
	}
	
	@Override
	public void run()
	{
		for(World w : Bukkit.getServer().getWorlds())
		{
			if(!disabled.contains(w.getName())) process(w);
		}
	}
	
	private int getCount(LivingEntity le)
	{
		int count = 1;
		String name = le.getCustomName();
		if(name != null && name.startsWith(color.toString()))
		{
			try{count = Integer.parseInt(Util.uncolor(name));}
			catch(Exception ex) {};
		}
		return count;
	}
	
	private boolean match(Entity a, Entity b)
	{
		EntityType at = a.getType();
		EntityType bt = b.getType();
		if(at == bt)
		{
			if(a instanceof Ageable && b instanceof Ageable)
			{
				Ageable aa = (Ageable) a;
				Ageable ab = (Ageable) b;
				if(aa.isAdult() != ab.isAdult()) return false;
			}
			if(a instanceof Colorable && b instanceof Colorable)
			{
				Colorable ca = (Colorable) a;
				Colorable cb = (Colorable) b;
				if(ca.getColor() != cb.getColor()) return false;
			}
			return true;
		}
		return false;
	}
	
	private void process(World w)
	{
		for(Entity e : w.getEntities())
		{
			if(e instanceof LivingEntity && e.isValid())
			{
				LivingEntity le = (LivingEntity) e;
				EntityType et = le.getType();
				if(mobs.contains(et.name()))
				{
					int count = getCount(le);
					int removed = 0;
					List<Entity> near = le.getNearbyEntities(radius, radius, radius);
					for(Entity other : near)
					{
						if(match(e, other))
						{
							LivingEntity le2 = (LivingEntity) other;
							int ocount = getCount(le2);
							if(count + removed + ocount < max)
							{
								le2.remove();
								removed += ocount;
							}
						}
					}
					if(removed > 0)
					{
						le.setCustomName(color + "" + (count + removed));
					}
				}
			}
		}
	}
}