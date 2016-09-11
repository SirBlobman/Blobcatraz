package com.SirBlobman.blobcatraz.command;

import java.util.List;
import java.util.UUID;

import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.item.BItems;
import com.SirBlobman.blobcatraz.item.PortalWand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandPortal implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("portal"))
		{
			if(args.length > 0)
			{
				String sub = args[0].toLowerCase();
				switch(sub)
				{
				case "wand":
					return wand(cs);
				case "list":
					return list(cs);
				case "create":
					return create(cs, args);
				case "remove":
					return remove(cs, args);
				}
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		return false;
		
	}

	private boolean wand(CommandSender cs)
	{
		String permission = "blobcatraz.portal.wand";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			ItemStack wand = BItems.portalWand();
			ItemUtil.give(p, wand);
			return true;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	private boolean list(CommandSender cs)
	{
		String permission = "blobcatraz.portal.list";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		List<String> portals = ConfigPortals.getPortals();
		StringBuffer list = new StringBuffer();
		for(int i = 0; i < portals.size(); i++)
		{
			if(i != 0) list.append("§r, §2");
			list.append(portals.get(i));
		}
		
		cs.sendMessage(Util.blobcatraz + "Portal List:");
		cs.sendMessage(list.toString());
		return true;
	}
	
	private boolean create(CommandSender cs, String[] args)
	{
		String permission = "blobcatraz.portal.create";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			UUID uuid = p.getUniqueId();
			if(args.length > 1)
			{
				YamlConfiguration portals = ConfigPortals.load();
				String pos1 = args[1] + ".pos1.";
				String pos2 = args[1] + ".pos2.";
				String pos3 = args[1] + ".pos3.";

				Location l1 = PortalWand.pos1.get(uuid);
				World w1 = l1.getWorld();
				String world1 = w1.getName();
				double x1 = l1.getX();
				double y1 = l1.getY();
				double z1 = l1.getZ();

				Location l2 = PortalWand.pos2.get(uuid);
				World w2 = l2.getWorld();
				String world2 = w2.getName();
				double x2 = l2.getX();
				double y2 = l2.getY();
				double z2 = l2.getZ();
				
				portals.set(pos1 + "world", world1);
				portals.set(pos1 + "x", x1);
				portals.set(pos1 + "y", y1);
				portals.set(pos1 + "z", z1);
				
				portals.set(pos2 + "world", world2);
				portals.set(pos2 + "x", x2);
				portals.set(pos2 + "y", y2);
				portals.set(pos2 + "z", z2);
				
				if(args.length > 2)
				{
					String command = Util.getFinal(args, 2);
					portals.set(args[1] + ".command", "/" + command);
					ConfigPortals.save();
					
					p.sendMessage(Util.blobcatraz + "Created a command portal called §2" + args[1] + " §rwith the command §e/" + command);
					return true;
				}
				
				Location l3 = p.getLocation();
				World w3 = l3.getWorld();
				String world3 = w3.getName();
				double x3 = l3.getX();
				double y3 = l3.getY();
				double z3 = l3.getZ();
				float yaw = l3.getYaw();
				float pitch = l3.getPitch();
				
				portals.set(pos3 + "world", world3);
				portals.set(pos3 + "x", x3);
				portals.set(pos3 + "y", y3);
				portals.set(pos3 + "z", z3);
				portals.set(pos3 + "yaw", yaw);
				portals.set(pos3 + "pitch", pitch);
				ConfigPortals.save();
				
				p.sendMessage("Created a teleporting portal called §2" + args[1]);
				return true;
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	private boolean remove(CommandSender cs, String[] args)
	{
		YamlConfiguration portals = ConfigPortals.load();
		List<String> list = ConfigPortals.getPortals();
		if(list.contains(args[1]))
		{
			portals.set(args[1], null);
			ConfigPortals.save();
			cs.sendMessage(Util.blobcatraz + "The portal called §2" + args[1] + " §rwas removed!");
			return true;
		}
		cs.sendMessage(Util.blobcatraz + "That portal doesn't exist");
		return true;
	}
}