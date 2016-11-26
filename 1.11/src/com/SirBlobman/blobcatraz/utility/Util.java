package com.SirBlobman.blobcatraz.utility;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigLanguage;
import com.SirBlobman.blobcatraz.config.ConfigSymbols;
import com.google.common.collect.Maps;

/**
 * Utility class for Blobcatraz<br/>
 * Contains many useful utilities that prevent code duplication
 * @author SirBlobman
 *
 */
public class Util
{
	protected static Blobcatraz plugin = Blobcatraz.instance;
	protected static final Server S = Bukkit.getServer();
	protected static final PluginManager PM = S.getPluginManager();
	protected static final YamlConfiguration CB = ConfigBlobcatraz.load();
	protected static final YamlConfiguration CL = ConfigLanguage.load();
	protected static final Random R = new Random();
	
	public static final String prefix = option("prefix");
	public static final String prefix2 = uncolor(prefix);
	public static final String enable = option("enable");
	public static final String disable = option("disable");
	
	public static final String IA = prefix + option("command.invalid arguments");
	public static final String NEA = prefix + option("command.not enough arguments");
	public static final String TMA = prefix + option("command.too many arguments");
	public static final String notPlayer = prefix + option("error.not player");
	
	public static final ItemStack AIR = new ItemStack(Material.AIR);
	
	/**
	 * Get a configured message
	 * @param key Path to the message
	 * @param format Formatting Objects
	 * @return Formatted message
	 */
	public static String option(String key, Object... format)
	{
		String o = CL.getString(key);
		if(o == null) o = key;
		String f = format(o, format);
		return f;
	}
	
	public static String format(String o, Object... format)
	{
		String c = color(o);
		String s = symbolize(c);
		String f = String.format(s, format);
		return f;
	}
	
	public static String color(String o)
	{
		String c = ChatColor.translateAlternateColorCodes('&', o);
		return c;
	}
	
	public static String uncolor(String c)
	{
		String o = ChatColor.stripColor(c);
		return o;
	}
	
	public static String symbolize(String o)
	{
		Map<String, Character> map = ConfigSymbols.symbols();
		for(Entry<String, Character> e : map.entrySet())
		{
			String s = e.getKey();
			char c = e.getValue();
			String c2 = ConfigSymbols.CtoS(c);
			o = o.replace(s, c2);
		}
		return o;
	}
	
	/**
	 * Show a message in console
	 * @param msg Message to show
	 * @see String
	 * @see java.io.PrintStream#println(String)
	 */
	public static void print(String msg)
	{
		String l = prefix2 + msg;
		System.out.println(l);
	}
	
	public static void broadcast(String msg)
	{
		String c = prefix + format(msg);
		S.broadcastMessage(c);
	}
	
	public static void regEvents(Listener... ls)
	{
		for(Listener l : ls) PM.registerEvents(l, plugin);
	}
	
	public static String getFinal(String[] args, int s)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = s; i < args.length; i++)
		{
			if(i != s) sb.append(" ");
			sb.append(args[i]);
		}
		return sb.toString();
	}
	
	public static List<String> getMatching(List<String> o, String arg)
	{
		if(o == null || arg == null) return null;
		List<String> match = new ArrayList<String>();
		for(String s : o)
		{
			String l = s.toLowerCase();
			String a = arg.toLowerCase();
			if(l.startsWith(a)) match.add(s); 
		}
		return match;
	}
	
	public static String json(String o)
	{
		String txt = "{\"text\": \"" + o + "\"}";
		return txt;
	}
	
	private static final TreeMap<Integer, String> romans()
	{
		TreeMap<Integer, String> map = Maps.newTreeMap();
		map.put(1000, "M");
		map.put(900, "CM");
		map.put(500, "D");
		map.put(400, "CD");
		map.put(100, "C");
		map.put(90, "XC");
		map.put(50, "L");
		map.put(40, "XL");
		map.put(10, "X");
		map.put(9, "IX");
		map.put(5, "V");
		map.put(4, "IV");
		map.put(1, "I");
		return map;
	}
	
	public static String numberToRoman(int number)
	{
		TreeMap<Integer, String> roman = romans();
		int n = roman.floorKey(number);
		if(number == n) return roman.get(number);
		return roman.get(n) + numberToRoman(number - 1);
	}
	
	public static String monetize(double d)
	{
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		String c = nf.format(d);
		return c;
	}
	
	public static List<Block> blocks(Location l1, Location l2)
	{
		List<Block> blocks = new ArrayList<Block>();
		int topX = (l1.getBlockX() < l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
		int botX = (l1.getBlockX() > l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
		int topY = (l1.getBlockY() < l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int botY = (l1.getBlockY() > l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int topZ = (l1.getBlockZ() < l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        int botZ = (l1.getBlockZ() > l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        for(int x = botX; x <= topX; x++)
        {
        	for(int z = botZ; z <= topZ; z++)
        	{
        		for(int y = botY; y <= topY; y++)
        		{
        			World w = l1.getWorld();
        			Block b = w.getBlockAt(x, y, z);
        			blocks.add(b);
        		}
        	}
        }
        return blocks;
	}
}