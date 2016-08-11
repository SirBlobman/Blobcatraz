package com.SirBlobman.blobcatraz.config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class ConfigSpawn 
{
	private static File spawnFile = new File(Blobcatraz.instance.getDataFolder(), "spawn.yml");
	private static FileConfiguration spawnConfig = YamlConfiguration.loadConfiguration(spawnFile);
	
	public static void saveSpawn()
	{
		if(!spawnFile.exists())
		{
			try{spawnFile.createNewFile();} catch (Exception ex) {Util.print("Failed to create " + spawnFile); ex.printStackTrace(); return;}
		}
		
		try{spawnConfig.save(spawnFile);} catch (Exception ex) {Util.print("Failed to save " + spawnFile); ex.printStackTrace(); return;}
	}
	
	public static void loadSpawn()
	{
		if(!spawnFile.exists()) setSpawn(Bukkit.getWorlds().get(0).getSpawnLocation());
		
		try{spawnConfig.load(spawnFile);} catch (Exception ex) {Util.print("Failed to load " + spawnFile); ex.printStackTrace(); return;}
		
		if(spawnConfig.get("spawn.world") == null) setSpawn("world", 0, 64, 0, 0, 0);
		if(spawnConfig.get("hub.world") == null) setHub("world", 0, 64, 0, 0, 0);
	}
	
	public static void setSpawn(String worldName, double x, double y, double z, float pitch, float yaw)
	{
		if(worldName == null) return;
		saveSpawn();
		World w = Bukkit.getWorld(worldName);
		if(w == null) return;
		
		spawnConfig.set("spawn.world", worldName);
		spawnConfig.set("spawn.x", x);
		spawnConfig.set("spawn.y", y);
		spawnConfig.set("spawn.z", z);
		spawnConfig.set("spawn.pitch", pitch);
		spawnConfig.set("spawn.yaw", yaw);
		saveSpawn();
	}
	
	public static void setSpawn(Location l)
	{
		if(l == null) return;
		World w = l.getWorld();
		String worldName = w.getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float pitch = l.getPitch();
		float yaw = l.getYaw();
		
		setSpawn(worldName, x, y, z, pitch, yaw);
	}
	
	public static Location getSpawn()
	{
		loadSpawn();
		Location spawn = new Location(null, 0, 0, 0);
		
		World w = Bukkit.getWorld(spawnConfig.getString("spawn.world"));
		if(w == null) return spawn;
		double x = spawnConfig.getDouble("spawn.x");
		double y = spawnConfig.getDouble("spawn.y");
		double z = spawnConfig.getDouble("spawn.z");
		float pitch = (float) spawnConfig.getDouble("spawn.pitch");
		float yaw = (float) spawnConfig.getDouble("spawn.yaw");
		
		spawn.setWorld(w);
		spawn.setX(x);
		spawn.setY(y);
		spawn.setZ(z);
		spawn.setPitch(pitch);
		spawn.setYaw(yaw);
		
		return spawn;
	}
	
	public static void teleportToSpawn(Entity e)
	{
		try
		{
			if(e == null) return;

			Location spawn = getSpawn();
			e.setFallDistance(0.0F);
			e.teleport(spawn);
			e.sendMessage(Util.blobcatraz + "You have been teleported to spawn!");
		}
		catch(Exception ex)
		{
			e.sendMessage(Util.blobcatraz + "The spawn hasn't been set yet! Please contact an Administrator");
		}
	}
	
	public static void setHub(Location l)
	{
		if(l == null) return;
		World w = l.getWorld();
		String worldName = w.getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float pitch = l.getPitch();
		float yaw = l.getYaw();
		setHub(worldName, x, y, z, pitch, yaw);
	}
	
	public static void setHub(String worldName, double x, double y, double z, float pitch, float yaw)
	{
		if(worldName == null) return;
		saveSpawn();
		World w = Bukkit.getWorld(worldName);
		if(w == null) return;
		
		spawnConfig.set("hub.world", worldName);
		spawnConfig.set("hub.x", x);
		spawnConfig.set("hub.y", y);
		spawnConfig.set("hub.z", z);
		spawnConfig.set("hub.pitch", pitch);
		spawnConfig.set("hub.yaw", yaw);
		saveSpawn();
	}
	
	public static Location getHub()
	{
		loadSpawn();
		Location hub = new Location(null, 0, 0, 0);
		
		String worldName = spawnConfig.getString("hub.world");
		World w = Bukkit.getWorld(worldName);
		if(w == null) return hub;
		double x = spawnConfig.getDouble("hub.x");
		double y = spawnConfig.getDouble("hub.y");
		double z = spawnConfig.getDouble("hub.z");
		float pitch = (float) spawnConfig.getDouble("hub.pitch");
		float yaw = (float) spawnConfig.getDouble("hub.yaw");
		
		hub.setWorld(w);
		hub.setX(x);
		hub.setY(y);
		hub.setZ(z);
		hub.setPitch(pitch);
		hub.setYaw(yaw);
		
		return hub;
	}
	
	public static void teleportToHub(Entity e)
	{
		try
		{
			if(e == null) return;

			Location hub = getHub();
			e.setFallDistance(0.0F);
			e.teleport(hub);
			e.sendMessage(Util.blobcatraz + "You have been teleported to hub!");
		}
		catch(Exception ex)
		{
			e.sendMessage(Util.blobcatraz + "The hub hasn't been set yet! Please contact an Administrator");
		}
	}
}