package com.SirBlobman.blobcatraz.command;

import java.util.ArrayList;
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
import com.SirBlobman.blobcatraz.config.Portals;
import com.SirBlobman.blobcatraz.item.Items;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Portal implements CommandExecutor 
{
	public static HashMap<UUID, Location> pos1 = new HashMap();
	public static HashMap<UUID, Location> pos2 = new HashMap();
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
				if (args[0].equalsIgnoreCase("create")) 
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
						Portals.portalConfig.set(args[1] + ".pos1.world", pos1.get(id).getWorld().getName());
						Portals.portalConfig.set(args[1] + ".pos1.x", Double.valueOf(pos1.get(id).getX()));
						Portals.portalConfig.set(args[1] + ".pos1.y", Double.valueOf(pos1.get(id).getY()));
						Portals.portalConfig.set(args[1] + ".pos1.z", Double.valueOf(pos1.get(id).getZ()));
						Portals.portalConfig.set(args[1] + ".pos2.world", pos2.get(id).getWorld().getName());
						Portals.portalConfig.set(args[1] + ".pos2.x", Double.valueOf(pos2.get(id).getX()));
						Portals.portalConfig.set(args[1] + ".pos2.y", Double.valueOf(pos2.get(id).getY()));
						Portals.portalConfig.set(args[1] + ".pos2.z", Double.valueOf(pos2.get(id).getZ()));
						Portals.portalConfig.set(args[1] + ".command", "/" + command.toString());
						Portals.savePortals();
						sender.sendMessage(Util.blobcatraz + "Created a command portal called §2§l" + args[1] + "§a!");
						return true;
					}
					Portals.portalConfig.set(args[1] + ".pos1.world", pos1.get(id).getWorld().getName());
					Portals.portalConfig.set(args[1] + ".pos1.x", Double.valueOf(pos1.get(id).getX()));
					Portals.portalConfig.set(args[1] + ".pos1.y", Double.valueOf(pos1.get(id).getY()));
					Portals.portalConfig.set(args[1] + ".pos1.z", Double.valueOf(pos1.get(id).getZ()));
					Portals.portalConfig.set(args[1] + ".pos2.world", pos2.get(id).getWorld().getName());
					Portals.portalConfig.set(args[1] + ".pos2.x", Double.valueOf(pos2.get(id).getX()));
					Portals.portalConfig.set(args[1] + ".pos2.y", Double.valueOf(pos2.get(id).getY()));
					Portals.portalConfig.set(args[1] + ".pos2.z", Double.valueOf(pos2.get(id).getZ()));
					Portals.portalConfig.set(args[1] + ".pos3.world", p.getLocation().getWorld().getName());
					Portals.portalConfig.set(args[1] + ".pos3.x", Double.valueOf(p.getLocation().getX()));
					Portals.portalConfig.set(args[1] + ".pos3.y", Double.valueOf(p.getLocation().getY()));
					Portals.portalConfig.set(args[1] + ".pos3.z", Double.valueOf(p.getLocation().getZ()));
					Portals.portalConfig.set(args[1] + ".pos3.pitch", Float.valueOf(p.getLocation().getPitch()));
					Portals.portalConfig.set(args[1] + ".pos3.yaw", Float.valueOf(p.getLocation().getYaw()));
					Portals.savePortals();
					sender.sendMessage(Util.blobcatraz + "Created a teleporting portal called §2§l" + args[1] + "§a!");
					return true;
				} 
				if (args[0].equalsIgnoreCase("list")) 
				{
					Portals.loadPortals();
					if (Portals.portals.isEmpty()) 
					{
						sender.sendMessage(Util.blobcatraz + "You have no portals! Do /portal create to make portals.");
						return true;
					}
					StringBuilder list = new StringBuilder();
					list.append(Util.blobcatraz + "List of portals: ");
					for (int x = 0; x < Portals.portals.size() - 1; x++) 
					{
						list.append("§2§l" + Portals.portals.get(x) + "§a, ");
					}
					list.append("§2§l" + Portals.portals.get(Portals.portals.size() - 1) + "§a.");
					sender.sendMessage(list.toString());
					return true;
				}
				if (args[0].equalsIgnoreCase("remove")) 
				{
					Portals.loadPortals();
					if (!Portals.portals.contains(args[1])) 
					{
						sender.sendMessage(Util.blobcatraz + "That portal does not exist! Do /portal list to see existing portals.");
						return true;
					}
					Portals.portalConfig.set(args[1], null);
					Portals.savePortals();
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
}