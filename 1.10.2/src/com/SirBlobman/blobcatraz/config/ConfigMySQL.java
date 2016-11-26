package com.SirBlobman.blobcatraz.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigMySQL
{
	private static final File file = new File(Blobcatraz.folder, "mysql.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public static YamlConfiguration load()
	{
		if(!file.exists()) save();
		try {config.load(file); return config;}
		catch(Exception ex)
		{
			Util.print("Failed to load database file!");
			Util.print(ex.getMessage());
			return null;
		}
	}
	
	public static void save()
	{
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
				defaults();
			} catch(Exception ex)
			{
				Util.print("Failed to create mysql.yml");
				return;
			}
		}
	}
	
	private static void defaults()
	{
		config.set("hostname", "localhost");
		config.set("port", 3306);
		config.set("database", "Blobcatraz");
		config.set("username", "user");
		config.set("password", "pass");
	}
}