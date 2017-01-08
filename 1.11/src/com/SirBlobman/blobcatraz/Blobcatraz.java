package com.SirBlobman.blobcatraz;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.SirBlobman.blobcatraz.command.CommandAFK;
import com.SirBlobman.blobcatraz.command.CommandBlobcatraz;
import com.SirBlobman.blobcatraz.command.CommandFly;
import com.SirBlobman.blobcatraz.command.CommandGamemode;
import com.SirBlobman.blobcatraz.command.CommandHeal;
import com.SirBlobman.blobcatraz.command.CommandMOTD;
import com.SirBlobman.blobcatraz.command.CommandSpawner;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigLanguage;
import com.SirBlobman.blobcatraz.listener.ListenAFK;
import com.SirBlobman.blobcatraz.listener.ListenAntiTNT;
import com.SirBlobman.blobcatraz.listener.ListenAutoPickup;
import com.SirBlobman.blobcatraz.listener.ListenFreeze;
import com.SirBlobman.blobcatraz.listener.ListenGiantLoot;
import com.SirBlobman.blobcatraz.listener.ListenHubEffects;
import com.SirBlobman.blobcatraz.listener.ListenLoginLogout;
import com.SirBlobman.blobcatraz.listener.ListenMOTD;
import com.SirBlobman.blobcatraz.listener.ListenSlime;
import com.SirBlobman.blobcatraz.utility.Util;

/**
 * Main class for the plugin "Blobcatraz"<br/>
 * Used by all the extensions to make sure they work<br/>
 * Copyright &copy; SirBlobman 2017
 * @author SirBlobman
 * @see JavaPlugin
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
		events();
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
		c("fly", new CommandFly(), null);
		c("feed", new CommandHeal(), null);
		c("gamemode", new CommandGamemode(), null);
		c("gma", new CommandGamemode(), null);
		c("gmc", new CommandGamemode(), null);
		c("gms", new CommandGamemode(), null);
		c("gmsp", new CommandGamemode(), null);
		c("heal", new CommandHeal(), null);
		c("setmotd", new CommandMOTD(), null);
		c("spawner", new CommandSpawner(), new CommandSpawner());
	}
	
	private void events()
	{
		Util.regEvents
		(
			new ListenAFK(),
			new ListenAntiTNT(),
			new ListenAutoPickup(),
			new ListenLoginLogout(),
			new ListenFreeze(),
			new ListenHubEffects(),
			new ListenGiantLoot(),
			new ListenSlime(),
			new ListenMOTD()
		);
		Bukkit.getScheduler().runTaskTimer(this, new ListenHubEffects(), 0, 20);
	}
	
	protected void c(String cmd, CommandExecutor ce, TabCompleter tc)
	{
		PluginCommand pc = getCommand(cmd);
		if(ce != null) pc.setExecutor(ce);
		if(tc != null) pc.setTabCompleter(tc);
	}
}