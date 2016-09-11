package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.TeleportUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CommandTeleport implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("tp"))
			{
				String permission = "blobcatraz.tp";
				if(!PlayerUtil.hasPermission(le, permission)) return true;
				int length = args.length;
				switch(length)
				{
				case 1:
					return sToP(le, args);
				case 2:
					return pToP(le, args);
				case 3:
					return sToC(le, args);
				case 4:
					return pToC(le, args);
				}
			}
			if(command.equals("center"))
			{
				String permission = "blobcatraz.center";
				if(!PlayerUtil.hasPermission(le, permission)) return true;
				Location o = le.getLocation();
				World w = o.getWorld();
				double x = o.getBlockX() + 0.5;
				double y = o.getY();
				double z = o.getBlockZ() + 0.5;
				Location center = new Location(w, x, y, z, 0, 90);
				le.teleport(center);
				le.sendMessage(Util.blobcatraz + "You are now in the center of this block");
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.csNotLiving);
		return true;
	}
	
	private boolean sToP(LivingEntity le, String[] args)
	{
		String name = args[0];
		Player p = Bukkit.getPlayer(name);
		if(p == null)
		{
			le.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
			return true;
		}
		
		String pname = p.getName();
		String lname = le.getName();
		
		le.teleport(p);
		if(!pname.equals(lname)) le.sendMessage("You were teleported to §r" + p.getDisplayName());
		return true;
	}
	
	private boolean sToC(LivingEntity le, String[] args)
	{
		Location original = le.getLocation();
		String sx = args[0];
		String sy = args[1];
		String sz = args[2];
		Location coords = TeleportUtil.getTeleportCoords(original, sx, sy, sz);
		if(coords == null)
		{
			le.sendMessage(Util.blobcatraz + "Invalid coordinates");
			return true;
		}
		
		int x = coords.getBlockX();
		int y = coords.getBlockY();
		int z = coords.getBlockZ();
		le.teleport(coords);
		String scoords = "§5" + x + " " + y + " " + z;
		le.sendMessage(Util.blobcatraz + "You were teleported to: " + scoords);
		return true;
	}
	
	String permission = "blobcatraz.tp.others";
	private boolean pToP(LivingEntity le, String[] args)
	{
		if(!PlayerUtil.hasPermission(le, permission)) return true;
		String name = args[0];
		String name2 = args[1];
		Player p = Bukkit.getPlayer(name);
		Player p2 = Bukkit.getPlayer(name2);
		if(p == null || p2 == null)
		{
			le.sendMessage(Util.blobcatraz + "Invalid Player!");
			return true;
		}

		String pname = p.getName();
		String p2name = p2.getName();
		
		if(p.isInsideVehicle())
		{
			Entity v = p.getVehicle();
			v.teleport(p2);
			v.sendMessage(Util.blobcatraz + "§5" + p.getName() + " §rwas your passenger, so you were also teleported");
		}
		p.teleport(p2);
		
		if(!p2name.equals(pname))
		{
			p.sendMessage(Util.blobcatraz + "You were teleported to §r" + p2.getDisplayName());
			p2.sendMessage(Util.blobcatraz + p.getDisplayName() + " §rwas teleported to you!");
		}
		le.sendMessage(Util.blobcatraz + "You teleported " + p.getDisplayName() + " §rto " + p2.getDisplayName());
		return true;
	}
	
	private boolean pToC(LivingEntity le, String[] args)
	{
		if(!PlayerUtil.hasPermission(le, permission)) return true;
		
		String name = args[0];
		Player p = Bukkit.getPlayer(name);
		if(p == null)
		{
			le.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
			return true;
		}
		
		Location original = p.getLocation();
		String sx = args[1];
		String sy = args[2];
		String sz = args[3];
		Location coords = TeleportUtil.getTeleportCoords(original, sx, sy, sz);
		if(coords == null)
		{
			le.sendMessage(Util.blobcatraz + "Invalid Coordinates");
			return true;
		}
		
		int x = coords.getBlockX();
		int y = coords.getBlockY();
		int z = coords.getBlockZ();
		String scoords = "§5" + x + " " + y + " " + z;
		String lname = le.getName();
		String pname = p.getName();
		
		if(!pname.equals(lname))
		{
			p.sendMessage(Util.blobcatraz + lname + " §rteleported you to " + scoords);
			le.sendMessage(Util.blobcatraz + pname + " §rwas teleported to " + scoords);
		}
		return true;
	}
}