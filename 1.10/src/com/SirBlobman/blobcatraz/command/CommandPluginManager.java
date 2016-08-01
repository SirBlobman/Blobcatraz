package com.SirBlobman.blobcatraz.command;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.plugin.manager.PluginManager;

public class CommandPluginManager implements CommandExecutor
{
	private final PluginManager control;

	public CommandPluginManager(Blobcatraz instance)
	{
		control = new PluginManager(instance);
	}

	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(args.length == 0)
		{
			cs.sendMessage(Util.notEnoughArguments);
			return false;
		}

		switch(args[0].toLowerCase())
		{
		case "enable":
		{
			if(!cs.hasPermission("blobcatraz.pluginmanager.enable"))
			{
				cs.sendMessage(Util.noPermission + "blobcatraz.pluginmanager.enable");

				if(args.length < 2)
				{
					cs.sendMessage(Util.notEnoughArguments);
					return false;
				}

				String name = Util.getFinalArg(args, 2);
				Plugin p = Bukkit.getServer().getPluginManager().getPlugin(name);

				if(p == null)
				{
					cs.sendMessage(Util.blobcatraz + name + " is not a Plugin");
				}
				if(p.isEnabled())
				{
					cs.sendMessage(Util.blobcatraz + p.getName() + " is already Enabled");
				}
				else
				{
					control.enablePlugin(p);
					cs.sendMessage(Util.blobcatraz + "§5" + p.getName() + " has been enabled!");
				}

				return true;
			}
		}

		case "disable":
		{
			if(!cs.hasPermission("blobcatraz.pluginmanager.disable"))
			{
				cs.sendMessage(Util.noPermission + "blobcatraz.pluginmanager.disable");
				return true;
			}
			if(args.length < 2)
			{
				cs.sendMessage(Util.notEnoughArguments);
				return false;
			}

			String name = args[1];
			Plugin p = Bukkit.getServer().getPluginManager().getPlugin(name);
			if(p == null)
			{
				cs.sendMessage(Util.blobcatraz + name + " is not a Plugin");
			}
			if(!p.isEnabled())
			{
				cs.sendMessage(Util.blobcatraz + name + " is already Disabled");
			}
			else
			{
				control.disablePlugin(p);
				cs.sendMessage(Util.blobcatraz + name + " has been disabled");
			}

			return true;
		}
		case "load":
		{
			if (!cs.hasPermission("blobcatraz.pluginmanager.load")) 
			{
				cs.sendMessage(Util.noPermission + "blobcatraz.pluginmanager.load");
				return true;
			}
			if (args.length < 2)
			{
				cs.sendMessage(Util.notEnoughArguments);
				return false;
			}

			String toLoad = Util.getFinalArg(args, 1);
			File fileToLoad = new File("plugins" + File.separator + toLoad + (toLoad.endsWith(".jar") ? "" : ".jar"));
			Util.print("Attempting to load: " + fileToLoad.getName());
			if (!fileToLoad.exists())
			{
				cs.sendMessage(Util.blobcatraz + "That file doesn't exist");
				return true;
			}
			PluginDescriptionFile desc = control.getDescription(fileToLoad);
			if (desc == null)
			{
				cs.sendMessage(Util.blobcatraz + "Invalid description");
			}
			if (Bukkit.getPluginManager().getPlugin(desc.getName()) != null)
			{
				cs.sendMessage(Util.blobcatraz + toLoad + " is already loaded");
				return true;
			}
			Plugin p = null;
			if ((p = control.loadPlugin(fileToLoad)) != null)
			{
				control.enablePlugin(p);
				cs.sendMessage(Util.blobcatraz + Util.color(String.format("Loaded Plugin: &a%1$s %2$s", p.getDescription().getName(), p.getDescription().getVersion())));
			}
			else
			{
				cs.sendMessage(Util.blobcatraz + "Failed to load plugin");
			}
			return true;
		}
		case "unload":
		{
			if(!cs.hasPermission("blobcatraz.pluginmanager.unload"))
			{
				cs.sendMessage(Util.noPermission + "blobcatraz.pluginmanager.unload");
				return true;
			}

			if(args.length < 2)
			{
				cs.sendMessage(Util.notEnoughArguments);
				return false;
			}

			String name = args[1];
			Plugin p = Bukkit.getServer().getPluginManager().getPlugin(name);
			if(p == null)
			{
				cs.sendMessage(Util.blobcatraz + name + " is not a Plugin");
			}
			if(control.unloadPlugin(p, true))
			{
				cs.sendMessage(Util.blobcatraz + "Unloaded " + p.getName());
			}
			else
			{
				cs.sendMessage(Util.blobcatraz + "Failed to unload " + p.getName());
			}

			return true;
		}
		case "reload":
		{
			if (!cs.hasPermission("blobcatraz.pluginmanager.reload")) 
			{
				cs.sendMessage(Util.noPermission + "blobcatraz.pluginmanager.reload");
				return true;
			}
			if (args.length < 2)
			{
				cs.sendMessage(Util.notEnoughArguments);
				return false;
			}

			String name = args[1];
			Plugin p = Bukkit.getServer().getPluginManager().getPlugin(name);
			if (p == null)
			{
				cs.sendMessage(Util.blobcatraz + name + " is not a Plugin");
			}
			else
			{
				File file = control.getFile((JavaPlugin)p);
				if (file == null)
				{
					cs.sendMessage(Util.blobcatraz + file + " is Missing!");
					return true;
				}
				File named = new File("plugins" + File.separator + file.getName());
				JavaPlugin loaded = null;
				if (!control.unloadPlugin(p, Boolean.valueOf(false))) 
				{
					cs.sendMessage(Util.blobcatraz + "Error unloading plugin");
				} else if ((loaded = (JavaPlugin)control.loadPlugin(named)) == null) 
				{
					cs.sendMessage(Util.blobcatraz + "Error reloading plugin");
				}
				control.enablePlugin(loaded);
				cs.sendMessage(Util.blobcatraz + "Successfully reloaded " + name);
			}
			return true;
		}
		case "sreload":
		{
			if (!cs.hasPermission("blobcatraz.pluginmanager.sreload")) 
			{
				cs.sendMessage(Util.noPermission + "blobcatraz.pluginmanager.sreload");
				return true;
			}
			if (args.length < 2)
			{
				cs.sendMessage(Util.notEnoughArguments);
				return false;
			}

			String name = args[1];
			Plugin p = Bukkit.getServer().getPluginManager().getPlugin(name);
			if (p == null)
			{
				cs.sendMessage(Util.blobcatraz + name + " is not a Plugin");
			}
			if(!p.isEnabled())
			{
				cs.sendMessage(Util.blobcatraz + name + " is not enabled!");
			}
			else
			{
				control.disablePlugin(p);
				control.enablePlugin(p);
				cs.sendMessage(Util.blobcatraz + "Successfully Soft-Reloaded " + name);
			}

			return true;
		}
		case "show":
		{
			if (!cs.hasPermission("blobcatraz.pluginmanager.show")) 
			{
				cs.sendMessage(Util.noPermission + "blobcatraz.pluginmanager.show");
				return true;
			}
			if (args.length < 2)
			{
				cs.sendMessage(Util.notEnoughArguments);
				return false;
			}

			String name = args[1];
			Plugin p = Bukkit.getServer().getPluginManager().getPlugin(name);
			if (p == null)
			{
				cs.sendMessage(Util.blobcatraz + name + " is not a Plugin");
			}
			else
			{
				File f = control.getFile((JavaPlugin) p);
				cs.sendMessage(Util.blobcatraz + "Plugin Info: " + p.getName());
				if(p.getDescription().getDescription() != null)	cs.sendMessage("Description: " + p.getDescription().getDescription());
				cs.sendMessage("Version: " + p.getDescription().getVersion());
				cs.sendMessage("Main Class: " + p.getDescription().getMain());
				cs.sendMessage("File: " + f.getName());

				StringBuffer authors = new StringBuffer();
				if(p.getDescription().getAuthors() != null && !p.getDescription().getAuthors().isEmpty())
				{
					for(String a : p.getDescription().getAuthors())
					{
						if(authors.length() > 0)
						{
							authors.append(", ");
						}
						authors.append(a);
					}
				}
				if(authors != null)
				{
					if(p.getDescription().getAuthors().size() == 1) cs.sendMessage("Author: " + authors);
					if(p.getDescription().getAuthors().size() > 1) cs.sendMessage("Authors: " + authors);
				}

				if(p.getDescription().getWebsite() != null)
				{
					cs.sendMessage("Website: " + p.getDescription().getWebsite());
				}
			}

			return true;
		}
		case "list":
		{
			if (!cs.hasPermission("blobcatraz.pluginmanager.list")) 
			{
				cs.sendMessage(Util.noPermission + "blobcatraz.pluginmanager.list");
				return true;
			}
			boolean versions = false, options = false, alphabetical = false;
			String search = "";

			for (int i = 1; i < args.length; i++)
			{
				String s = args[i];
				if ((s.equalsIgnoreCase("-v")) || (s.equalsIgnoreCase("-version")))
				{
					versions = true;
				}
				else if ((s.equalsIgnoreCase("-options")) || (s.equalsIgnoreCase("-o")))
				{
					options = true;
				}
				else if ((s.equalsIgnoreCase("-alphabetical")) || (s.equalsIgnoreCase("-a")))
				{
					alphabetical = true;
				}
				else if ((s.startsWith("-s:")) || (s.startsWith("-search:")))
				{
					String[] t = s.split("[:]", 2);
					if (t.length == 2) {
						search = t[1];
					}
				}
			}
			if (options)
			{
				String[] options_s = "&eList options\n&e-v &6- Shows plugins with versions\n&e-o &6- Lists options\n&e-a &6- Lists plugins in alphabetical order\n&e-s:name &6- Lists plugins containing the given name".split("\n");
				for (String s : options_s) {
					cs.sendMessage(Util.color(s));
				}
				return true;
			}
			Plugin[] pl = Bukkit.getPluginManager().getPlugins();
			String enabledList = "";String disabledList = "";

			ArrayList<Plugin> plugins = new ArrayList<Plugin>(Arrays.asList(pl));
			if (!search.isEmpty())
			{
				Iterator<Plugin> it = plugins.iterator();
				while (it.hasNext())
				{
					Plugin p = (Plugin)it.next();
					if (!p.getName().contains(search)) {
						it.remove();
					}
				}
			}
			if (alphabetical)
			{
				ArrayList<String> s = new ArrayList<String>();
				for (Plugin p : plugins) {
					s.add(p.getName());
				}
				Collections.sort(s);
				plugins = new ArrayList<Plugin>();
				for (String a : s) {
					plugins.add(Bukkit.getPluginManager().getPlugin(a));
				}
			}
			for (Plugin p : plugins)
			{
				String l = p.getName() + (versions ? " " + p.getDescription().getVersion() : "");
				if (p.isEnabled())
				{
					if (enabledList.isEmpty()) {
						enabledList = l;
					} else {
						enabledList = enabledList + ", " + l;
					}
				}
				else if (disabledList.isEmpty()) {
					disabledList = l;
				} else {
					disabledList = disabledList + ", " + l;
				}
			}
			if (!enabledList.isEmpty()) {
				cs.sendMessage(Util.blobcatraz + "Enabled Plugins: " + enabledList);
			}
			if (!disabledList.isEmpty()) {
				cs.sendMessage(Util.blobcatraz + "Disabled Plugins: " + disabledList);
			}
			return true;
		}
		}

		return false;
	}
}