package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;

public class CommandMob implements CommandExecutor 
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("mob") || label.equalsIgnoreCase("spawnmob"))
		{
			String permission = "blobcatraz.mob";
			if(!Util.hasPermission(cs, permission)) return true;
			if(args.length == 0) {cs.sendMessage(Util.notEnoughArguments); return false;}
			return spawn(cs, args);
		}
		return false;
	}
	
	private boolean spawn(CommandSender cs, String[] args)
	{
		switch(args.length)
		{
		case 1:
		{

		}
		case 2:
		{
			
		}
		case 3:
		{
			
		}
		}
		return true;
	}
}