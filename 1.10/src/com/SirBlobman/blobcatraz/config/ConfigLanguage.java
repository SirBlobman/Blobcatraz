package com.SirBlobman.blobcatraz.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class ConfigLanguage 
{
	private static File file = new File(Blobcatraz.instance.getDataFolder(), "messages.yml");
	private static FileConfiguration messages = YamlConfiguration.loadConfiguration(file);
	
	public static FileConfiguration load()
	{
		if(!file.exists()) save();
		try
		{
			messages.load(file);
		} catch(Exception ex)
		{
			Util.print("Failed to load message! There may be errors: " + ex.getCause());
		}
		writeDefaults();
		return messages;
	}
	
	public static void save()
	{
		if(!file.exists())
		{
			Util.print(file + " doesn't exist! Attempting to create");
			try 
			{
				file.createNewFile();
			} catch (Exception ex) 
			{
				Util.print("Failed to create messages.yml. There may be errors: " + ex.getCause());
			}
		}
		try
		{
			messages.save(file);
		}
		catch(Exception ex)
		{
			Util.print("Failed to save messages.yml. There may be erros: " + ex.getCause());
			return;
		}
	}
	
	private static void writeDefaults()
	{
		set("prefix", "&8&l{&b&lBlobcatraz&8&l} &r");
		set("motd", "[Blobcatraz] Default MOTD");
		set("player.join", "&8&l[&a&l+&8&l]&e&l %username%&8&l [&a&l+&8&l]");
		set("player.quit", "&8&l[&4&l-&8&l]&e&l %username%&8&l [&4&l-&8&l]");
		set("title.noPermission", "&4&lNo Permission!");
		set("title.noPermission2", "&b%s");
		set("title.inventoryFull", "&b&l&m--&8&l&m[-<&f&lInventory Full&8&l&m>-]&b&l&m--");
		set("title.inventoryFull2", "&2You should sell some items");
		save();
	}
	
	private static void set(String path, Object value)
	{
		if(messages.get(path) == null) messages.set(path, value);
	}
	
	public static String getMessage(String key, Object... format)
	{
		String msg = Util.format(load().getString(key));
		if(msg == null) return "";
		if(format.length == 0) return msg;
		return String.format(msg, format);
	}
	
	public static void setMessage(String key, String msg)
	{
		load();
		messages.set(key, msg);
		save();
	}
}