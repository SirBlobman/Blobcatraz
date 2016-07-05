package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.item.Items;

public class CommandPortal implements CommandExecutor 
{
	public static HashMap<UUID, Location> p1 = new HashMap<UUID, Location>();
	public static HashMap<UUID, Location> p2 = new HashMap<UUID, Location>();
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player))
		{
			cs.sendMessage(Util.commandExecutorNotAPlayer);
			return true;
		}
		Player p = (Player) cs;
		UUID uuid = p.getUniqueId();
		
		try
		{
			if(label.equalsIgnoreCase("portal"))
			{
				if(args[0].equalsIgnoreCase("wand"))
				{
					ItemStack wand = Items.portalWand();
					Util.giveItem(p, wand);
					p.sendMessage(Util.blobcatraz + "You have recieved a portal wand!");
					return true;
				}
				if(args[0].equalsIgnoreCase("create"))
				{
					if(args.length > 2)
					{
						List<String> newArgs = Arrays.asList(args);
						newArgs.remove(0);
						newArgs.remove(0);
						
						StringBuilder command = new StringBuilder();
						for(String x : newArgs)
						{
							command.append(x + " ");
						}
						
						ConfigPortals.portalConfig.set(args[1] + ".pos1.world", p1.get(uuid).getWorld().getName());
						ConfigPortals.portalConfig.set(args[1] + ".pos1.x", p1.get(uuid).getX());
						ConfigPortals.portalConfig.set(args[1] + ".pos1.y", p1.get(uuid).getY());
						ConfigPortals.portalConfig.set(args[1] + ".pos1.z", p1.get(uuid).getZ());
						
						ConfigPortals.portalConfig.set(args[1] + ".pos2.world", p2.get(uuid).getWorld().getName());
						ConfigPortals.portalConfig.set(args[1] + ".pos2.x", p2.get(uuid).getX());
						ConfigPortals.portalConfig.set(args[1] + ".pos2.y", p2.get(uuid).getY());
						ConfigPortals.portalConfig.set(args[1] + ".pos2.z", p2.get(uuid).getZ());
						
						ConfigPortals.portalConfig.set(args[1] + ".command", "/" + command.toString());
						
						ConfigPortals.savePortals();
						p.sendMessage(Util.blobcatraz + "Created a command portal: §2§l" + args[1] + "§r!");
						return true;
					}
					
					ConfigPortals.portalConfig.set(args[1] + ".pos1.world", p1.get(uuid).getWorld().getName());
					ConfigPortals.portalConfig.set(args[1] + ".pos1.x", p1.get(uuid).getX());
					ConfigPortals.portalConfig.set(args[1] + ".pos1.y", p1.get(uuid).getY());
					ConfigPortals.portalConfig.set(args[1] + ".pos1.z", p1.get(uuid).getZ());
					
					ConfigPortals.portalConfig.set(args[1] + ".pos2.world", p2.get(uuid).getWorld().getName());
					ConfigPortals.portalConfig.set(args[1] + ".pos2.x", p1.get(uuid).getX());
					ConfigPortals.portalConfig.set(args[1] + ".pos2.y", p1.get(uuid).getY());
					ConfigPortals.portalConfig.set(args[1] + ".pos2.z", p1.get(uuid).getZ());
					
					ConfigPortals.portalConfig.set(args[1] + ".pos3.world", p.getLocation().getWorld().getName());
					ConfigPortals.portalConfig.set(args[1] + ".pos3.x", p.getLocation().getX());
					ConfigPortals.portalConfig.set(args[1] + ".pos3.y", p.getLocation().getY());
					ConfigPortals.portalConfig.set(args[1] + ".pos3.z", p.getLocation().getZ());
					ConfigPortals.portalConfig.set(args[1] + ".pos3.pitch", p.getLocation().getPitch());
					ConfigPortals.portalConfig.set(args[1] + ".pos3.yaw", p.getLocation().getYaw());
					
					ConfigPortals.savePortals();
					p.sendMessage(Util.blobcatraz + "Create a TP portal: §2§l" + args[1] + "§r!");
					return true;
				}
				if(args[0].equalsIgnoreCase("list"))
				{
					ConfigPortals.loadPortals();
					if(ConfigPortals.portals.isEmpty())
					{
						p.sendMessage(Util.blobcatraz + "You don't have any portals!");
						return true;
					}
					
					StringBuffer portallist = new StringBuffer();
					for(int i = 0; i < ConfigPortals.portals.size(); i++)
					{
						if(i != 0) portallist.append("§r, §2");
						portallist.append(ConfigPortals.portals.get(i));
					}
					
					p.sendMessage(Util.blobcatraz + "List of portals: \n§2" + portallist.toString());
					return true;
				}
				if(args[0].equalsIgnoreCase("remove"))
				{
					ConfigPortals.loadPortals();
					
					if(!ConfigPortals.portals.contains(args[1]))
					{
						p.sendMessage(Util.blobcatraz + "That portal doesn't exist!");
						return true;
					}
					
					ConfigPortals.portalConfig.set(args[1], null);
					ConfigPortals.savePortals();
					p.sendMessage(Util.blobcatraz + "Removed a portal: §2§l" + args[1] + "§r!");
					return true;
				}
			}
		}
		catch (Exception ex)
		{
			p.sendMessage(Util.blobcatraz + "Somethine went wrong! Do /portal help");
			return true;
		}
		
		return false;
	}
}