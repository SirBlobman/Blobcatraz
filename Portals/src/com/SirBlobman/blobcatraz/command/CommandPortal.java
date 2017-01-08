package com.SirBlobman.blobcatraz.command;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.item.BItems;
import com.SirBlobman.blobcatraz.listener.ListenPortalWand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandPortal implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("portal"))
		{
			if(args.length > 0)
			{
				String sub = args[0].toLowerCase();
				switch(sub)
				{
				case "wand": return wand(cs);
				case "list": return list(cs);
				case "create": return create(cs, args);
				case "remove": return remove(cs, args);
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
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	private boolean list(CommandSender cs)
	{
		String permission = "blobcatraz.portal.list";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		List<String> list = ConfigPortals.portals();
		StringBuffer portals = new StringBuffer();
		for(int i = 0; i < list.size(); i++)
		{
			if(i != 0) portals.append(Util.color("&r, &2"));
			String portal = list.get(i);
			portals.append(portal);
		}
		
		cs.sendMessage(Util.prefix + "Portal List: ");
		cs.sendMessage(portals.toString());
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
				YamlConfiguration config = ConfigPortals.load();
				String a1 = args[1];
				String path1 = a1 + ".pos1.";
				String path2 = a1 + ".pos2.";
				String path3 = a1 + ".pos3.";
				
				Location l1 = ListenPortalWand.p1(uuid);
				World world1 = l1.getWorld();
				String w1 = world1.getName();
				double x1 = l1.getX();
				double y1 = l1.getY();
				double z1 = l1.getZ();
				
				Location l2 = ListenPortalWand.p2(uuid);
				World world2 = l2.getWorld();
				String w2 = world2.getName();
				double x2 = l2.getX();
				double y2 = l2.getY();
				double z2 = l2.getZ();
				
				config.set(path1 + "world", w1);
				config.set(path2 + "world", w2);
				config.set(path1 + "x", x1);
				config.set(path2 + "x", x2);
				config.set(path1 + "y", y1);
				config.set(path2 + "y", y2);
				config.set(path1 + "z", z1);
				config.set(path2 + "z", z2);
				
				if(args.length > 2)
				{
					String com = Util.getFinal(args, 2);
					String comm = "/" + com;
					config.set(a1 + ".command", comm);
					ConfigPortals.save();
					
					String msg = Util.prefix + Util.option("command.portal create.command", a1, comm);
					p.sendMessage(msg);
					return true;
				}
				
				Location l3 = p.getLocation();
				World world3 = l3.getWorld();
				String w3 = world3.getName();
				double x3 = l3.getX();
				double y3 = l3.getY();
				double z3 = l3.getZ();
				float yaw = l3.getYaw();
				float pitch = l3.getPitch();
				
				config.set(path3 + "world", w3);
				config.set(path3 + "x", x3);
				config.set(path3 + "y", y3);
				config.set(path3 + "z", z3);
				config.set(path3 + "yaw", yaw);
				config.set(path3 + "pitch", pitch);
				ConfigPortals.save();
				
				String msg = Util.prefix + Util.option("command.portal create.teleport", a1);
				p.sendMessage(msg);
				return true;
			}
			p.sendMessage(Util.NEA);
			return false;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	private boolean remove(CommandSender cs, String[] args)
	{
		String permission = "blobcatraz.portal.remove";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		YamlConfiguration config = ConfigPortals.load();
		List<String> list = ConfigPortals.portals();
		String portal = args[1];
		if(list.contains(portal))
		{
			config.set(portal, null);
			ConfigPortals.save();

			String msg = Util.prefix + Util.option("command.portal remove.success", portal);
			cs.sendMessage(msg);
			return true;
		}
		String msg = Util.prefix + Util.option("command.portal remove.does not exist", portal);
		cs.sendMessage(msg);
		return true;
	}
}