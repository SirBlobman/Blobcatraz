package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class CommandTeleport implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof LivingEntity)) {cs.sendMessage(Util.commandExecutorNonLiving); return true;}
		LivingEntity le = (LivingEntity) cs;	

		if(label.equalsIgnoreCase("tp") || label.equalsIgnoreCase("teleport"))
		{
			if(!le.hasPermission("blobcatraz.tp")) {le.sendMessage(Util.noPermission + "blobcatraz.tp"); return true;}
			if(args.length == 1)
			{
				Player p = Bukkit.getPlayer(args[0]);
				if(p == null) {le.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Player"); return true;}
				le.teleport(p);
				le.sendMessage(Util.blobcatraz + "You have been teleported to " + p.getDisplayName());
				return true;
			}
			if(args.length == 2)
			{
				if(!le.hasPermission("blobcatraz.tp.others")) {le.sendMessage(Util.noPermission + "blobcatraz.tp.others"); return true;}
				Player p = Bukkit.getPlayer(args[0]);
				Player p2 = Bukkit.getPlayer(args[1]);
				if(p == null) {le.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Player"); return true;}
				if(p2 == null) {le.sendMessage(Util.blobcatraz + "§5" + args[1] + " §ris not a Player"); return true;}
				p.teleport(p2);
				p.sendMessage(Util.blobcatraz + le.getName() + " has teleported you to " + p2.getDisplayName());
				p2.sendMessage(Util.blobcatraz + le.getName() + " has teleported " + p.getDisplayName() + " §rto you");
				le.sendMessage(Util.blobcatraz + "You teleported §5" + p.getDisplayName() + " §rto §5" + p2.getDisplayName());
				return true;
			}
			if(args.length == 3)
			{
				Double x = null, y = null, z = null;
				Location l = le.getLocation();
				if(args[0].startsWith("~"))
				{
					try
					{
						double xr = Double.parseDouble(args[0].substring(1));
						x = getRelativeX(l, xr);
					} catch(Exception ex)
					{
						le.sendMessage(Util.blobcatraz + "Invalid coordinates");
						return true;
					}
				}
				if(args[1].startsWith("~"))
				{
					try
					{
						double yr = Double.parseDouble(args[1].substring(1));
						y = getRelativeY(l, yr);
					} catch(Exception ex)
					{
						le.sendMessage(Util.blobcatraz + "Invalid coordinates");
						return true;
					}
				}
				if(args[2].startsWith("~"))
				{
					try
					{
						double zr = Double.parseDouble(args[2].substring(1));
						z = getRelativeZ(l, zr);
					} catch(Exception ex)
					{
						le.sendMessage(Util.blobcatraz + "Invalid coordinates");
						return true;
					}
				}
				else
				{
					try
					{
						x = Double.parseDouble(args[0]);
						y = Double.parseDouble(args[1]);
						z = Double.parseDouble(args[2]);
					} catch(Exception ex)
					{
						le.sendMessage(Util.blobcatraz + "Invalid coordinates");
						return true;
					}
				}
				
				if(x!=null && y!=null && z!=null)
				{
					Location l2 = new Location(l.getWorld(), x, y, z, l.getYaw(), l.getPitch());
					String ls = l2.getX() + " " + l2.getY() + " " + l2.getZ();
					le.teleport(l2);
					le.sendMessage(Util.blobcatraz + "You have been teleported to " + ls);
					return true;
				}
				else
				{
					le.sendMessage(Util.blobcatraz + "Invalid coordinates");
					return true;
				}
			}
		}
		if(label.equalsIgnoreCase("center") || label.equalsIgnoreCase("centre"))
		{
			if(!le.hasPermission("blobcatraz.center")) {le.sendMessage(Util.noPermission + "blobcatraz.center"); return true;}
			Location l = le.getLocation();
			Location center = new Location(l.getWorld(), l.getBlockX() + 0.5, l.getY(), l.getBlockZ() + 0.5, 0, 90);
			le.teleport(center);
			return true;
		}
		return false;
	}
	
	private static double getRelativeX(Location original, double x)
	{
		Location l = original;
		l.setX(original.getX() + x);
		return l.getX();
	}

	private static double getRelativeY(Location original, double y)
	{
		Location l = original;
		l.setY(original.getY() + y);
		return l.getY();
	}
	
	private static double getRelativeZ(Location original, double z)
	{
		Location l = original;
		l.setY(original.getZ() + z);
		return l.getZ();
	}
}