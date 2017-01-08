package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandFly implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("fly"))
		{
			if(args.length > 0)
			{
				String sub = args[0].toLowerCase();
				if(sub.equals("on")) return on(cs, args);
				if(sub.equals("off")) return off(cs, args);
				//if(sub.equals("check")) return check(cs, args);
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		return false;
	}
	
	String permission1 = "blobcatraz.fly";
	String permission2 = permission1 + ".others";
	private boolean on(CommandSender cs, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, permission1)) return true;
		if(args.length > 1)
		{
			if(!PlayerUtil.hasPermission(cs, permission2)) return true;
			String target = args[1];
			Player p = Bukkit.getPlayer(target);
			if(p == null) 
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", target);
				cs.sendMessage(msg);
				return true;
			}

			p.setAllowFlight(true);
			p.setFlying(true);
			String msg1 = Util.prefix + Util.option("command.fly.sender.on", target);
			String msg2 = Util.prefix + Util.option("command.fly.target.on", cs.getName());
			cs.sendMessage(msg1);
			p.sendMessage(msg2);
			return true;
		}
		
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			p.setAllowFlight(true);
			p.setFlying(true);
			String msg = Util.prefix + Util.option("command.fly.1.on");
			p.sendMessage(msg);
			return true;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	

	private boolean off(CommandSender cs, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, permission1)) return true;
		if(args.length > 1)
		{
			if(!PlayerUtil.hasPermission(cs, permission2)) return true;
			String target = args[1];
			Player p = Bukkit.getPlayer(target);
			if(p == null) 
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", target);
				cs.sendMessage(msg);
				return true;
			}

			p.setFlying(false);
			p.setAllowFlight(false);
			String msg1 = Util.prefix + Util.option("command.fly.sender.off", target);
			String msg2 = Util.prefix + Util.option("command.fly.target.off", cs.getName());
			cs.sendMessage(msg1);
			p.sendMessage(msg2);
			return true;
		}
		
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			p.setFlying(false);
			p.setAllowFlight(false);
			String msg = Util.prefix + Util.option("command.fly.1.off");
			p.sendMessage(msg);
			return true;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
}