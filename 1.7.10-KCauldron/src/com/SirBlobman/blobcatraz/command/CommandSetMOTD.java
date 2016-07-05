package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class CommandSetMOTD implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("setmotd"))
		{
			Util.setMOTD(Util.getFinalArg(args, 0));
			String motd = Util.color(ConfigBlobcatraz.config.getString("motd"));
			cs.sendMessage(Util.blobcatraz + "Changed MOTD to " + motd);
			return true;
		}
		return false;
	}
}