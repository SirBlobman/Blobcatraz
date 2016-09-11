package com.SirBlobman.blobcatraz.utility;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigEmojis;
import com.SirBlobman.blobcatraz.config.ConfigLanguage;
import com.google.common.collect.Maps;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

/**
 * Utility class for Blobcatraz
 * <br>Contains many useful utilities that prevent code duplicating
 * @author SirBlobman
 */
public class Util
{
	private static Blobcatraz plugin = Blobcatraz.instance;
	private static Server S = Bukkit.getServer();
	private static PluginManager PM = S.getPluginManager();
	
	public static final String blobcatraz = ConfigLanguage.getMessage("blobcatraz.prefix");
	public static final String unformatted = uncolor(blobcatraz);
	public static final String enabled = message("blobcatraz.enabled");
	public static final String disabled = message("blobcatraz.disabled");
	public static final String NEA = blobcatraz + message("command.notEnoughArguments");
	public static final String TMA = blobcatraz + message("command.tooManyArguments");
	public static final String IA = blobcatraz + message("command.invalidArguments");
	public static final String noPerms(String perm) {return blobcatraz + message("command.noPermission", perm);}
	public static final String csNotPlayer = blobcatraz + message("command.error.nonPlayer");
	public static final String csNotLiving = blobcatraz + message("command.error.nonLiving");
	public static final String notInThisWorld = blobcatraz + message("command.error.worldNotEnabled");
	public static final String banned = blobcatraz + message("player.banned");
	
	/**
	 * This gets a message from the config
	 * @param key Key for the message <dl><dd><b>Example:</b> "blobcatraz.prefix"</dl>
	 * @param format (optional) format for the message
	 * @return formatted message. <br><b>Example:</b> [Blobcatraz]
	 */
	public static String message(String key, Object... format) 
	{
		String msg = ConfigLanguage.getMessage(key, format);
		return msg;
	}
	
	/**
	 * This turns a message to color
	 * @param msg Message to color <dl><dd><b>Example:</b> "&4Hello"</dl>
	 * @return Colored message <br><b>Example:</b> <font color=#ff0000> Hello </font>
	 * @see ChatColor#translateAlternateColorCodes(char, String)
	 */
	public static String color(String msg)
	{
		String colored = ChatColor.translateAlternateColorCodes('&', msg);
		return colored;
	}
	
	/**
	 * This removes ALL color from a message
	 * @param msg Message to uncolor <dl><dd><b>Example:</b> <font color=#ff0000> Hello </font></dl>
	 * @return Uncolored message <br><b>Example:</b> Hello
	 * @see ChatColor#stripColor(String)
	 */
	public static String uncolor(String msg)
	{
		String uncolored = ChatColor.stripColor(msg);
		return uncolored;
	}
	
	/**
	 * This replaces all valid characters with an emoji
	 * @param msg Message to symbolize
	 * @return String with emojis. <dl><dd><b>Example:</b> :) = <font size=5>&#9786;</font>
	 */
	public static String symbolize(String msg)
	{
		for(Entry<String, Character> emoji : ConfigEmojis.getEmojis().entrySet())
		{
			msg = msg.replace(emoji.getKey(), ConfigEmojis.getEmoji(emoji.getValue()));
		}
		return msg;
	}
	
	/**
	 * colors and symbolizes a message
	 * @param msg Message to format
	 * @return Formatted message
	 * @see #color(String)
	 * @see #symbolize(String)
	 */
	public static String format(String msg)
	{
		msg = symbolize(msg);
		msg = color(msg);
		msg = msg.replace("/n", "\n");
		return msg;
	}
	
