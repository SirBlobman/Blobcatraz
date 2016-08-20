package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class ConfigBlobcatraz
{
	private static File configFile = new File(Blobcatraz.instance.getDataFolder(), "blobcatraz.yml");
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
		try{config.load(configFile);}
		catch(Exception ex)
		{
			Util.print("Failed to load " + configFile + ". This plugin will fail to work properly");
			return;
		}	
		writeDefaults();
	}
	
	public static FileConfiguration getConfig()
	{
		loadConfig();
		return config;
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
		
		set("protection.preventPrisonEscape", true);
		
		set("chat.disabled", false);
		set("chat.ping", true);
		set("chat.emojis", true);
		
		set("random.owner", "5ba03c6c-ad4c-4475-8ec9-8bc8a15a9ebe");
		set("random.invincibleSlimes", true);
		set("random.giantDropsPrize.enabled", true);
		set("random.giantDropsPrize.prize", Material.GOLDEN_APPLE.toString());
		set("random.giantDropsPrize.meta", 1);
		set("random.giantDropsPrize.amount", 64);
		set("random.customItems", true);
		set("random.customEnchants", true);
		set("random.combatLog.enabled", true);
		set("random.combatLog.seconds", 30);
		set("random.unlimitedPlayers", true);
		set("random.autoPickup", true);
		
		List<String> portalDisabledWorlds = Arrays.asList("world");
		set("portals.enabled", true);
		set("portals.preventNetherEntryWorlds", portalDisabledWorlds);
		
		List<String> randomTPWorlds = Arrays.asList("world", "world_nether");
		set("randomtp.enabledWorlds", randomTPWorlds);
		set("randomtp.maxTinyDistance", 1000);
		set("randomtp.maxNormalDistance", 3000);
		set("randomtp.maxFarDistance", 6000);
		
		List<String> mobMergeDisableWorlds = Arrays.asList("WoRlD");
		List<String> mobMergeMobs = Arrays.asList(EntityType.ZOMBIE.toString(), EntityType.SKELETON.toString());
		set("mobmerge.enabled", true);
		set("mobmerge.mobs", mobMergeMobs);
		set("mobmerge.disabledWorlds", mobMergeDisableWorlds);
		set("mobmerge.radius", 5);
		set("mobmerge.period", 10);
		set("mobmerge.color", ChatColor.DARK_BLUE.name());
		set("mobmerge.limit", 100);
		
		List<String> disabledCommands = Arrays.asList("/login", "/register", "/changepassword");
		set("commandspy.enabled", true);
		set("commandspy.ignored commands", disabledCommands);
		
		List<String> voteLinks = Arrays.asList("planetminecraft.com", "topg.org", "serverpact.com");
		set("vote.links", voteLinks);
		
		try{config.save(configFile);}
		catch(Exception ex)
		{
			Util.print("Failed to save " + configFile + ". This plugin will fail to work properly");
			return;
		}
	}
	
	public static void reloadConfig() {loadConfig(); saveConfig();}
	
	private static void set(String path, Object value)
	{
		if(config.get(path) == null)
		{
			config.set(path, value);
		}
		saveConfig();
	}
}