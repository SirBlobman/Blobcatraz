package com.SirBlobman.blobcatraz.command;

import java.text.NumberFormat;
import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;

public class CommandRandom implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(label.equalsIgnoreCase("random"))
		{
			Random r = new Random();
			String randomNumber = "";
			
			if(args.length == 0) randomNumber = NumberFormat.getIntegerInstance().format(r.nextInt(32767));
			if(args.length == 1)
			{
				if(args[0].equals("help"))
				{
					cs.sendMessage(c.getUsage());
					return true;
				}
				
				try
				{
					int max = Integer.parseInt(args[0]) + 1;
					randomNumber = NumberFormat.getIntegerInstance().format(r.nextInt(max));
				}
				catch (NumberFormatException ex)
				{
					cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a number");
					cs.sendMessage(Util.blobcatraz + "§5" + (Integer.MAX_VALUE - 1) + " §ris the largest possible number");
					return false;
				}
			}
			if(args.length == 2)
			{
				try
				{
					int min = Integer.parseInt(args[0]);
					int max = Integer.parseInt(args[1]);
					randomNumber = NumberFormat.getIntegerInstance().format(r.nextInt(max - min + 1) + min);
				}
				catch (NumberFormatException ex)
				{
					cs.sendMessage(Util.invalidArguments);
					cs.sendMessage(Util.blobcatraz + "§5" + (Integer.MAX_VALUE - 1) + " §ris the largest possible number");
					return false;
				}
				catch (IllegalArgumentException ex)
				{
					cs.sendMessage(Util.invalidArguments);
					cs.sendMessage(Util.blobcatraz + "Your minimum should be larger than your maximum");
					return false;
				}
			}
			if(args.length > 2)
			{
				cs.sendMessage(Util.tooManyArguments);
				return false;
			}
			
			cs.sendMessage(Util.blobcatraz + "Your random number is: §b" + randomNumber);
			return true;
		}
        return false;
    }
}