	/**
	 * Gets the a string from a set of arguments
	 * @param args String[] of arguments
	 * @param start argument to start at
	 * @return Single string 
	 */
	public static String getFinal(String[] args, int start)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = start; i < args.length; i++)
		{
			if(i != start) sb.append(" ");
			sb.append(args[i]);
		}
		return sb.toString();
	}
	
	/**
	 * Gets a list of strings that start with another one
	 * @param original Original list to check through
	 * @param arg String to match with
	 * @return List of strings from the original list that start with the argument
	 * @see String#startsWith(String)
	 */
	public static List<String> getMatchingStrings(List<String> original, String arg)
	{
		if(original == null || arg == null) return null;
		List<String> matching = new ArrayList<String>();
		for(String s : original)
		{
			if(s.toLowerCase().startsWith(arg.toLowerCase())) matching.add(s);
		}
		return matching;
	}
	
	/**
	 * Prints a message to the {@link System#out} logger
	 * @param msg Message to print
	 */
	public static void print(String msg)
	{
		String print = unformatted + msg;
		System.out.println(print);
	}
	
	/**
	 * Broadcasts a message to every player on the server
	 * @param msg Message to broadcast
	 * @see Server#broadcastMessage(String)
	 */
	public static void broadcast(String msg)
	{
		String broadcast = blobcatraz + format(msg);
		S.broadcastMessage(broadcast);
	}
	
	/**
	 * Plays the sonic screwdriver sound to a Player
	 * <br>Only works if that player has the proper texture pack
	 * @param p Player to play the sound to
	 * @see Player#playSound(Location, String, float, float)
	 */
	public static void playSonic(Player p)
	{
		if(p == null) return;
		Location location = p.getLocation();
		String sound = "sonic-screwdriver";
		float volume = 100.0F;
		float pitch = 1.0F;
		p.playSound(location, sound, volume, pitch);
	}
	
	/**
	 * Register a listener for events
	 * @param listeners Listeners to register
	 * @see PluginManager#registerEvents(Listener, org.bukkit.plugin.Plugin)
	 */
	public static void regEvents(Listener... listeners)
	{
		if(listeners == null) return;
		if(listeners.length == 0) return;
		for(Listener l : listeners) PM.registerEvents(l, plugin);
	}
	
	/**
	 * @param p Player to get the message for
	 * @return Join message for the player
	 * @see ConfigLanguage#getMessage(String, Object...)
	 */
	public static String getJoinMessage(Player p)
	{
		ConfigLanguage.load();
		String msg = ConfigLanguage.getMessage("player.join");
		msg = msg.replace("{USERNAME}", p.getName());
		msg = msg.replace("{DISPLAYNAME}", p.getDisplayName());
		return color(msg);
	}

	/**
	 * @param p Player to get the message for
	 * @return Quit message for the player
	 * @see ConfigLanguage#getMessage(String, Object...)
	 */
	public static String getQuitMessage(Player p)
	{
		ConfigLanguage.load();
		String msg = ConfigLanguage.getMessage("player.quit");
		msg = msg.replace("{USERNAME}", p.getName());
		msg = msg.replace("{DISPLAYNAME}", p.getDisplayName());
		return color(msg);
	}
	
	private static TreeMap<Integer, String> getRomans()
	{
		final TreeMap<Integer, String> map = Maps.newTreeMap();
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
	
	/**
	 * Turns an integer into a roman numeral
	 * @param number Integer to convert
	 * @return Roman Numeral. <br><b>Example:</b> {@code 4 = "IV"}
	 */
	public static String numberToRoman(int number)
	{
		final TreeMap<Integer, String> roman = getRomans();
		int l = roman.floorKey(number);
		if(number == l) return roman.get(number);
		return roman.get(l) + numberToRoman(number - 1);
	}
	
	/**
	 * Turns a double into a money string
	 * @param amount amount to turn into $
	 * @return currency value. <br><b>Example:</b> {@code 4.0599999 = "$4.06"}
	 */
	public static String monetize(double amount)
	{
		NumberFormat money = NumberFormat.getCurrencyInstance();
		String currency = money.format(amount);
		return currency;
	}
	
	public static ConsoleCommandSender getConsole()
	{
		return S.getConsoleSender();
	}
}