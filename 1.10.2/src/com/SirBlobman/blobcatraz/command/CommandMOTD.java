package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.config.ConfigLanguage;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMOTD implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("setmotd"))
		{
			if(args.length > 0)
			{
				String motd = Util.getFinal(args, 0);
				ConfigLanguage.forceMessage("blobcatraz.motd", motd);
				motd = Util.message("blobcatraz.motd");
				cs.sendMessage(Util.blobcatraz + "Changed the MOTD to §r" + motd);
				return true;
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		if(command.equals("getmotd"))
		{
			String motd = Util.message("blobcatraz.motd");
			cs.sendMessage(Util.blobcatraz + "The MOTD is: ");
			cs.sendMessage(motd);
			return true;
		}
		return false;
	}
}