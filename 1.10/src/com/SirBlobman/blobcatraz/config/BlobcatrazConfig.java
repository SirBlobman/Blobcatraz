package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class BlobcatrazConfig 
{
	private static File configFile = new File(Blobcatraz.instance.getDataFolder(), "blobcatraz.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
	
	public static void saveConfig()
	{
		if(!configFile.exists())
		{
			try 
			{
				configFile.createNewFile();
			}
			catch (Exception ex) 
			{
				Util.print("Failed to create 'blobcatraz.yml'. This plugin will fail to work properly");
				ex.printStackTrace();
				return;
			}
		}
		
		try
		{
			config.save(configFile);
		}
		catch (Exception ex)
		{
			Util.print("Failed to save 'blobcatraz.yml'. This plugin will fail to work properly");
			return;
		}
	}
	
	public static void writeDefaults()
	{
		if(!configFile.exists())
		{
			try 
			{
				configFile.createNewFile();
			}
			catch (Exception ex) 
			{
				Util.print("Failed to create 'blobcatraz.yml'. This plugin will fail to work properly");
				ex.printStackTrace();
				return;
			}
		}
		
		config.set("protection.preventPrisonEscape", true);
		config.set("chat.disabled", false);
		config.set("chat.ping", true);
		config.set("chat.emojis", true);
		config.set("random.invincibleSlimes", false);
		config.set("random.giantDropsPrize", true);
		config.set("random.customItems", true);
		config.set("random.customEnchants", true);
		config.set("random.portals", true);
		
		List<String> randomTPWorlds = Arrays.asList("world", "world_nether", "world_the_end");
		config.set("randomtp.enabledWorlds", randomTPWorlds);
		config.set("randomtp.maxTinyDistance", 1000);
		config.set("randomtp.maxNormalDistance", 3000);
		config.set("randomtp.maxFarDistance", 6000);
		
		List<String> voteLinks = Arrays.asList("Link 1", "Link 2", "Link 3");
		config.set("vote.links", voteLinks);
		
		config.set("motd", Util.blobcatraz +  "This is the default MOTD");
		
		try
		{
			config.save(configFile);
		}
		catch (Exception ex)
		{
			Util.print("Failed to save 'blobcatraz.yml'. This plugin will fail to work properly");
			return;
		}
		
	}
	
	public static void loadConfig()
	{
		if(!configFile.exists())
		{
			writeDefaults();
		}
		
		try
		{
			config.load(configFile);
		}
		catch (Exception ex)
		{
			Util.print("Failed to load 'blobcatraz.yml'. This plugin will fail to work properly");
			return;
		}
		
		if(config.get("chat.disabled") == null)
		{
			writeDefaults();
		}
	}
	
	public static void reloadConfig()
	{
		loadConfig();
		saveConfig();
	}
}