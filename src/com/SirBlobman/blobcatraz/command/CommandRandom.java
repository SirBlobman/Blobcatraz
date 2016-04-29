package com.SirBlobman.blobcatraz.command;

import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRandom implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("random"))
		{
			int random_number;
			
			Random r = new Random();
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("help"))
				{
					sender.sendMessage(c.getUsage());
					return true;
				}
			}
			
			if(args.length == 2)
			{
				int min = Integer.parseInt(args[0]);
				int max = Integer.parseInt(args[1]);
				random_number = r.nextInt(max - min + 1) + min;
			}
			else
			{
				random_number = r.nextInt(32767);
			}
			sender.sendMessage("§1[§6Blobcatraz§1]§r Your random number is: " + random_number);
			return true;
		}
		
		return false;
	}

}
