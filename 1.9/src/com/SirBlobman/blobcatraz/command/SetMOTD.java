package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class SetMOTD implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) 
	{
		if(label.equalsIgnoreCase("setmotd"))
		{
			Blobcatraz.instance.config.set("motd", Util.getFinalArg(args, 0));
			Blobcatraz.instance.saveConfig();
			Blobcatraz.instance.saveConfig();
			Blobcatraz.instance.reloadConfig();
			String motd = Util.color(Blobcatraz.instance.config.getString("motd"));
			sender.sendMessage(Util.blobcatraz + "Changed MOTD to " + motd);
			return true;
		}
		
		return false;
	}
	
}
