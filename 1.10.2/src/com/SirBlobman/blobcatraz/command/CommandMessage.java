package com.SirBlobman.blobcatraz.command;

import java.util.HashMap;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.google.common.collect.Maps;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandMessage implements CommandExecutor
{
	public static HashMap<CommandSender, CommandSender> reply = Maps.newHashMap();
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		switch(command)
		{
		case "tell":
			return msg(cs, args);
		case "reply":
			return reply(cs, args);
		default:
			return false;
		}
	}
	
	private boolean msg(CommandSender cs, String[] args)
	{
		if(args.length < 2)
		{
			cs.sendMessage(Util.NEA);
			return false;
		}
		
		String to = args[0];
		String msg = Util.format(Util.getFinal(args, 1));
		
		String mto = Util.message("command.msg.format.to");
		mto = mto.replace("{to}", to);
		mto = mto.replace("{message}", msg);
		
		String mfrom = Util.message("command.msg.format.from");
		mfrom = mfrom.replace("{from}", cs.getName());
		mfrom = mfrom.replace("{message}", msg);
		
		if(to.equalsIgnoreCase("console"))
		{
			String permission = "blobcatraz.msg.console";
			if(!PlayerUtil.hasPermission(cs, permission)) return true;
			ConsoleCommandSender console = Util.getConsole();
			console.sendMessage(mfrom);
			cs.sendMessage(mto);
			reply.put(cs, console);
			reply.put(console, cs);
			return true;
		}
		else
		{
			Player p = Bukkit.getPlayer(to);
			if(p == null)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + to + " §ris not a Player");
				return true;
			}
			p.sendMessage(mfrom);
			cs.sendMessage(mto);
			reply.put(cs, p);
			reply.put(p, cs);
			return true;
		}
	}
	
	private boolean reply(CommandSender cs, String[] args)
	{
		if(args.length < 1)
		{
			cs.sendMessage(Util.NEA);
			return false;	
		}
		
		String noReply = Util.blobcatraz + "You don't have anyone you can reply";
		
		if(reply.containsKey(cs))
		{	
			CommandSender to = reply.get(cs);
			
			String msg = Util.format(Util.getFinal(args, 0));
			
			String mto = Util.message("command.msg.format.to");
			mto = mto.replace("{to}", to.getName());
			mto = mto.replace("{message}", msg);
			
			String mfrom = Util.message("command.msg.format.from");
			mfrom = mfrom.replace("{from}", cs.getName());
			mfrom = mfrom.replace("{message}", msg);
			
			if(to instanceof OfflinePlayer)
			{
				OfflinePlayer op = (OfflinePlayer) cs;
				if(op.isOnline())
				{
					Player p = op.getPlayer();
					p.sendMessage(mfrom);
					cs.sendMessage(mto);
					reply.put(cs, p);
					reply.put(p, cs);
					return true;
				}
				cs.sendMessage(noReply);
				return true;
			}
			else
			{
				to.sendMessage(mfrom);
				cs.sendMessage(mto);
				reply.put(cs, to);
				reply.put(to, cs);
				return true;
			}
		}
		else
		{
			cs.sendMessage(noReply);
			return true;
		}
	}
}