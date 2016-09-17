package com.SirBlobman.blobcatraz.plugin.manager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public class PluginManager 
{
	Blobcatraz blobcatraz;
	Manager manager;
	org.bukkit.plugin.PluginManager pm = Bukkit.getPluginManager();
	
	public PluginManager(Manager plugin, Blobcatraz instance)
	{
		this.manager = plugin;
		this.blobcatraz = instance;
	}
	
	public PluginDescriptionFile getDescription(File file)
	{
		try
		{
			JarFile jar = new JarFile(file);
			ZipEntry zip = jar.getEntry("plugin.yml");
			if(zip == null) {jar.close(); return null;}
			PluginDescriptionFile pdf = new PluginDescriptionFile(jar.getInputStream(zip));
			jar.close();
			return pdf;
		} catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public File getFile(JavaPlugin plugin)
	{
		try
		{
			Field field = JavaPlugin.class.getDeclaredField("file");
			field.setAccessible(true);
			File file = (File) field.get(plugin);
			return file;
		} catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public void enable(Plugin plugin)
	{
		pm.enablePlugin(plugin);
		File file = new File(manager.getDataFolder(), "List.yml");
		if(file.exists())
		{
			FileConfiguration disabled = YamlConfiguration.loadConfiguration(file);
			List<String> list = disabled.getStringList("disabled");
			if(list.contains(plugin.getName())) list.remove(plugin.getName());
			disabled.set("Disabled", list);
			try{disabled.save(file);} catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	public void disable(Plugin plugin)
	{
		pm.disablePlugin(plugin);
		File file = new File(manager.getDataFolder(), "List.yml");
		if(!file.exists())
		{
			try{file.createNewFile();} catch(Exception ex) {ex.printStackTrace();}
		}
		FileConfiguration disabled = YamlConfiguration.loadConfiguration(file);
		List<String> list = disabled.getStringList("Disabled");
		if(!list.contains(plugin.getName())) list.add(plugin.getName());
		disabled.set("Disabled", list);
		try{disabled.save(file);} catch(Exception ex) {ex.printStackTrace();}
	}
	
	public Plugin load(File file)
	{
		try
		{
			Plugin plugin = pm.loadPlugin(file);
			try {plugin.onLoad();} catch (Exception ex)
			{
				Util.print("Failed to load plugin: " + file);
				ex.printStackTrace();
			}
			return plugin;
		} catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean unload(Plugin plugin, boolean reloadepends)
	{
		String pName = plugin.getName();

		ArrayList<Plugin> reload = new ArrayList<Plugin>();
		if (reloadepends)
		{
			for (Plugin p : pm.getPlugins())
			{
				List<String> depend = p.getDescription().getDepend();
				if (depend != null) {
					for (String s : depend) {
						if (s.equals(pName)) {
							if (!reload.contains(p))
							{
								reload.add(p);
								unload(p, Boolean.valueOf(false));
							}
						}
					}
				}
				List<String> softDepend = p.getDescription().getSoftDepend();
				if (softDepend != null) {
					for (String s : softDepend) {
						if (s.equals(pName)) {
							if (!reload.contains(p))
							{
								reload.add(p);
								unload(p, Boolean.valueOf(false));
							}
						}
					}
				}
			}
		}
		for (Plugin p : reload) {
			Bukkit.getServer().broadcastMessage(p.getName() + "\n");
		}
		List<Plugin> plugins;
		Map<String, Plugin> names;
		SimpleCommandMap commandMap;
		Map<String, Command> commands;
		try
		{
			Field pluginsField = pm.getClass().getDeclaredField("plugins");
			Field lookupNamesField = pm.getClass().getDeclaredField("lookupNames");
			Field commandMapField = pm.getClass().getDeclaredField("commandMap");
			Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");

			pluginsField.setAccessible(true);
			lookupNamesField.setAccessible(true);
			commandMapField.setAccessible(true);
			knownCommandsField.setAccessible(true);

			plugins = (List<Plugin>)pluginsField.get(pm);
			names = (Map<String, Plugin>)lookupNamesField.get(pm);
			commandMap = (SimpleCommandMap)commandMapField.get(pm);
			commands = (Map<String, Command>)knownCommandsField.get(commandMap);
		}
		catch (NoSuchFieldException|IllegalAccessException e)
		{
			e.printStackTrace();
			return false;
		}
		if (commandMap != null) {
			synchronized (commandMap)
			{
				Iterator<Map.Entry<String, Command>> it = commands.entrySet().iterator();
				while (it.hasNext())
				{
					Map.Entry<String, Command> entry = (Entry<String, Command>)it.next();
					if ((entry.getValue() instanceof PluginCommand))
					{
						PluginCommand c = (PluginCommand)entry.getValue();
						if (c.getPlugin() == plugin)
						{
							c.unregister(commandMap);
							it.remove();
						}
					}
				}
			}
		}
		disable(plugin);
		synchronized (pm)
		{
			if ((plugins != null) && (plugins.contains(plugin))) {
				plugins.remove(plugin);
			}
			if ((names != null) && (names.containsKey(pName))) {
				names.remove(pName);
			}
		}
		JavaPluginLoader jpl = (JavaPluginLoader)plugin.getPluginLoader();
		Field loaders = null;
		try
		{
			loaders = jpl.getClass().getDeclaredField("loaders");
			loaders.setAccessible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			Map<String, ?> loaderMap = (Map<String, ?>)loaders.get(jpl);
			loaderMap.remove(plugin.getDescription().getName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		ClassLoader cl = plugin.getClass().getClassLoader();
		try
		{
			((URLClassLoader)cl).close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.gc();
		if (reloadepends) {
			for (int i = 0; i < reload.size(); i++) {
				enable(load(getFile((JavaPlugin)reload.get(i))));
			}
		}
		File loaded = getFile((JavaPlugin)plugin);
		File unloaded = new File(getFile((JavaPlugin)plugin) + ".unloaded");

		return loaded.renameTo(unloaded);
	}
}