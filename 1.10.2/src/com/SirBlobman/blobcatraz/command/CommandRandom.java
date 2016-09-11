package com.SirBlobman.blobcatraz.command;

import java.text.NumberFormat;
import java.util.Random;

import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRandom implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("random"))
		{
			Random random = new Random();
			NumberFormat gii = NumberFormat.getIntegerInstance();
			String number = "";
			
			if(args.length == 0)
			{
				int r = random.nextInt(32767);
				number = gii.format(r);
			}
			if(args.length == 1)
			{
				String sub = args[0].toLowerCase();
				if(sub.equals("help"))
				{
					cs.sendMessage(c.getUsage());
					return true;
				}
				
				try
				{
					int max = Integer.parseInt(args[0]);
					int r = random.nextInt(max);
					number = gii.format(r);
				} catch(NumberFormatException ex)
				{
					cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Number");
					cs.sendMessage(Integer.MAX_VALUE + " §ris the largest possible number");
					return false;
				}
			}
			if(args.length == 2)
			{
				try
				{
					int min = Integer.parseInt(args[0]);
					int max = Integer.parseInt(args[1]);
					int r = random.nextInt(max - min + 1) + min;
					number = gii.format(r);
				} catch(NumberFormatException nfe)
				{
					cs.sendMessage(Util.IA);
					cs.sendMessage("§5" + Integer.MAX_VALUE + " §ris the largest possible number");
					return false;
				} catch(IllegalArgumentException iae)
				{
					cs.sendMessage(Util.IA);
					cs.sendMessage("Your min should be larger than your max");
					return false;
				}
			}
			if(args.length > 2)
			{
				cs.sendMessage(Util.TMA);
				return false;
			}
			
			cs.sendMessage(Util.blobcatraz + "Random Number: §b" + number);
			return true;
		}
		return false;
	}
}