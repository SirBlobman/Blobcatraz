package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;

public class CommandTime implements CommandExecutor 
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("btime"))
		{
			if(args.length < 1) {cs.sendMessage(Util.notEnoughArguments); return false;}
			
			if(args[0].equalsIgnoreCase("12")) cs.sendMessage(Util.blobcatraz + "The time is §7" + Util.getTime12());
			if(args[0].equalsIgnoreCase("24")) cs.sendMessage(Util.blobcatraz + "The time is §7" + Util.getTime24());
			
			return true;
		}
		if(label.equalsIgnoreCase("date"))
		{
			cs.sendMessage(Util.blobcatraz + "The date is §7" + Util.getDate());
			return true;
		}
		return false;
	}
}