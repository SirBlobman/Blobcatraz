package com.SirBlobman.blobcatraz.listener;

import java.io.File;

import com.SirBlobman.blobcatraz.plugin.manager.Manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener
{
	@EventHandler
	public void reload(PlayerCommandPreprocessEvent e)
	{
		String msg = e.getMessage();
		String[] args = msg.split(" ");
		if(args.length == 1)
		{
			if(args[0].equalsIgnoreCase("/reload") || args[0].equalsIgnoreCase("/rl"))
			{
				File file = new File(Manager.instance.getDataFolder(), "List.yml");
				if(!file.exists())
				{
					try{file.createNewFile();} catch (Exception ex) {ex.printStackTrace();}
				}
				FileConfiguration disabled = YamlConfiguration.loadConfiguration(file);
				disabled.set("Reload", true);
				try{disabled.save(file);} catch(Exception ex) {ex.printStackTrace();}
			}
		}
	}
}