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
			String permission = "blobcatraz.tp";
			if(!Util.hasPermission(cs, permission)) return true;
			switch(args.length)
			{
			case 1:
				return senderToPlayer(le, args);
			case 2:
				return playerToPlayer(le, args);
			case 3:
				return senderToCoords(le, args);
			case 4:
				return playerToCoords(le, args);
			}
		}
		if(label.equalsIgnoreCase("center") || label.equalsIgnoreCase("centre"))
		{
			if(!Util.hasPermission(le, "blobcatraz.center")) return true;
			Location o = le.getLocation();
			Location center = new Location(o.getWorld(), o.getBlockX() + 0.5, o.getY(), o.getBlockZ() + 0.5, 0, 90);
			le.teleport(center);
			le.sendMessage(Util.blobcatraz + "You have been teleported to the center");
			return true;
		}
		return false;
	}
	
	private boolean senderToPlayer(LivingEntity le, String[] args)
	{
		String name = args[0];
		Player p = Bukkit.getPlayer(name);
		if(p == null) {le.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player!"); return true;}
		le.teleport(p);
		le.sendMessage(Util.blobcatraz + "You have been teleported to " + p.getDisplayName());
		return true;
	}
	
	private boolean senderToCoords(LivingEntity le, String[] args)
	{
		String sx = args[0];
		String sy = args[1];
		String sz = args[2];
		Location coords = Util.getCoords(le.getLocation(), sx, sy, sz);
		if(coords == null) {le.sendMessage(Util.blobcatraz + "Invalid coordinates"); return false;}
		le.teleport(coords);
		String scoords = "§5" + coords.getBlockX() + "§r, §5" + coords.getBlockY() + "§r, §5" + coords.getBlockZ();
		le.sendMessage(Util.blobcatraz + "You have been teleported to: " + scoords);
		return true;
	}
	
	private boolean playerToPlayer(LivingEntity le, String[] args)
	{
		String permission = "blobcatraz.tp.others";
		if(!Util.hasPermission(le, permission)) return true;
		String name = args[0];
		String name2 = args[1];
		Player p = Bukkit.getPlayer(name);
		Player p2 = Bukkit.getPlayer(name2);
		if(p == null) {le.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player!"); return true;}
		if(p2 == null) {le.sendMessage(Util.blobcatraz + "§5" + name2 + " §ris not a Player!"); return true;}
		p.teleport(p2);
		if(!p.equals(le))
		{
			le.sendMessage(Util.blobcatraz + "You teleported " + p.getDisplayName() + " §rto " + p2.getDisplayName());
			p.sendMessage(Util.blobcatraz + le.getName() + " §rteleported you to " + p2.getDisplayName());
		}
		return true;
	}
	
	private boolean playerToCoords(LivingEntity le, String[] args)
	{
		String permission = "blobcatraz.tp.others";
		if(!Util.hasPermission(le, permission)) return true;
		String name = args[0];
		Player p = Bukkit.getPlayer(name);
		if(p == null) {le.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player!"); return true;}
		String sx = args[1];
		String sy = args[2];
		String sz = args[3];
		Location coords = Util.getCoords(p.getLocation(), sx, sy, sz);
		if(coords == null) {le.sendMessage(Util.blobcatraz + "Invalid coordinates"); return false;}
		p.teleport(coords);
		String scoords = "§5" + coords.getBlockX() + "§r, §5" + coords.getBlockY() + "§r, §5" + coords.getBlockZ();
		if(!p.equals(le))
		{
			p.sendMessage(Util.blobcatraz + le.getName() + " §rhas teleported you to " + scoords);
			le.sendMessage(Util.blobcatraz + p.getDisplayName() + " §rhas been teleported to: " + scoords);
		}
		return true;
	}
}