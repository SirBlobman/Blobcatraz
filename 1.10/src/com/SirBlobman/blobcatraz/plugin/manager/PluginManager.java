package com.SirBlobman.blobcatraz.plugin.manager;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class PluginManager
{
	Blobcatraz plugin;
	
	public PluginManager(Blobcatraz instance)
	{
		plugin = instance;
	}
	
	public PluginDescriptionFile getDescription(File f)
	{
		try
		{
			JarFile jar = new JarFile(f);
			ZipEntry zip = jar.getEntry("plugin.yml");
			if(zip == null) {jar.close(); return null;}
			
			PluginDescriptionFile pdf = new PluginDescriptionFile(jar.getInputStream(zip));
			jar.close();
			
			return pdf;
		}
		catch(Exception ex)
		{
			Util.print(f + " doesn't have a proper description");
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public File getFile(JavaPlugin jp)
	{
		try
		{
			Field f = JavaPlugin.class.getDeclaredField("file");
			f.setAccessible(true);
			return (File)f.get(jp);
		}
		catch (Exception ex)
		{
			Util.print("Error getting file!");
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public void enablePlugin(Plugin p)
	{
		Bukkit.getPluginManager().enablePlugin(p);
	}
	
	public void disablePlugin(Plugin p)
	{
		Bukkit.getPluginManager().disablePlugin(p);
	}
	
	public Plugin loadPlugin(File p)
	{
		try
		{
			Plugin pl = Bukkit.getPluginManager().loadPlugin(p);
			try{pl.onLoad();}
			catch(Exception ex)
			{
				Util.print("Failed to load plugin: " + p.getName());
				ex.printStackTrace();
			}
		}
		catch(Exception ex)
		{
			Util.print("Failed to load plugin: " + p.getName());
			ex.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public boolean unloadPlugin(Plugin pl, boolean reloadDependents)
	{
		org.bukkit.plugin.PluginManager pm = Bukkit.getPluginManager();
		String name = pl.getName();
		List<Plugin> plugins = null;
		Map<String, Plugin> names = null;
		Map<String, Command> commands = null;
		ArrayList<Plugin> reload = new ArrayList<Plugin>();
		
		if(reloadDependents)
		{
			for(Plugin p : pm.getPlugins())
			{
				List<String> depend = p.getDescription().getDepend();
				if(depend != null)
				{
					for(String s : depend)
					{
						if(s.equals(name))
						{
							if(!reload.contains(p))
							{
								reload.add(p);
								unloadPlugin(p, false);
							}
						}
					}
				}
			}
		}
		
		SimpleCommandMap scm;
		try
		{
			Field pF = pm.getClass().getDeclaredField("plugins");
			Field lNF = pm.getClass().getDeclaredField("lookupNames");
			Field cMF = pm.getClass().getDeclaredField("commandMap");
			Field kCF = SimpleCommandMap.class.getDeclaredField("knownCommands");
			
			pF.setAccessible(true);
			lNF.setAccessible(true);
			cMF.setAccessible(true);
			kCF.setAccessible(true);
			
			plugins = (List<Plugin>) pF.get(pm);
			names = (Map<String, Plugin>) lNF.get(pm);
			scm = (SimpleCommandMap) cMF.get(pm);
			commands = (Map<String, Command>) kCF.get(scm);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
		if(scm != null)
		{
			synchronized (scm)
			{
				Iterator<Entry<String, Command>> it = commands.entrySet().iterator();
				
				while(it.hasNext())
				{
					Entry<String, Command> entry = it.next();
					if(entry.getValue() instanceof PluginCommand)
					{
						PluginCommand c = (PluginCommand) entry.getValue();
						if(c.getPlugin() == pl)
						{
							c.unregister(scm);
							it.remove();
						}
					}
				}
			}
		}
		
		disablePlugin(pl);
		
		synchronized(pm)
		{
			if(plugins != null && plugins.contains(pl))
			{
				plugins.remove(pl);
			}
			
			if(names != null && names.containsKey(name))
			{
				names.remove(name);
			}
		}
		
		JavaPluginLoader jpl = (JavaPluginLoader) pl.getPluginLoader();
		Field loaders = null;
		
		try
		{
			loaders = jpl.getClass().getDeclaredField("loaders");
			loaders.setAccessible(true);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		try
		{
			Map<String, ?> lM = (Map<String, ?>) loaders.get(jpl);
			lM.remove(pl.getDescription().getName());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		ClassLoader cl = pl.getClass().getClassLoader();
		try
		{
			((URLClassLoader)cl).close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		System.gc();
		if(reloadDependents)
		{
			for(int i = 0; i < reload.size(); i++)
			{
				enablePlugin(loadPlugin(getFile((JavaPlugin) reload.get(i))));
			}
		}
		
		return true;
	}
}