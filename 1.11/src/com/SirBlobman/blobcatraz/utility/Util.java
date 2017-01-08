package com.SirBlobman.blobcatraz.utility;

import java.io.PrintStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import com.SirBlobman.blobcatraz.Blobcatraz;

/**
 * Default Utility class for Blobcatraz
 * Has a lot of useful stuff
 * @author SirBlobman
 */
public class Util
{
	private static final Blobcatraz PLUGIN = Blobcatraz.instance;
	private static final Server SERVER = Bukkit.getServer();
	private static final PluginManager PLUGIN_MANAGER = SERVER.getPluginManager();
	
	public static final String PREFIX = color("&3{&bBlobcatraz&3} &f");
	public static final String PREFIX2 = uncolor(PREFIX);
	
	/**
	 * Print a message to the console<br/>
	 * Colors will be removed
	 * @param msg Message to print
	 */
	public static void print(String msg)
	{
		String prt = uncolor(PREFIX2 + msg);
		PrintStream ps = System.out;
		ps.println(prt);
	}
	
	/**
	 * Broadcast a message<br/>
	 * The message will be seen by entities, players, and the console
	 * @param msg Message to broadcast
	 */
	public static void broadcast(String msg)
	{
		String bc = color(PREFIX + msg);
		SERVER.broadcastMessage(bc);
	}
	
	/**
	 * Add some chat colors to your message<br/>
	 * <b>&amp;</b> will be replaced with <b>&sect;</b>
	 * @param o original message
	 * @return <span style="color: pink">colored message</span>
	 */
	public static String color(String o)
	{
		String c = ChatColor.translateAlternateColorCodes('&', o);
		return c;
	}
	
	/**
	 * Remove all chat colors and formatting<br/>
	 * <b>&sect;</b> will be removed, along with the letter/number after it<br/>
	 * @param c <span style="color: pink">colored message</span>
	 * @return un-formatted message
	 */
	public static String uncolor(String c)
	{
		String o = ChatColor.stripColor(c);
		return o;
	}
	
	/**
	 * Register your {@link Listener} classes to bukkit
	 * @param listeners any amount of listeners you want to register
	 */
	public static void regEvents(Listener... listeners)
	{
		for(Listener l : listeners)
		{
			boolean b1 = (l != null);
			if(b1) PLUGIN_MANAGER.registerEvents(l, PLUGIN);
		}
	}
}