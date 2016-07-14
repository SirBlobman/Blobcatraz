package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class ConfigWarps 
{
	public static HashMap<String, Location> warps = new HashMap<String, Location>();
	private static File warpsFolder = new File(Blobcatraz.instance.getDataFolder() + File.separator + "warps");
	private static File[] warpFiles = warpsFolder.listFiles();
	private static FileConfiguration warp = new YamlConfiguration();
	
	public static void saveWarp(String name, Location l)
	{
		if(name == null || l == null) return;
		
		for(String w : warps.keySet())
		{
			File warpName = new File(warpsFolder + File.separator + w + ".yml");
			warp = YamlConfiguration.loadConfiguration(warpName);
			
			World world = l.getWorld();
			double x = l.getX();
			double y = l.getY();
			double z = l.getZ();
			float pitch = l.getPitch();
			float yaw = l.getYaw();
			
			warp.set("name", name);
			warp.set("world", world);
			warp.set("x", x);
			warp.set("y", y);
			warp.set("z", z);
			warp.set("pitch", pitch);
			warp.set("yaw", yaw);
			
			try{warp.save(warpName);} catch (Exception ex) {Util.print("Failed to save " + warpName + "!"); return;}
		}
	}
	
	public static void loadWarps()
	{
		for(File f : warpFiles)
		{
			try
			{
				warp.load(f);
				Location l = new Location(null, 0, 0, 0);
				
				String name = warp.getString("name");
				World w = Bukkit.getWorld(warp.getString("world"));
				double x = warp.getDouble("x");
				double y = warp.getDouble("y");
				double z = warp.getDouble("z");
				float pitch = (float) warp.getDouble("pitch");
				float yaw = (float) warp.getDouble("yaw");
				
				l.setWorld(w);
				l.setX(x);
				l.setY(y);
				l.setZ(z);
				l.setPitch(pitch);
				l.setYaw(yaw);
				
				warps.put(name, l);
			}
			catch(Exception ex)
			{
				return;
			}
		}
	}
}