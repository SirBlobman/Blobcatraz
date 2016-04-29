package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;

public class SetMOTD implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) 
	{
		if(label.equalsIgnoreCase("setmotd"))
		{
			com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("motd", Util.getFinalArg(args, 0));
			com.SirBlobman.blobcatraz.Blobcatraz.instance.saveConfig();
			com.SirBlobman.blobcatraz.Blobcatraz.instance.saveConfig();
			com.SirBlobman.blobcatraz.Blobcatraz.instance.reloadConfig();
			String motd = Util.color(com.SirBlobman.blobcatraz.Blobcatraz.instance.config.getString("motd"));
			sender.sendMessage("§1[§6Blobcatraz§1]§r Changed MOTD to " + motd);
			return true;
		}
		
		return false;
	}
	
}
