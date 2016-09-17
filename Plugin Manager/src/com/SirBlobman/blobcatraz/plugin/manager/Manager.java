package com.SirBlobman.blobcatraz.plugin.manager;

import java.io.File;
import java.util.List;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.command.CommandPM;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Manager extends Blobcatraz 
{
	private Blobcatraz blobcatraz = Blobcatraz.instance;
	private PluginManager manager = new PluginManager(this, blobcatraz);
	
	@Override
	public void onEnable()
	{
		commands();
		configs();
		Util.broadcast("Plugin Manager has been enabled!");
	}
	
	@Override
	public void onDisable()
	{
		Util.broadcast("Plugin Manager has been disabled!");
	}
	
	private void commands()
	{
		getCommand("pm").setExecutor(new CommandPM(this));
	}
	
	private void configs()
	{
		File file = new File(getDataFolder(), "List.yml");
		if(file.exists())
		{
			final FileConfiguration disabled = YamlConfiguration.loadConfiguration(file);
			boolean reload = disabled.contains("Reload") && disabled.getBoolean("Reload");
			if(reload)
			{
				disabled.set("Reload", null);
				getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
				{
					@Override
					public void run()
					{
						List<String> list = disabled.getStringList("Disabled");
						for(String name : list) manager.disable(Bukkit.getPluginManager().getPlugin(name));
					}
				});
			}
			else disabled.set("Disabled", null);
			
			try{disabled.save(file);} catch (Exception ex) {ex.printStackTrace();}
		}
	}
}