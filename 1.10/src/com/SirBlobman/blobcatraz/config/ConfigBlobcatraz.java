package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.*;

public class ConfigBlobcatraz 
{
	private static File configFile = new File(com.SirBlobman.blobcatraz.Blobcatraz.instance.getDataFolder(), "blobcatraz.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
	
	public static void saveConfig()
	{
		if(!configFile.exists())
		{
			try{configFile.createNewFile();}
			catch (Exception ex)
			{
				Util.print("Failed to create " + configFile + ". This plugin will fail to work properly");
				ex.printStackTrace();
				return;
			}
		}
		
		try{config.save(configFile);}
		catch (Exception ex)
		{
			Util.print("Failed to save " + configFile + ". This plugin will fail to work properly");
			return;
		}
	}
	
	public static void loadConfig()
	{
		if(!configFile.exists()) writeDefaults();
		
		try{config.load(configFile);}
		catch(Exception ex)
		{
			Util.print("Failed to load " + configFile + ". This plugin will fail to work properly");
			return;
		}
		
		if(config.get("chat.disabled") == null) writeDefaults();
	}
	
	public static void writeDefaults()
	{
		if(!configFile.exists())
		{
			try{configFile.createNewFile();}
			catch(Exception ex)
			{
				Util.print("Failed to create " + configFile + ". This plugin will fail to work properly");
				ex.printStackTrace();
				return;
			}
		}
		
		config.set("protection.preventPrisonEscape", true);
		
		config.set("chat.disabled", false);
		config.set("chat.ping", true);
		config.set("chat.emojis", true);
		
		config.set("random.invincibleSlimes", true);
		config.set("random.giantDropsPrize.enabled", true);
		config.set("random.giantDropsPrize.prize", Material.GOLDEN_APPLE.toString());
		config.set("random.giantDropsPrize.meta", 1);
		config.set("random.giantDropsPrize.amount", 64);
		config.set("random.customItems", true);
		config.set("random.customEnchants", true);
		config.set("random.portals", true);
		config.set("random.combatLog.enabled", true);
		config.set("random.combatLog.seconds", 30);
		config.set("random.unlimitedPlayers", true);
		
		List<String> randomTPWorlds = Arrays.asList("world", "world_nether");
		config.set("randomtp.enabledWorlds", randomTPWorlds);
		config.set("randomtp.maxTinyDistance", 1000);
		config.set("randomtp.maxNormalDistance", 3000);
		config.set("randomtp.maxFarDistance", 6000);
		
		List<String> voteLinks = Arrays.asList("planetminecraft.com", "topg.org", "serverpact.com");
		config.set("vote.links", voteLinks);
		
		config.set("motd", Util.blobcatrazUnformatted + "Default MOTD");
		
		try{config.save(configFile);}
		catch(Exception ex)
		{
			Util.print("Failed to save " + configFile + ". This plugin will fail to work properly");
			return;
		}
	}
	
	public static void reloadConfig() {loadConfig(); saveConfig();}
}