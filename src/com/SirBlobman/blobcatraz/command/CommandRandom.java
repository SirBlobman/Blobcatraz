package com.SirBlobman.blobcatraz.command;

import java.text.NumberFormat;
import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;

public class CommandRandom implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("random"))
		{
			String random_number = null;
			
			if(args.length == 0)
			{
				Random r = new Random();
				random_number = NumberFormat.getIntegerInstance().format(r.nextInt(32767));
			}
			if(args.length == 1)
			{
				if(args[0].equals("help"))
				{
					sender.sendMessage(c.getUsage());
					return true;
				}
				
				Random rMax = new Random();
				try
				{
					int max = Integer.parseInt(args[0]) + 1;
					random_number = NumberFormat.getIntegerInstance().format(rMax.nextInt(max));
				}
				catch (NumberFormatException ex)
				{
					sender.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a number");
					sender.sendMessage(Util.blobcatraz + "§5" + (Integer.MAX_VALUE - 1) + " §ris the largest possible number");
					return false;
				}
				
			}
			if(args.length == 2)
			{
				Random rMaxMin = new Random();
				try
				{
					int min = Integer.parseInt(args[0]);
					int max = Integer.parseInt(args[1]);
					random_number = NumberFormat.getIntegerInstance().format(rMaxMin.nextInt(max - min + 1) + min);
				}
				catch (NumberFormatException ex)
				{
					sender.sendMessage(Util.invalidArguments);
					sender.sendMessage(Util.blobcatraz + "§5" + (Integer.MAX_VALUE - 1) + " §ris the largest possible number");
					return false;
				}
				catch (IllegalArgumentException ex)
				{
					sender.sendMessage(Util.invalidArguments);
					sender.sendMessage(Util.blobcatraz + "Your minimum should be larger than your maximum");
					return false;
				}
			}
			if(args.length == 3)
			{
				sender.sendMessage(Util.tooManyArguments);
				return false;
			}
			
			sender.sendMessage(Util.blobcatraz + "Your random number is: §b" + random_number);
			return true;
		}
		return false;
	}
}
