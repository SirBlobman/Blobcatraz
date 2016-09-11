package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.utility.TimeUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandTime implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("btime"))
		{
			if(args.length >= 1)
			{
				String sub = args[0].toLowerCase();
				String time = Util.blobcatraz + "Current Time: §3";
				String t12 = TimeUtil.time12();
				String t24 = TimeUtil.time24();
				if(sub.equals("12")) cs.sendMessage(time + t12);
				if(sub.equals("24")) cs.sendMessage(time + t24);
				
				return true;
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		if(command.equals("date"))
		{
			String date = Util.blobcatraz + "Current Date: §3";
			String today = TimeUtil.date();
			cs.sendMessage(date + today);
			return true;
		}
		return false;
	}
}