package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class ConfigPortals 
{
	public static List<String> portals = new ArrayList<String>();
	public static List<UUID> inPortal = new ArrayList<UUID>();
	
	private static File portalFile = new File(Blobcatraz.instance.getDataFolder(), "portals.yml");
	public static FileConfiguration portalConfig = YamlConfiguration.loadConfiguration(portalFile);
	
	public static void savePortals()
	{
		if(!portalFile.exists())
		{
			try{portalFile.createNewFile();}
			catch(Exception ex)
			{
				Util.print("Could not create " + portalFile + ".");
				ex.printStackTrace();
				return;
			}
		}
		
		try{portalConfig.save(portalFile);}
		catch (Exception ex)
		{
			Util.print("Failed to save " + portalFile);
			ex.printStackTrace();
			return;
		}
	}
	
	public static void loadPortals()
	{
		savePortals();
		portals.clear();
		
		for(String portal : portalConfig.getKeys(false)) portals.add(portal);
	}
	
	public static void reloadPortals()
	{
		loadPortals();
		savePortals();
	}
	
	public static void saveAndLoadPortals()
	{
		savePortals();
		loadPortals();
	}
}