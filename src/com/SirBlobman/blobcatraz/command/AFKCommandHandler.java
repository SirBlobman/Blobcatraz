package com.SirBlobman.blobcatraz.command;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class AFKCommandHandler implements CommandExecutor 
{
	public static FileConfiguration afk = YamlConfiguration.loadConfiguration(new File(Blobcatraz.instance.getDataFolder(), "afk.yml"));

	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) 
	{
		String nsender = sender.getName();
		
		if (label.equalsIgnoreCase("not-afk")) 
		{
			Util.broadcast("§6§l* §7" + sender.getName() + " §6is no longer AFK");
			return true;
		}
		if (label.equalsIgnoreCase("afk")) 
		{
			if (args.length < 1) 
			{
				Util.broadcast("§6§l* §7" + sender.getName() + " §6is now AFK");
				afk.set(nsender + ".afk", true);
			} 
			else 
			{
				String reason = Util.getFinalArg(args, 0);
				Util.broadcast("§6§l* §7" + sender.getName() + " §6is now AFK: ");
				Util.broadcast("    - " + reason);
				afk.set(nsender + ".afk", true);
			}
			return true;
		}
		
		return false;
	}



	private static File afkFile = null;
	public static void reloadAFKConfig()
	{
		if(afkFile == null)
		{
			afkFile = new File(Blobcatraz.instance.getDataFolder(), "afk.yml");
		}
		afk = YamlConfiguration.loadConfiguration(afkFile);
		
		Reader defConfigStream = new InputStreamReader(Blobcatraz.instance.getResource("afk.yml"));
		if(defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			afk.setDefaults(defConfig);
		}
	}
	
	public static FileConfiguration getAFKConfig()
	{
		if(afk == null)
		{
			reloadAFKConfig();
		}
		return afk;
	}
	
	public static void saveAFKConfig()
	{
		if(afk == null || afkFile == null)
		{
			return;
		}
		try
		{
			getAFKConfig().save(afkFile);
		}
		catch (IOException e)
		{
			Blobcatraz.instance.getLogger().log(Level.SEVERE, "Could not save AFK.yml", e);
		}
	}
}
