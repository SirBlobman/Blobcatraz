package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFly implements CommandExecutor
{
	String on = Util.format("&2ON");
	String off = Util.format("&4OFF");
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("fly"))
		{
			int length = args.length;
			if(length == 0) return fly0(cs);
			if(length == 1) return fly1(cs, args);
			if(length >= 2) return fly2(cs, args);
		}
		return false;
	}
	
	private boolean fly0(CommandSender cs)
	{
		cs.sendMessage(Util.NEA);
		return true;
	}
	
	private boolean fly1(CommandSender cs, String[] args)
	{
		if(cs instanceof Player)
		{	
			Player p = (Player) cs;
			String sub = args[0].toLowerCase();
			String toggle = "blobcatraz.fly.toggle";
			String check = "blobcatraz.fly.check";
			if(sub.equals("on"))
			{
				if(!PlayerUtil.hasPermission(p, toggle)) return true;
				p.setAllowFlight(true);
				p.sendMessage(Util.blobcatraz + "Flight is " + on);
				return true;
			}
			if(sub.equals("off"))
			{
				if(!PlayerUtil.hasPermission(p, toggle)) return true;
				p.setAllowFlight(false);
				p.sendMessage(Util.blobcatraz + "Flight is " + off);
				return true;
			}
			if(sub.equals("check"))
			{
				if(!PlayerUtil.hasPermission(p, check)) return true;
				boolean fly = p.getAllowFlight();
				if(fly) p.sendMessage("Your ability to fly is " + on);
				else p.sendMessage("Your ability to fly is " + off);
				return true;
			}
			cs.sendMessage(Util.IA);
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	private boolean fly2(CommandSender cs, String[] args)
	{
		String sub = args[0].toLowerCase();
		String target = args[1];
		String toggle = "blobcatraz.fly.toggle.others";
		String check = "blobcatraz.fly.check.others";
		Player p = Bukkit.getPlayer(target);
		if(p != null)
		{
			if(sub.equals("on"))
			{
				if(!PlayerUtil.hasPermission(p, toggle)) return true;
				p.setAllowFlight(true);
				p.sendMessage(Util.blobcatraz + "Flight is now" + on);
				cs.sendMessage(Util.blobcatraz + "§5" + p.getDisplayName() + "'s §rFlight is now " + on);
				return true;
			}
			if(sub.equals("off"))
			{
				if(!PlayerUtil.hasPermission(p, toggle)) return true;
				p.setAllowFlight(false);
				p.sendMessage(Util.blobcatraz + "Flight is now" + off);
				cs.sendMessage(Util.blobcatraz + "§5" + p.getDisplayName() + "'s §rFlight is now " + off);
				return true;
			}
			if(sub.equals("check"))
			{
				if(!PlayerUtil.hasPermission(p, check)) return true;
				boolean fly = p.getAllowFlight();
				if(fly) cs.sendMessage("§5" + p.getDisplayName() + "'s §rability to fly is currently " + on);
				else cs.sendMessage("§5" + p.getDisplayName() + "'s §rability to fly is currently " + off);
				return true;
			}
		}
		cs.sendMessage(Util.blobcatraz + "§5" + target + " §ris not a Player");
		return true;
	}
}