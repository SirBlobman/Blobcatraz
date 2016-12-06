package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WarpComparator;

public class ConfigWarps
{
	private static File folder = new File(Blobcatraz.folder, "warps");
	
	public static YamlConfiguration load(String name)
	{
		if(name == null) return null;
		String f = name + ".warp";
		File file = new File(folder, f);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if(!file.exists()) {Util.print(Util.option("error.warp.does not exist")); return null;}
		
		try{config.load(file); return config;}
		catch(Exception ex) {Util.print("Error loading " + file + ": " + ex.getMessage()); return null;}
	}
	
	public static void save(String name, Location l, ItemStack is)
	{
		if(name == null || l == null || is == null) return;
		String f = name + ".warp";
		File file = new File(folder, f);
		if(!file.exists())
		{
			try
			{
				folder.mkdirs();
				file.createNewFile();
			} catch(Exception ex)
			{
				Util.print("Failed to create warp " + file + ": " + ex.getMessage());
				return;
			}
		}
		
		try
		{
			World w = l.getWorld();
			String world = w.getName();
			double x = l.getX();
			double y = l.getY();
			double z = l.getZ();
			float pitch = l.getPitch();
			float yaw = l.getYaw();
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.load(file);
			config.set("name", name);
			config.set("icon", is);
			config.set("world", world);
			config.set("x", x);
			config.set("y", y);
			config.set("z", z);
			config.set("pitch", pitch);
			config.set("yaw", yaw);
			config.save(file);
		} catch(Exception ex)
		{
			Util.print("Failed to save " + file + ": " + ex.getMessage());
			return;
		}
	}
	
	public static List<String> swarps()
	{
		if(!folder.exists()) folder.mkdirs();
		File[] files = folder.listFiles();
		List<String> list = new ArrayList<String>();
		for(File file : files)
		{
			String name = file.getName();
			String end = ".warp";
			if(name.endsWith(end))
			{
				String f = FilenameUtils.getBaseName(name);
				YamlConfiguration config = load(f);
				String name2 = config.getString("name");
				list.add(name2);
			}
		}
		
		Collections.sort(list);
		return list;
	}
	
	public static List<Warp> warps()
	{
		List<Warp> list = new ArrayList<Warp>();
		for(String s : swarps())
		{
			ItemStack is = icon(s);
			Location warp = warp(s);
			Warp w = new Warp(s, is, warp);
			list.add(w);
		}
		list.sort(new WarpComparator());
		return list;
	}
	
	public static boolean exists(String name)
	{
		List<String> list = swarps();
		return list.contains(name);
	}
	
	public static ItemStack icon(String name)
	{
		if(name == null) return null;
		if(exists(name))
		{
			YamlConfiguration config = load(name);
			ItemStack is = config.getItemStack("icon");
			return is;
		}
		return null;
	}
	
	public static Location warp(String name)
	{
		if(name == null) return null;
		if(exists(name))
		{
			Location warp = new Location(null, 0, 0, 0);
			
			YamlConfiguration config = load(name);
			String world = config.getString("world");
			World w = Bukkit.getWorld(world);
			double x = config.getDouble("x");
			double y = config.getDouble("y");
			double z = config.getDouble("z");
			float pitch = (float) config.getDouble("pitch");
			float yaw = (float) config.getDouble("yaw");
			
			warp.setWorld(w);
			warp.setX(x);
			warp.setY(y);
			warp.setZ(z);
			warp.setPitch(pitch);
			warp.setYaw(yaw);
			return warp;
		}
		return null;
	}
	
	public static void delete(String name)
	{
		String f = name + ".warp";
		File file = new File(folder, f);
		file.delete();
	}
}