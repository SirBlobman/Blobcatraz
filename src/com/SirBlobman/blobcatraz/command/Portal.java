package com.SirBlobman.blobcatraz.command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.item.Items;

@SuppressWarnings({"unchecked", "rawtypes", "deprecation"})
public class Portal implements CommandExecutor 
{
	public static HashMap<UUID, Location> pos1 = new HashMap();
	public static HashMap<UUID, Location> pos2 = new HashMap();
	public static List<String> portals = new ArrayList();
	public static List<UUID> inPortal = new ArrayList();
	
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage(Util.notAPlayer);
			return true;
		}
		
		Player p = (Player) sender;
		
		try 
		{
			if (label.equalsIgnoreCase("portal")) 
			{
				if (args[0].equalsIgnoreCase("wand")) 
				{
					ItemStack wand = Items.portalWand();
					Util.giveItem(p, wand);
					sender.sendMessage(Util.blobcatraz + "Given you the portal wand!");
					return true;
				} 
				else if (args[0].equalsIgnoreCase("create")) 
				{
					UUID id = p.getUniqueId();
					if (args.length > 2) 
					{
						List<String> new_args = new ArrayList(Arrays.asList(args));
						new_args.remove(0);
						new_args.remove(0);
						StringBuilder command = new StringBuilder();
						for (String x : new_args) 
						{
							command.append(x + " ");
						}
						getPortalConfig().set(args[1] + ".pos1.world",
								Portal.pos1.get(id).getWorld().getName());
						getPortalConfig().set(args[1] + ".pos1.x",
								Double.valueOf(Portal.pos1.get(id).getX()));
						getPortalConfig().set(args[1] + ".pos1.y",
								Double.valueOf(Portal.pos1.get(id).getY()));
						getPortalConfig().set(args[1] + ".pos1.z",
								Double.valueOf(Portal.pos1.get(id).getZ()));
						getPortalConfig().set(args[1] + ".pos2.world",
								Portal.pos2.get(id).getWorld().getName());
						getPortalConfig().set(args[1] + ".pos2.x",
								Double.valueOf(Portal.pos2.get(id).getX()));
						getPortalConfig().set(args[1] + ".pos2.y",
								Double.valueOf(Portal.pos2.get(id).getY()));
						getPortalConfig().set(args[1] + ".pos2.z",
								Double.valueOf(Portal.pos2.get(id).getZ()));
						getPortalConfig().set(args[1] + ".command", "/" + command.toString());
						savePortals();
						sender.sendMessage(
								Util.blobcatraz + "Created a command portal called §2§l" + args[1] + "§a!");
						return true;
					}
					getPortalConfig().set(args[1] + ".pos1.world", Portal.pos1.get(id).getWorld().getName());
					getPortalConfig().set(args[1] + ".pos1.x", Double.valueOf(Portal.pos1.get(id).getX()));
					getPortalConfig().set(args[1] + ".pos1.y", Double.valueOf(Portal.pos1.get(id).getY()));
					getPortalConfig().set(args[1] + ".pos1.z", Double.valueOf(Portal.pos1.get(id).getZ()));
					getPortalConfig().set(args[1] + ".pos2.world", Portal.pos2.get(id).getWorld().getName());
					getPortalConfig().set(args[1] + ".pos2.x", Double.valueOf(Portal.pos2.get(id).getX()));
					getPortalConfig().set(args[1] + ".pos2.y", Double.valueOf(Portal.pos2.get(id).getY()));
					getPortalConfig().set(args[1] + ".pos2.z", Double.valueOf(Portal.pos2.get(id).getZ()));
					getPortalConfig().set(args[1] + ".pos3.world", p.getLocation().getWorld().getName());
					getPortalConfig().set(args[1] + ".pos3.x", Double.valueOf(p.getLocation().getX()));
					getPortalConfig().set(args[1] + ".pos3.y", Double.valueOf(p.getLocation().getY()));
					getPortalConfig().set(args[1] + ".pos3.z", Double.valueOf(p.getLocation().getZ()));
					getPortalConfig().set(args[1] + ".pos3.pitch", Float.valueOf(p.getLocation().getPitch()));
					getPortalConfig().set(args[1] + ".pos3.yaw", Float.valueOf(p.getLocation().getYaw()));
					savePortals();
					sender.sendMessage(
							Util.blobcatraz + "Created a teleporting portal called §2§l" + args[1] + "§a!");
					return true;
				} else if (args[0].equalsIgnoreCase("list")) 
				{
					if (Portal.portals.isEmpty()) 
					{
						sender.sendMessage(
								Util.blobcatraz + "You have no portals! Do /portal create to make portals.");
						return true;
					}
					StringBuilder list = new StringBuilder();
					list.append(Util.blobcatraz + "List of portals: ");
					for (int x = 0; x < Portal.portals.size() - 1; x++) 
					{
						list.append("§2§l" + Portal.portals.get(x) + "§a, ");
					}
					list.append("§2§l" + Portal.portals.get(Portal.portals.size() - 1) + "§a.");
					sender.sendMessage(list.toString());
					return true;
				}
				else if (args[0].equalsIgnoreCase("remove")) 
				{
					if (!Portal.portals.contains(args[1])) 
					{
						sender.sendMessage(
								Util.blobcatraz + "That portal does not exist! Do /portal list to see existing portals.");
						return true;
					}
					getPortalConfig().set(args[1], null);
					savePortals();
					sender.sendMessage(Util.blobcatraz + "Removed a portal called §2§l" + args[1] + "§a!");
					return true;
				}
			}
		}
		catch (Exception e) 
		{
			sender.sendMessage(Util.blobcatraz + "Something went wrong! Do /portal help");
			return true;
		}
		
		return false;
	}
	
// Custom Portal Config After This Point
	private static FileConfiguration portalConfig = null;
	private static File portalConfigFile = null;

	public static void reloadPortalConfig() 
	{
		if (portalConfigFile == null) 
		{
			portalConfigFile = new File(Blobcatraz.instance.getDataFolder(), "portals.yml");
		}
		portalConfig = YamlConfiguration.loadConfiguration(portalConfigFile);
		
		final InputStream defPortalConfigStream = Blobcatraz.instance.getResource("portals.yml");
		if (defPortalConfigStream != null) 
		{
			YamlConfiguration defPortalConfig = YamlConfiguration.loadConfiguration(defPortalConfigStream);
			portalConfig.setDefaults(defPortalConfig);
		}
	}
	
	public static void savePortals() 
	{
		savePortalConfig();
		Portal.portals.clear();
		for (String x : getPortalConfig().getKeys(false)) 
		{
			Portal.portals.add(x);
		}
	}

	public static FileConfiguration getPortalConfig() 
	{
		if (portalConfig == null) 
		{
			reloadPortalConfig();
		}
		return portalConfig;
	}

	public static void savePortalConfig() 
	{
		if (portalConfig == null || portalConfigFile == null) 
		{
			return;
		}
		try 
		{
			getPortalConfig().save(portalConfigFile);
		} catch (IOException ex) 
		{
			Util.print("Could not save portals to " + portalConfigFile);
			ex.printStackTrace();
		}
	}
}