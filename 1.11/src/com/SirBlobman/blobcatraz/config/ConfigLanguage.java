package com.SirBlobman.blobcatraz.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigLanguage
{
	private static File folder = Blobcatraz.folder;
	private static File file = new File(folder, "language.yml");
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
			catch(Exception ex) {Util.print("Failed to create " + file + ": " + ex.getMessage()); return;}
		}
		try{config.save(file);}
		catch(Exception ex) {Util.print("Failed to save " + file + ": " + ex.getMessage()); return;}
	}
	
	private static void defaults()
	{
		set("prefix", "&8{&3Blobcatraz&8}&f ", false);
		set("enable", "&2Enabled", false);
		set("disable", "&4Disabled", false);
		
		set("command.invalid arguments", "&4Invalid Arguments!", false);
		set("command.not enough arguments", "&4Not Enough Arguments!", false);
		set("command.too many arguments", "&4Too Many Arguments!", false);
		set("command.no permission", "&4No Permission: &3%s", false);
		set("command.no permission1", "&4No Permission!", false);
		set("command.no permission2", "&3%s", false);
		save();
	}
	
	public static void set(String path, String msg, boolean force)
	{
		boolean b1 = (config.get(path) == null);
		if(b1 || force) config.set(path, msg);
	}
}