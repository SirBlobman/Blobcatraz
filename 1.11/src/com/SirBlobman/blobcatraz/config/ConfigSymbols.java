package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;
import com.google.common.collect.Maps;

public class ConfigSymbols
{
	private static File file = new File(Blobcatraz.folder, "symbols.yml");
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
			try{file.createNewFile(); defaults();}
			catch(Exception ex)
			{
				Util.print("Failed to create " + file + ": " + ex.getMessage());
				return;
			}
		}
		try{config.save(file);}
		catch(Exception ex) {Util.print("Failed to save " + file + ": " + ex.getMessage()); return;}
	}
	
	private static void defaults()
	{
		set(":)", '\u263a', false);
		set(":(", '\u2639', false);
		set("<3", '\u2665', false);
		set("(o)", '\u00b0', false);
		set("(c)", '\u00a9', false);
		set("(r)", '\u00ae', false);
		set("(tm)", '\u2122', false);
		set("n~", '\u00f1', false);
		set("N~", '\u00d1', false);
		set("~n", '\u00f1', false);
		set("~N", '\u00d1', false);
		set("(phone)", '\u260e', false);
		set("(male)", '\u2642', false);
		set("(female)", '\u2640', false);
		set("(skull)", '\u2620', false);
		set("(cent)", '\u00a2', false);
		set("(?!)", '\u2048', false);
		set("(X)", '\u2593', false);
		set(">>", '\u00bb', false);
		set("<<", '\u00ab', false);
		save();
	}
	
	private static void set(String o, char e, boolean force)
	{
		boolean b1 = (config.get(o) == null);
		if(b1 || force) config.set(o, e);
	}
	
	public static Map<String, Character> symbols()
	{
		load();
		TreeMap<String, Character> map = Maps.newTreeMap();
		Set<String> keys = config.getKeys(false);
		if(keys == null) defaults();
		for(String s : keys)
		{
			String e = config.getString(s);
			char c = e.toCharArray()[0];
			map.put(s, c);
		}
		return map;
	}
	
	public static String CtoS(char c)
	{
		String s = Character.toString(c);
		return s;
	}
}