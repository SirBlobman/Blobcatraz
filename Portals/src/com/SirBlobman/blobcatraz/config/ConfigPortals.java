package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigPortals
{
	private static File folder = Blobcatraz.folder;
	private static File file = new File(folder, "portals.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public static YamlConfiguration load()
	{
		if(!file.exists()) save();
		try
		{
			config.load(file);
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
			try
			{
				folder.mkdirs();
				file.createNewFile();
			} catch(Exception ex)
			{
				Util.print("Failed to create " + file + ": " + ex.getMessage());
				return;
			}
		}
		
		try{config.save(file);}
		catch(Exception ex)
		{
			Util.print("Failed to save portals: " + ex.getMessage());
			return;
		}
	}
	
	public static List<String> portals()
	{
		load();
		List<String> list = new ArrayList<String>();
		Set<String> portals = config.getKeys(false);
		for(String s : portals) list.add(s);
		return list;
	}
}