package com.SirBlobman.blobcatraz;

import java.io.File;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.SirBlobman.blobcatraz.command.CommandAFK;
import com.SirBlobman.blobcatraz.command.CommandBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigLanguage;
import com.SirBlobman.blobcatraz.utility.Util;

/**
 * Main class for this plugin
 * @author SirBlobman
 */
public class Blobcatraz extends JavaPlugin
{
	/**
	 * Instance of Blobcatraz
	 * Do not use before it is enabled
	 */
	public static Blobcatraz instance;
	public static YamlConfiguration config;
	public static File folder;
	
	@Override
	public void onEnable()
	{
		instance = this;
		folder = getDataFolder();
		configs();
		commands();
		Util.broadcast(Util.enable);
	}
	
	@Override
	public void onDisable()
	{
		Util.broadcast(Util.disable);
	}
	
	private void configs()
	{
		ConfigLanguage.load();
		ConfigBlobcatraz.load();
	}
	
	private void commands()
	{
		c("blobcatraz", new CommandBlobcatraz(), new CommandBlobcatraz());
		c("afk", new CommandAFK(), null);
	}
	
	private void c(String cmd, CommandExecutor ce, TabCompleter tc)
	{
		PluginCommand pc = getCommand(cmd);
		pc.setExecutor(ce);
		if(tc != null) pc.setTabCompleter(tc);
	}
}