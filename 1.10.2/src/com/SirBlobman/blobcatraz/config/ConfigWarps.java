package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigWarps
{
	private static File folder = new File(Blobcatraz.folder, "warps");
	
	public static YamlConfiguration load(String name)
	{
		if(name == null) return null;
		File warp = new File(folder, name + ".warp");
		YamlConfiguration yc = YamlConfiguration.loadConfiguration(warp);
		if(!warp.exists()) {Util.print("You can't load a warp that doesn't exist!"); return null;}
		try{yc.load(warp);}
		catch(Exception ex) {Util.print("Error loading warp " + warp + ": " + ex.getCause());}
		return yc;
	}
	
	public static void save(String name, Location l, ItemStack icon)
	{
		if(name == null || l == null || icon == null) return;
		if(!folder.exists()) folder.mkdir();
		
		World w = l.getWorld();
		String world = w.getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float pitch = l.getPitch();
		float yaw = l.getYaw();
		
		File warp = new File(folder, name + ".warp");
		if(!warp.exists())
		{
			try{warp.createNewFile();}
			catch(Exception ex)
			{
				Util.print("Failed to create warp " + warp + ": " + ex.getCause());
				return;
			}
		}
		
		try
		{
			YamlConfiguration yc = YamlConfiguration.loadConfiguration(warp);
			yc.load(warp);
			yc.set("name", name);
			yc.set("icon", icon);
			yc.set("world", world);
			yc.set("x", x);
			yc.set("y", y);
			yc.set("z", z);
			yc.set("pitch", pitch);
			yc.set("yaw", yaw);
			yc.save(warp);
		} catch(Exception ex)
		{
			Util.print("Failed to save " + warp + ": " + ex.getCause());
			return;
		}
	}
	
	public static void reload()
	{
		for(String s : ConfigWarps.getStringWarps())
		{
			load(s);
			Location warp = getWarp(s);
			save(s, warp, getIcon(s));
		}
	}
	
	public static ItemStack getIcon(String name)
	{
		if(name == null) return null;
		if(exists(name))
		{
			YamlConfiguration yc = load(name);
			ItemStack icon = yc.getItemStack("icon");
			return icon;
		}
		return null;
	}
	
	public static Location getWarp(String name)
	{
		if(name == null) return null;
		if(exists(name))
		{
			Location warp = new Location(null, 0, 0, 0);
			
			YamlConfiguration yc = load(name);
			String w = yc.getString("world");
			World world = Bukkit.getWorld(w);
			double x = yc.getDouble("x");
			double y = yc.getDouble("y");
			double z = yc.getDouble("z");
			float pitch = (float) yc.getDouble("pitch");
			float yaw = (float) yc.getDouble("yaw");
			
			warp.setWorld(world);
			warp.setX(x);
			warp.setY(y);
			warp.setZ(z);
			warp.setPitch(pitch);
			warp.setYaw(yaw);
			
			return warp;
		}
		return null;
	}
	
	public static List<String> getStringWarps()
	{
		if(!folder.exists()) folder.mkdir();
		File[] files = folder.listFiles();
		List<String> warps = new ArrayList<String>();
		for(File file : files)
		{
			String end = ".warp";
			if(end.endsWith(".warp"))
			{
				String name = FilenameUtils.getBaseName(file.getName());
				YamlConfiguration warp = load(name);
				String name2 = warp.getString("name");
				warps.add(name2);
			}
		}
		Collections.sort(warps);
		return warps;
	}
	
	public static List<Warp> getWarps()
	{
		List<Warp> warps = new ArrayList<Warp>();
		for(String s : getStringWarps())
		{
			String name = s;
			ItemStack icon = getIcon(s);
			Location warp = getWarp(name);
			Warp w = new Warp(name, icon, warp);
			warps.add(w);
		}
		warps.sort(new Comparator<Warp>()
		{
			@Override
			public int compare(Warp w1, Warp w2)
			{
				String n1 = w1.getName();
				String n2 = w2.getName();
				return n1.compareToIgnoreCase(n2);
			}
		});
		return warps;
	}
	
	public static boolean exists(String name)
	{
		return getStringWarps().contains(name);
	}
	
	public static void delete(String name)
	{
		File warp = new File(folder, name + ".warp");
		warp.delete();
	}
}