package com.SirBlobman.blobcatraz.command;

import java.io.File;
import java.util.List;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.plugin.manager.Manager;
import com.SirBlobman.blobcatraz.plugin.manager.PluginManager;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandPM implements CommandExecutor 
{
	private final Manager plugin;
	private final PluginManager manager;
	org.bukkit.plugin.PluginManager pm = Bukkit.getServer().getPluginManager();
	
	public CommandPM(Manager instance)
	{
		plugin = instance;
		manager = new PluginManager(plugin, Blobcatraz.instance);
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(args.length == 0)
		{
			//String[] options = new String[] {"Enable", "Disable", "Load", "Unload", "Reload", "SReload", "Show", "List", "ConfigReload"};
			cs.sendMessage(Util.prefix + Util.color("&aPlugin Manager Help"));
			cs.sendMessage(c.getUsage());
			return true;
		}
		switch(args[0].toLowerCase())
		{
		case "enable":
			return enable(cs, label, args);
		case "disable":
			return disable(cs, label, args);
		case "load":
			return load(cs, label, args);
		case "unload":
			return unload(cs, label, args);
		case "reload":
			return reload(cs, label, args);
		case "sreload":
			return sreload(cs, label, args);
		case "show":
			return show(cs, label, args);
		case "configreload":
			return reloadConfig(cs, label, args);
		case "list":
			return list(cs, label, args);
		}
		cs.sendMessage(Util.prefix + "Error executing §5/" + label);
		return true;
	}
	
	public boolean enable(CommandSender cs, String label, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, "blocatraz.pluginmanager.enable")) return true;
		if(args.length < 2)
		{
			cs.sendMessage(Util.NEA);
			return false;
		}
		String name = Util.getFinal(args, 1);
		Plugin p = pm.getPlugin(name);
		if(p == null) cs.sendMessage(Util.prefix + "Plugin doesn't exist: " + name);
		else if(p.isEnabled()) cs.sendMessage(Util.prefix + name + " is already enabled!");
		else 
		{
			manager.enable(p);
			cs.sendMessage(Util.prefix + p.getName() + " has been enabled!");
		}
		return true;
	}
	
	public boolean disable(CommandSender cs, String label, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, "blobcatraz.pluginmanager.disable")) return true;
		if(args.length < 2)
		{
			cs.sendMessage(Util.NEA);
			return false;
		}
		String name = Util.getFinal(args, 1);
		Plugin p = pm.getPlugin(name);
		if(p == null) cs.sendMessage(Util.prefix + "Plugin doesn't exist: " + name);
		else if(!p.isEnabled()) cs.sendMessage(Util.prefix + name + " is already disabled!");
		else
		{
			manager.disable(p);
			cs.sendMessage(Util.prefix + p.getName() + " has been disabled!");
		}
		return true;
	}
	
	public boolean load(CommandSender cs, String label, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, "blobcatraz.pluginmanager.load")) return true;
		if(args.length < 2)
		{
			cs.sendMessage(Util.NEA);
			return false;
		}
		String name = Util.getFinal(args, 1);
		File toLoad = new File("plugins" + File.separator + name + (name.endsWith(".jar") ? "" : ".jar"));
		if(!toLoad.exists())
		{
			cs.sendMessage(Util.prefix + toLoad + " doesn't exist!");
			return true;
		}
		PluginDescriptionFile pdf = manager.getDescription(toLoad);
		if(pdf == null)
		{
			cs.sendMessage(Util.prefix + toLoad + " doesn't have a proper description (plugin.yml)");
			return true;
		}
		if(pm.getPlugin(pdf.getName()) != null)
		{
			cs.sendMessage(Util.prefix + pdf.getName() + " is already loaded!");
			return true;
		}
		Plugin p = null;
		if((p = manager.load(toLoad)) != null)
		{
			manager.enable(p);
			cs.sendMessage(Util.prefix + "Successfully loaded " + p.getName() + p.getDescription().getVersion());
		}
		else
		{
			cs.sendMessage(Util.prefix + "Failed to load " + name);
		}
		return true;
	}
	
	private boolean unload(CommandSender cs, String label, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, "blobcatraz.pluginmanager.unload")) return true;
		if(args.length < 2)
		{
			cs.sendMessage(Util.NEA);
			return false;
		}
		String name = Util.getFinal(args, 1);
		Plugin p = pm.getPlugin(name);
		if(p == null) cs.sendMessage(Util.prefix + "Plugin doesn't exist: " + name);
		else if(manager.unload(p, true)) cs.sendMessage(Util.prefix + p.getName() + " has been unloaded!");
		else cs.sendMessage(Util.prefix + "Failed to unload " + p.getName());
		return true;
	}
	
	private boolean reload(CommandSender cs, String label, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, "blobcatraz.pluginmanager.reload")) return true;
		if(args.length < 2)
		{
			cs.sendMessage(Util.NEA);
			return false;
		}
		String name = Util.getFinal(args, 1);
		Plugin p = pm.getPlugin(name);
		if(p == null) cs.sendMessage(Util.prefix + "Plugin doesn't exist: " + name);
		else
		{
			File file = manager.getFile((JavaPlugin) p);
			if(file == null)
			{
				cs.sendMessage(Util.prefix + name + "'s Jar File is missing");
				return true;
			}
			File unload = new File("plugins" + File.separator + file.getName());
			JavaPlugin loaded = null;
			if(!manager.unload(p, false)) cs.sendMessage(Util.prefix + "Failed to unload " + name);
			else if((loaded = (JavaPlugin) manager.load(unload)) == null) cs.sendMessage("Failed to reload " + name);
			manager.enable(loaded);
			cs.sendMessage(Util.prefix + p.getName() + " has been reloaded");
		}
		return true;
	}
	
	private boolean sreload(CommandSender cs, String label, String[] args)
	{

		if(!PlayerUtil.hasPermission(cs, "blocatraz.pluginmanager.sreload")) return true;
		if(args.length < 2)
		{
			cs.sendMessage(Util.NEA);
			return false;
		}
		String name = Util.getFinal(args, 1);
		Plugin p = pm.getPlugin(name);
		if(p == null) cs.sendMessage(Util.prefix + "Plugin doesn't exist: " + name);
		else if(!p.isEnabled()) cs.sendMessage(Util.prefix + name + " is already disabled!");
		else
		{
			manager.disable(p);
			manager.enable(p);
			cs.sendMessage(Util.prefix + p.getName() + ", " + p.getDescription().getVersion() + " soft-reloaded successfully");
		}
		return true;
	}
	
	private boolean show(CommandSender cs, String label, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, "blobcatraz.pluginmanager.show")) return true;
		if(args.length < 2)
		{
			cs.sendMessage(Util.NEA);
			return false;
		}
		String name = Util.getFinal(args, 1);
		Plugin p = pm.getPlugin(name);
		if(p == null) cs.sendMessage(Util.prefix + "Plugin doesn't exist: " + name);
		else
		{
			File file = manager.getFile((JavaPlugin) p);
			cs.sendMessage(Util.prefix + "§lDescription for §6§l" + p.getName() + "§r§l: ");
			cs.sendMessage("  §lStatus:§r " + (p.isEnabled() ? "§2§lEnabled" : "§4§lDisabled"));
			if(p.getDescription().getDescription() != null) cs.sendMessage("  §lDescription:§r " + p.getDescription().getDescription());
			cs.sendMessage("  §lVersion:§r " + p.getDescription().getVersion());
			cs.sendMessage("  §lMain Class:§r " + p.getDescription().getMain());
			cs.sendMessage("  §lFile:§r " + file.getName());
			StringBuffer authors = new StringBuffer();
			List<String> dauthors = p.getDescription().getAuthors();
			if(dauthors != null && !dauthors.isEmpty())
			{
				for(String author : dauthors)
				{
					if(authors.length() > 0) authors.append(", ");
					authors.append(author);
				}
			}
			if(authors != null) 
			{
				int size = dauthors.size();
				String prefix = size == 1 ? "  §lAuthor:§r " : "  §lAuthors:§r ";
				cs.sendMessage(prefix + authors.toString());
			}
			if(p.getDescription().getWebsite() != null) cs.sendMessage("  §lWebsite:§r §3§n" + p.getDescription().getWebsite());
		}
		return true;
	}
	
	private boolean list(CommandSender cs, String label, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, "blobcatraz.pluginmanager.list")) return true;
		StringBuffer plugins = new StringBuffer();
		for(Plugin p : pm.getPlugins())
		{
			if(plugins.length() > 0) plugins.append("§r, ");
			if(p.isEnabled()) plugins.append("§2" + p.getName());
			else plugins.append("§4" + p.getName());
		}
		cs.sendMessage(Util.prefix + "List of plugins: ");
		cs.sendMessage(plugins.toString());
		return true;
	}
	
	private boolean reloadConfig(CommandSender cs, String label, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, "blobcatraz.pluginmanager.configreload")) return true;
		Plugin p;
		if(args.length < 2) p = this.plugin;
		else p = pm.getPlugin(Util.getFinal(args, 1));
		String name = Util.getFinal(args, 1);
		if(p == null) cs.sendMessage(Util.prefix + "Plugin doesn't exist: " + name);
		p.reloadConfig();
		cs.sendMessage(Util.prefix + "Attempted to reload the config of " + p.getName());
		return true;
	}
}