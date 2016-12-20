package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigBlobcatraz
{
	private static File folder = Blobcatraz.folder;
	private static File file = new File(folder, "blobcatraz.yml");
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
		set("owner", "5ba03c6c-ad4c-4475-8ec9-8bc8a15a9ebe", false);
		set("custom items", true, false);
		set("unlimited players", true, false);
		set("auto pickup", true, false);
		set("block protection", true, false);
		
		set("invincible slimes.enable", true, false);
		set("invincible slimes.max size", 20, false);
		
		set("giant loot.enable", true, false);
		set("giant loot.prize.item", "GOLDEN_APPLE", false);
		set("giant loot.prize.meta", 1, false);
		set("giant loot.prize.amount", 64, false);
		
		set("combat log.enable", true, false);
		set("combat log.time", 30, false);
		
		List<String> portalWorlds = Arrays.asList("WoRlD", "Hub");
		set("portals.enable", true, false);
		set("portals.only blobcatraz portals", portalWorlds, false);
		
		List<String> randomTPWorlds = Arrays.asList("world", "world_nether");
		set("random tp.worlds", randomTPWorlds, false);
		set("random tp.max distance.tiny", 1000, false);
		set("random tp.max distance.normal", 3000, false);
		set("random tp.max distance.far", 6000, false);
		
		List<String> merge = Arrays.asList("ZOMBIE", "SKELETON");
		List<String> mergeWorlds = Arrays.asList("WoRlD");
		set("mob merge.enable", true, false);
		set("mob merge.mobs", merge, false);
		set("mob merge.radius", 5, false);
		set("mob merge.period", 5, false);
		set("mob merge.color", "DARK_BLUE", false);
		set("mob merge.limit", 100, false);
		set("mob merge.disabled worlds", mergeWorlds, false);
		
		List<String> commands = Arrays.asList("login", "register", "changepassword");
		set("command spy.enable", true, false);
		set("command spy.ignored commands", commands, false);
		
		List<String> effects = Arrays.asList("SPEED", "JUMP");
		List<String> hubList = Arrays.asList("Hub", "Lobby");
		set("hub effects.enable", true, false);
		set("hub effects.enabled worlds", hubList, false);
		set("hub effects.effects", effects, false);
		
		List<String> tntWorlds = Arrays.asList("Hub", "Lobby");
		set("tnt.disabled worlds", tntWorlds, false);
		
		List<String> voteLinks = Arrays.asList("1", "2", "3", "4", "5", "6");
		set("vote links", voteLinks, false);
		save();
	}
	
	private static void set(String path, Object o, boolean force)
	{
		boolean b1 = (config.get(path) == null);
		if(b1 || force) config.set(path, o);
	}
}