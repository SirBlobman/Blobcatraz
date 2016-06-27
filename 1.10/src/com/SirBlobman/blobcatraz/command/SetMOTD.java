package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.BlobcatrazConfig;

public class SetMOTD implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) 
	{
		if(label.equalsIgnoreCase("setmotd"))
		{
			BlobcatrazConfig.config.set("motd", Util.getFinalArg(args, 0));
			BlobcatrazConfig.saveConfig();
			BlobcatrazConfig.loadConfig();
			String motd = Util.color(BlobcatrazConfig.config.getString("motd"));
			sender.sendMessage(Util.blobcatraz + "Changed MOTD to " + motd);
			return true;
		}
		
		return false;
	}
	
}
