package com.SirBlobman.blobcatraz.config;

import java.io.File;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

public class ConfigSpawn
{
	private static File file = new File(Blobcatraz.folder, "spawn.yml");
	private static YamlConfiguration spawns = YamlConfiguration.loadConfiguration(file);
	
	public static YamlConfiguration load()
	{
		if(!file.exists()) save();
		try{spawns.load(file);}
		catch(Exception ex)
		{
			Util.print("Failed to load " + file + ": " + ex.getCause());
			return null;
		}
		return spawns;
	}
	
	public static void save()
	{
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
				writeDefaults();
			} catch(Exception ex)
			{
				Util.print("Failed to create " + file + ": " + ex.getCause());
				return;
			}
		}
		
		try{spawns.save(file);}
		catch(Exception ex)
		{
			Util.print("Failed to save " + file + ": " + ex.getCause());
			return;
		}
	}
	
	private static void writeDefaults()
	{
		for(World w : Bukkit.getWorlds())
		{
			String world = w.getName();
			String path = "spawns." + world + ".";
			spawns.set(path + "x", 0.5D);
			spawns.set(path + "y", 64.0D);
			spawns.set(path + "z", 0.5D);
			spawns.set(path + "yaw", 0.0F);
			spawns.set(path + "pitch", 0.0F);
		}
		spawns.set("hub.world", "world");
		spawns.set("hub.x", 0.5D);
		spawns.set("hub.y", 64.0D);
		spawns.set("hub.z", 0.5D);
		spawns.set("hub.yaw", 0.0F);
		spawns.set("hub.pitch", 0.0F);
		save();
	}
	
	public static void reload()
	{
		load();
		save();
	}
	
	public static Location getSpawn(World w)
	{
		YamlConfiguration config = load();
		String world = w.getName();
		String path = "spawns." + world + ".";
		double x = config.getDouble(path + "x");
		double y = config.getDouble(path + "y");
		double z = config.getDouble(path + "z");
		float yaw = (float) config.getDouble(path + "yaw");
		float pitch = (float) config.getDouble(path + "pitch");
		Location spawn = new Location(w, x, y, z, yaw, pitch);
		return spawn;
	}
	
	public static void spawn(Entity e, World w)
	{
		Location spawn = getSpawn(w);
		e.teleport(spawn);
		e.sendMessage(Util.blobcatraz + "You were teleported to spawn!");
	}
	
	public static Location getHub()
	{
		YamlConfiguration config = load();
		String world = config.getString("hub.world");
		World w = Bukkit.getWorld(world);
		if(w == null) 
		{
			Util.print("Error: HUB WORLD IS NULL!");
			return null;
		}
		double x = config.getDouble("hub.x");
		double y = config.getDouble("hub.y");
		double z = config.getDouble("hub.z");
		float yaw = (float) config.getDouble("hub.yaw");
		float pitch = (float) config.getDouble("hub.pitch");
		Location hub = new Location(w, x, y, z, yaw, pitch);
		return hub;
	}
	
	public static void hub(Entity e)
	{
		Location hub = getHub();
		e.teleport(hub);
		e.sendMessage(Util.blobcatraz + "You were teleported to the Hub");
	}
	
	public static void setHub(Location l)
	{
		World w = l.getWorld();
		String world = w.getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();

		spawns.set("hub.world", world);
		spawns.set("hub.x", x);
		spawns.set("hub.y", y);
		spawns.set("hub.z", z);
		spawns.set("hub.yaw", yaw);
		spawns.set("hub.pitch", pitch);
		
		save();
	}
	
	public static void setSpawn(Location l)
	{
		World w = l.getWorld();
		String world = w.getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();

		String path = "spawns." + world + ".";
		spawns.set(path + "x", x);
		spawns.set(path + "y", y);
		spawns.set(path + "z", z);
		spawns.set(path + "yaw", yaw);
		spawns.set(path + "pitch", pitch);
		
		save();
	}
}