package com.SirBlobman.blobcatraz.config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigSpawn 
{
	private static File folder = Blobcatraz.folder;
	private static File file = new File(folder, "spawn.yml");
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
		for(World w : Bukkit.getWorlds())
		{
			String world = w.getName();
			double x = 0.0D;
			double y = 64.0D;
			double z = 0.0D;
			float pitch = 90.0F;
			float yaw = 0.0F;
			String path = world + ".";
			set(path + "x", x, false);
			set(path + "y", y, false);
			set(path + "z", z, false);
			set(path + "yaw", yaw, false);
			set(path + "pitch", pitch, false);
		}
		save();
	}
	
	private static void set(String path, Number n, boolean force)
	{
		boolean b1 = (config.get(path) == null);
		if(b1 || force) config.set(path, n);
	}
	
	public static Location spawn(World w)
	{
		String world = w.getName();
		return spawn(world);
	}
	
	public static Location spawn(String world)
	{
		load();
		String path = world + ".";
		World w = Bukkit.getWorld(world);
		double x = config.getDouble(path + "x");
		double y = config.getDouble(path + "y");
		double z = config.getDouble(path + "z");
		float pitch = (float) config.getDouble(path + "pitch");
		float yaw = (float) config.getDouble(path + "yaw");		
		Location l = new Location(w, x, y, z, yaw, pitch);
		return l;
	}
	
	public static void setSpawn(Location l)
	{
		World w = l.getWorld();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		setSpawn(w, x, y, z, yaw, pitch);
	}
	
	public static void setSpawn(World w, double x, double y, double z, float yaw, float pitch)
	{
		String world = w.getName();
		String path = world + ".";
		set(path + "x", x, true);
		set(path + "y", y, true);
		set(path + "z", z, true);
		set(path + "yaw", yaw, true);
		set(path + "pitch", pitch, true);
		save();
	}
}