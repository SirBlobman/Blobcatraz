package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

public class ConfigBlobcatraz
{
	private static File file = new File(Blobcatraz.folder, "blobcatraz.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public static YamlConfiguration load()
	{
		if(!file.exists()) save();
		try{config.load(file); writeDefaults();} catch(Exception ex)
		{
			Util.print("Failed to load " + file + ": " + ex.getCause());
		}
		return config;
	}
	
	public static void save()
	{
		if(!file.exists())
		{
			try{file.createNewFile(); writeDefaults();} catch(Exception ex)
			{
				Util.print("Failed to create " + file + ": " + ex.getCause());
				return;
			}
		}
		try{config.save(file);} catch(Exception ex)
		{
			Util.print("Failed to save " + file + ": " + ex.getCause());
		}
	}
	
	public static void reload()
	{
		load();
		save();
	}
	
	public static void writeDefaults()
	{
		set("owner", "5ba03c6c-ad4c-4475-8ec9-8bc8a15a9ebe"); //Set SirBlobman as the server owner (default)
		set("custom items", true);
		set("custom enchants", true);
		set("unlimited players", true);
		set("auto pickup", true);
		set("block protection", true);
		
		set("invincible slimes.enabled", true);
		set("invincible slimes.max size", 20);
		
		set("giant loot.enabled", true);
		set("giant loot.prize", Material.GOLDEN_APPLE.name());
		set("giant loot.meta", 1);
		set("giant loot.amount", 64);
		
		set("combat log.enabled", true);
		set("combat log.seconds", 30);
		
		set("chat.emojis", true);
		set("chat.ping", true);
		set("chat.disabled", false);
		
		List<String> antiNetherPortalWorlds = Arrays.asList("WoRlD");
		set("portals.enabled", true);
		set("portals.no other portal worlds", antiNetherPortalWorlds);
		
		List<String> randomTPWorlds = Arrays.asList("world", "world_nether");
		set("randomTP.enabled worlds", randomTPWorlds);
		set("randomTP.max tiny distance", 1000);
		set("randomTP.max normal distance", 3000);
		set("randomTP.max far distance", 6000);
		
		List<String> mergeMobs = Arrays.asList(EntityType.ZOMBIE.name(), EntityType.SKELETON.name());
		List<String> mergeMobsDisabledWorlds = Arrays.asList("WoRlD");
		set("mob merge.enabled", true);
		set("mob merge.mobs", mergeMobs);
		set("mob merge.disabled worlds", mergeMobsDisabledWorlds);
		set("mob merge.radius", 5);
		set("mob merge.color", ChatColor.DARK_BLUE.name());
		set("mob merge.limit", 100);
		
		List<String> disabledCommands = Arrays.asList("/login", "/register", "/changepassword");
		set("command spy.enabled", true);
		set("command spy.ignored commands", disabledCommands);
		
		List<String> effects = Arrays.asList(PotionEffectType.SPEED.getName(), PotionEffectType.JUMP.getName());
		List<String> hubs = Arrays.asList("Hub", "Lobby");
		set("hub effects.enabled", true);
		set("hub effects.enabled worlds", hubs);
		set("hub effects.effects", effects);
		
		List<String> voteLinks = Arrays.asList("1", "2", "3", "4", "5", "6");
		set("vote links", voteLinks);
		
		save();
	}
	
	private static void set(String key, Object value)
	{
		if(config.get(key) != null) return;
		config.set(key, value);
	}
}