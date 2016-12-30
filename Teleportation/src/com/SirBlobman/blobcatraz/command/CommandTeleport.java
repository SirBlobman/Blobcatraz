package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.TeleportUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandTeleport implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String cmd = c.getName().toLowerCase();
			if(cmd.equals("teleport"))
			{
				String permission = "blobcatraz.tp";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				int length = args.length;
				switch(length)
				{
				case 1: return senderToPlayer(p, args);
				case 2: return playerToPlayer(p, args);
				case 3: return senderToCoords(p, args);
				case 4: return playerToCoords(p, args);
				default:	
					p.sendMessage(Util.IA);
					return false;
				}
			}
			if(cmd.equals("center"))
			{
				String permission = "blobcatraz.tp.center";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				Location o = p.getLocation();
				int ox = o.getBlockX();
				int oz = o.getBlockZ();

				World w = o.getWorld();
				double x = ox + 0.5D;
				double y = o.getY();
				double z = oz + 0.5D;
				Location center = new Location(w, x, y, z, 0.0F, 90.0F);
				p.teleport(center);
				String msg = Util.prefix + Util.option("command.center.success");
				p.sendMessage(msg);
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	private boolean senderToPlayer(Player p, String[] args)
	{
		String name = args[0];
		Player p2 = Bukkit.getPlayer(name);
		if(p2 == null)
		{
			String msg = Util.prefix + Util.option("error.target.does not exist", name);
			p.sendMessage(msg);
			return true;
		}
		
		String pname = p2.getName();
		String cname = p.getName();
		boolean b = !(pname.equals(cname));
		
		p.teleport(p2);
		if(b) p.sendMessage(Util.prefix + Util.option("command.teleport.success.1", p2.getDisplayName()));
		return true;
	}
	
	private boolean senderToCoords(Player p, String[] args)
	{
		Location o = p.getLocation();
		String sx = args[0];
		String sy = args[1];
		String sz = args[2];
		Location coords = TeleportUtil.coords(o, sx, sy, sz);
		if(coords == null)
		{
			String error = Util.prefix + Util.option("error.invalid coordinates");
			p.sendMessage(error);
			return true;
		}
		
		int x = coords.getBlockX();
		int y = coords.getBlockY();
		int z = coords.getBlockZ();
		String scoords = Util.format("&5%s %s %s", x, y, z);
		
		p.teleport(coords);
		String msg = Util.prefix + Util.option("command.teleport.success.3", scoords);
		p.sendMessage(msg);
		return true;
	}
	
	String permission = "blobcatraz.tp.others";
	private boolean playerToPlayer(Player p, String[] args)
	{
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		
		String name1 = args[0];
		String name2 = args[1];
		Player p1 = Bukkit.getPlayer(name1);
		Player p2 = Bukkit.getPlayer(name2);
		if(p1 == null || p2 == null)
		{
			String msg = Util.prefix + Util.option("error.target.does not exist", name1 + " " + name2);
			p.sendMessage(msg);
			return true;
		}
		
		String pname1 = p1.getDisplayName();
		String pname2 = p2.getDisplayName();
		
		if(p1.isInsideVehicle())
		{
			Entity v = p1.getVehicle();
			v.teleport(p2);
			String msg = Util.prefix + Util.option("command.teleport.passenger", pname1);
			v.sendMessage(msg);
		}
		p1.teleport(p2);
		
		boolean b = pname1.equals(pname2);
		if(!b)
		{
			String msg1 = Util.prefix + Util.option("command.teleport.2.p1", pname2);
			String msg2 = Util.prefix + Util.option("command.teleport.2.p2", pname1);
			p1.sendMessage(msg1);
			p2.sendMessage(msg2);
		}
		
		String msg3 = Util.prefix + Util.option("command.teleport.2.sender", pname1, pname2);
		p.sendMessage(msg3);
		return true;
	}
	
	private boolean playerToCoords(Player p, String[] args)
	{
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		
		String name = args[0];
		Player p1 = Bukkit.getPlayer(name);
		if(p1 == null)
		{
			String msg = Util.prefix + Util.option("error.target.does not exist", name);
			p.sendMessage(msg);
			return true;
		}
		
		Location o = p1.getLocation();
		String sx = args[1];
		String sy = args[2];
		String sz = args[3];
		Location coords = TeleportUtil.coords(o, sx, sy, sz);
		if(coords == null)
		{
			String error = Util.prefix + Util.option("error.invalid coordinates");
			p.sendMessage(error);
			return true;
		}
		
		int x = coords.getBlockX();
		int y = coords.getBlockY();
		int z = coords.getBlockZ();
		String scoords = Util.format("&5%s %s %s", x, y, z);
		String name1 = p.getName();
		String name2 = p1.getName();
		
		p1.teleport(coords);
		if(!name1.equals(name2))
		{
			String msg1 = Util.prefix + Util.option("command.teleport.4.sender", name2, scoords);
			String msg2 = Util.prefix + Util.option("command.teleport.4.p", name1, scoords);
			p.sendMessage(msg1);
			p1.sendMessage(msg2);
		}
		return true;
	}
}