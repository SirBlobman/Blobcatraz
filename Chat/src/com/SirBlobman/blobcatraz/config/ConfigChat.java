package com.SirBlobman.blobcatraz.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigChat
{
	private static File folder = Blobcatraz.folder;
	private static File file = new File(folder, "chat.yml");
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
		set("default format", "%displayname% &7>> %message%", false);
		set("emojis", true, false);
		set("ping", true, false);
		set("disabled", false, false);
		
		save();
	}
	
	public static void set(String p, Object o, boolean force)
	{
		boolean b1 = (config.get(p) == null);
		if(b1 || force) config.set(p, o);
		if(force) save();
	}
}