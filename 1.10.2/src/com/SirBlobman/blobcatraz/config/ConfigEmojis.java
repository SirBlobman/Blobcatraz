package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;
import com.google.common.collect.Maps;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigEmojis 
{
	private static File file = new File(Blobcatraz.folder, "symbols.yml");
	private static YamlConfiguration symbols = YamlConfiguration.loadConfiguration(file);
	
	public static YamlConfiguration load()
	{
		if(!file.exists()) save();
		try{symbols.load(file);} catch(Exception ex)
		{
			Util.print("Failed to load " + file + ": " + ex.getCause());
			return null;
		}
		return symbols;
	}
	
	public static void save()
	{
		if(!file.exists())
		{
			try{file.createNewFile(); writeDefaults();} catch(Exception ex)
			{Util.print("Failed to create " + file + ": " + ex.getCause()); return;}
		}
		try
		{symbols.save(file);} catch(Exception ex) {Util.print("Failed to save " + file + ": " + ex.getCause());}
	}
	
	public static void reload()
	{
		load();
		save();
	}
	
	public static void writeDefaults()
	{
		set(":)", '\u263a');
		set(":(", '\u2639');
		set("<3", '\u2665');
		set("[phone]", '\u260e');
		set("(c)", '\u00a9');
		set("(r)", '\u00ae');
		set("(tm)", '\u2122');
		set("n~", '\u00f1');
		set("N~", '\u00d1');
		set("~n", '\u00f1');
		set("~N", '\u00d1');
		set("(o)", '\u00b0');
		set("[male]", '\u2642');
		set("[female]", '\u2640');
		set("[skull]", '\u2620');
		set("[cent]", '\u00a2');
		set("[?!]", '\u2048');
		set("[X]", '\u2593');
		set(">>", '\u00bb');
		set("<<", '\u00ab');
		save();
	}
	
	private static void set(String replace, char emoji)
	{
		if(symbols.get(replace) != null) return;
		symbols.set(replace, emoji);
	}
	
	public static Map<String, Character> getEmojis()
	{
		HashMap<String, Character> emojis = Maps.newHashMap();
		load();
		Set<String> emojis2 = symbols.getKeys(false);
		if(emojis2 == null) save();
		for(String s : emojis2)
		{
			String emoji = symbols.getString(s);
			char character = emoji.toCharArray()[0];
			emojis.put(s, character);
		}
		return emojis;
	}
	
	public static String getEmoji(char emoji)
	{
		return Character.toString(emoji);
	}
}