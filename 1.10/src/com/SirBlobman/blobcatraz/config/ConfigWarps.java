package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

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
		World w = l.getWorld();
		String worldName = w.getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float pitch = l.getPitch();
		float yaw = l.getYaw();
		File warpName = new File(warpsFolder, File.separator + name + ".yml");
		if(!warpName.exists())
		{
			try{warpName.createNewFile();}catch(Exception ex) {Util.print("Failed to create warp " + warpName + ": " + ex.getMessage());}
		}
		try
		{
			warp.load(warpName);
			warp.set("name", name);
			warp.set("world", worldName);
			warp.set("x", x);
			warp.set("y", y);
			warp.set("z", z);
			warp.set("pitch", pitch);
			warp.set("yaw", yaw);
			warp.save(warpName);
		}
		catch(Exception ex)
		{
			Util.print("Failed to save warp " + warpName + ": " + ex.getMessage());
		}
	}
	
	public static void loadWarps()
	{
		warpsFolder.mkdir();
		warpFiles = warpsFolder.listFiles();
		if(warpFiles == null || warpFiles.length == 0)
		{
			Util.print("There are no warps to load");
			return;
		}
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
	
	public static Location getWarp(String warpName)
	{
		loadWarps();
		if(warps.containsKey(warpName))
		{
			return warps.get(warpName);
		}
		return null;
	}
	
	public static void teleportToWarp(Entity e, String warpName)
	{
		Location l = getWarp(warpName);
		if(l != null)
		{
			e.teleport(l);
		}
	}
	
	public static boolean doesWarpExist(String warpName)
	{
		loadWarps();
		return warps.containsKey(warpName);
	}
	
	public static List<String> getWarps()
	{
		loadWarps();
		List<String> warpList = new ArrayList<String>();
		for(String warp : warps.keySet())
		{
			warpList.add(warp);
		}
		Collections.sort(warpList);
		
		return warpList;
	}
	
	public static void deleteWarp(String warpName)
	{
		loadWarps();
		if(warps.containsKey(warpName))
		{
			warps.remove(warpName);
			File warp = new File(warpsFolder, warpName + ".yml");
			warp.delete();
		}
	}
}