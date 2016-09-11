package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigPortals
{
	private static List<String> portals = new ArrayList<String>();
	private static List<UUID> in = new ArrayList<UUID>();
	
	private static File file = new File(Blobcatraz.folder, "portals.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public static YamlConfiguration load()
	{
		if(!file.exists()) save();
		try{config.load(file);}
		catch(Exception ex){Util.print("Failed to load " + file + ": " + ex.getCause());}
		return config;
	}
	
	public static void save()
	{
		if(!file.exists())
		{
			try{file.createNewFile();}
			catch(Exception ex) 
			{
				Util.print("Could not create " + file + ": " + ex.getCause()); 
				return;
			}
		}
		try{config.save(file);}
		catch(Exception ex)
		{
			Util.print("Failed to save portals!");
			return;
		}
	}
	
	public static void reload()
	{
		load();
		save();
	}
	
	public static List<String> getPortals()
	{
		YamlConfiguration yc = load();
		portals.clear();
		for(String p : yc.getKeys(false)) portals.add(p);
		return portals;
	}
	
	public static List<UUID> getPeopleInPortal()
	{
		return in;
	}
}