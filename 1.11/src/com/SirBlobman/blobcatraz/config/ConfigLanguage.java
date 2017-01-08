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

		set("command.blobcatraz.enchant.success", "Successfully enchanted your &6%s&r with &c%s (Level %s)", false);
		set("command.setmotd.success", "The Server's MOTD was changed to:\n%s", false);
		
		set("command.item.recieve item", "You were given &6%s&r of &b%s&r", false);
		set("command.item.invalid item", "&5%s&r is not a valid Item", false);
		set("command.item.invalid meta", "&5%s&r must be between 1 and 32767", false);
		set("command.item.invalid amount", "&5%s&r is not a valid amount!", false);
		
		set("command.editing.air", "AIR cannot be edited!", false);
		set("command.editing.rename", "Renamed your &6%s&r to &b%s", false);

		set("command.afk.not", "&6&l* &7%s is no longer AFK", false);
		
		set("command.balance.self", "You have &2%s&r", false);
		set("command.balance.other", "&3%s&r has &2%s&r", false);
		
		set("command.nickname.success", "Your name is now %s\nPlease logout to see the change", false);
		set("command.nickname.other", "You changed &5%s's nickname to %s\nThey must logout to see the change!", false);
		
		set("command.gamemode.1", "Your game mode is now &e%s&r", false);
		set("command.gamemode.sender", "%s&r is now in &e%s&r mode", false);
		set("command.gamemode.target", "Your game mode was changed to &e%s", false);
		
		set("player.join", "{username} joined the game! :)", false);
		set("player.quit", "{displayname} left us! :(", false);
		
		set("error.target.does not exist", "&5%s&r does not exist!", false);
		set("error.gamemode.does not exist", "&3%s&r is not a valid GameMode!", false);
		set("error.number.parse", "&a%s&r is not a Number!", false);
		set("error.enchant.invalid", "&a%s&r is not a valid custom enchantment!", false);
		
		save();
	}
	
	public static void set(String path, String msg, boolean force)
	{
		boolean b1 = (config.get(path) == null);
		if(b1 || force) config.set(path, msg);
		if(force) save();
	}
}