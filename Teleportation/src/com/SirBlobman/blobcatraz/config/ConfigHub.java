package com.SirBlobman.blobcatraz.config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigHub
{
	private static File folder = Blobcatraz.folder;
	private static File file = new File(folder, "hub.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public static YamlConfiguration load()
	{
		if(!file.exists()) save();
		try 
		{
			config.load(file); 
			defaults();
			return config;
		} catch(Exception ex)
		{
			Util.print("Failed to load " + file + ": " + ex.getMessage());
			return null;
		}
	}
	
	public static void save()
	{
		if(!file.exists())
		{
			try{folder.mkdirs(); file.createNewFile(); defaults();}
			catch(Exception ex) {Util.print("Failed to create " + file + ": " + ex.getMessage());}
		}
		try{config.save(file);}
		catch(Exception ex) {Util.print("Failed to save " + file + ": " + ex.getMessage());}
	}
	
	private static void defaults()
	{
		set("world", "Hub", false);
		set("x", 0.0D, false);
		set("y", 64.0D, false);
		set("z", 0.0D, false);
		set("yaw", 90.0F, false);
		set("pitch", 0.0F, false);
		save();
	}
	
	private static void set(String path, Object value, boolean force)
	{
		boolean b1 = (config.get(path) == null);
		if(b1 || force) config.set(path, value);
	}
	
	public static Location hub()
	{
		load();
		String world = config.getString("world");
		World w = Bukkit.getWorld(world);
		double x = config.getDouble("x");
		double y = config.getDouble("y");
		double z = config.getDouble("z");
		float yaw = (float) config.getDouble("yaw");
		float pitch = (float) config.getDouble("pitch");
		Location hub = new Location(w, x, y, z, yaw, pitch);
		return hub;
	}
	
	public static void setHub(Location hub)
	{
		World w = hub.getWorld();
		String world = w.getName();
		double x = hub.getX();
		double y = hub.getY();
		double z = hub.getZ();
		float yaw = hub.getYaw();
		float pitch = hub.getPitch();
		
		set("world", world, true);
		set("x", x, true);
		set("y", y, true);
		set("z", z, true);
		set("yaw", yaw, true);
		set("pitch", pitch, true);
		save();
	}
}