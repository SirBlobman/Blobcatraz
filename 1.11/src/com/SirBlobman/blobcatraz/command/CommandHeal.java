package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandHeal implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		switch(cmd)
		{
		case "heal": return heal(cs, args);
		case "feed": return feed(cs, args);
		default: return false;
		}
	}
	
	private static final String healPerm = "blobcatraz.heal";
	private static final String feedPerm = "blobcatraz.feed";
	private boolean heal(CommandSender cs, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, healPerm)) return true;
		
		if(args.length > 0)
		{
			String permission = healPerm + ".others";
			if(!PlayerUtil.hasPermission(cs, permission)) return true;
			
			String target = args[0];
			Player p = Bukkit.getPlayer(target);
			if(p == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", target);
				cs.sendMessage(msg);
				return true;
			}
			
			PlayerUtil.heal(p);
			String msg1 = Util.prefix + Util.option("command.heal.target");
			String msg2 = Util.prefix + Util.option("command.heal.sender", target);
			p.sendMessage(msg1);
			cs.sendMessage(msg2);
			return true;
		}
		
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			PlayerUtil.heal(p);
			String msg1 = Util.prefix + Util.option("command.heal.target");
			p.sendMessage(msg1);
			return true;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	private boolean feed(CommandSender cs, String[] args)
	{
		if(!PlayerUtil.hasPermission(cs, feedPerm)) return true;
		
		if(args.length > 0)
		{
			String permission = feedPerm + ".others";
			if(!PlayerUtil.hasPermission(cs, permission)) return true;
			
			String target = args[0];
			Player p = Bukkit.getPlayer(target);
			if(p == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", target);
				cs.sendMessage(msg);
				return true;
			}
			
			PlayerUtil.feed(p);
			String msg1 = Util.prefix + Util.option("command.feed.target");
			String msg2 = Util.prefix + Util.option("command.feed.sender", target);
			p.sendMessage(msg1);
			cs.sendMessage(msg2);
			return true;
		}
		
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			PlayerUtil.feed(p);
			String msg1 = Util.prefix + Util.option("command.feed.target");
			p.sendMessage(msg1);
			return true;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
}