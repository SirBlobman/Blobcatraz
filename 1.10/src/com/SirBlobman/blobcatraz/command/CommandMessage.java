package com.SirBlobman.blobcatraz.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.listener.Emojis;
import com.google.common.collect.Maps;

public class CommandMessage implements CommandExecutor
{
	public static HashMap<CommandSender, CommandSender> reply = Maps.newHashMap();
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("msg") || label.equalsIgnoreCase("tell"))
		{
			if(args.length < 2) {cs.sendMessage(Util.notEnoughArguments); return false;}
			Player reciever = Bukkit.getPlayer(args[0]);
			if(reciever == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Player"); return true;}
			String msg = Util.color(Util.getFinalArg(args, 1));
			reciever.sendMessage("[" + cs.getName() + " §7" + Emojis.getString(Emojis.doubleArrow) + " §rme] " + msg);
			cs.sendMessage("[me §7" + Emojis.getString(Emojis.doubleArrow) + " §r" + reciever.getName() + "] " + msg);
			reply.put(cs, reciever);
			reply.put(reciever, cs);
			return true;
		}
		if(label.equalsIgnoreCase("reply") || label.equalsIgnoreCase("r"))
		{
			if(args.length < 1) {cs.sendMessage(Util.notEnoughArguments); return false;}
			if(!reply.containsKey(cs)) {cs.sendMessage(Util.blobcatraz + "You have no one to reply to!"); return true;}
			CommandSender target = reply.get(cs);
			String msg = Util.color(Util.getFinalArg(args, 0));
			target.sendMessage("[" + cs.getName() + " §7" + Emojis.getString(Emojis.doubleArrow) + " §rme] " + msg);
			cs.sendMessage("[me §7" + Emojis.getString(Emojis.doubleArrow) + " §r" + target.getName() + "] " + msg);
			return true;
		}
		return false;
	}
}