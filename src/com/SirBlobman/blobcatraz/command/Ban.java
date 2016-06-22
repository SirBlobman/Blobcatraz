package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.Database;

@SuppressWarnings({"deprecation"})
public class Ban implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("tempban"))
		{
			if(args.length < 3)
			{
				cs.sendMessage(Util.invalidArguments);
				return true;
			}

			OfflinePlayer toBeBanned = Bukkit.getOfflinePlayer(args[0]);
			try
			{
				String timeUnit = args[1].substring(args[1].length() - 1);
				int time = Integer.parseInt(args[1].substring(0, args[1].length() - 1));

				if(timeUnit.equalsIgnoreCase("s")) //Seconds
				{
						Database.tempban(toBeBanned, time * 1000L, Util.getFinalArg(args, 2)); 
						return true;
				}
				if(timeUnit.equalsIgnoreCase("m")) //Minutes
				{
					Database.tempban(toBeBanned, time * 60 * 1000L, Util.getFinalArg(args, 2)); 
					return true;
				}
				if(timeUnit.equalsIgnoreCase("h")) //Hours
				{
					Database.tempban(toBeBanned, time * 60 * 60 * 1000L, Util.getFinalArg(args, 2));
					return true;
				}
				if(timeUnit.equalsIgnoreCase("d")) //Days
				{
					Database.tempban(toBeBanned, time * 24 * 60 * 60 * 1000L, Util.getFinalArg(args, 2)); 
					return true;
				}
				if(timeUnit.equalsIgnoreCase("w")) //Weeks
				{
					Database.tempban(toBeBanned, time * 7 * 24 * 60 * 60 * 1000L, Util.getFinalArg(args, 2)); 
					return true;
				}
				if(timeUnit.equalsIgnoreCase("y")) //Years
				{
					Database.tempban(toBeBanned, time * 12 * 4 * 7 * 24 * 60 * 60 * 1000L, Util.getFinalArg(args, 2));
					return true;
				}
				if(timeUnit.equalsIgnoreCase("c")) //Centuries
					
				{
					Database.tempban(toBeBanned, time * 100 * 12 * 4 * 7 * 24 * 60 * 60 * 1000L, Util.getFinalArg(args, 2));
					broadcastTempban(cs, toBeBanned, Util.color(Util.getFinalArg(args, 2)));
					return true;
				}
				
				cs.sendMessage(Util.blobcatraz + "You don't have a time unit");
			}
			catch(NumberFormatException ex)
			{
				cs.sendMessage(Util.invalidArguments);
				return false;
			}
		}
		if(label.equalsIgnoreCase("ban") || label.equalsIgnoreCase("banish") || label.equalsIgnoreCase("exile"))
		{
			if(args.length < 2) 
			{
				cs.sendMessage(Util.notEnoughArguments); 
				return false;
			}

			OfflinePlayer banned = Bukkit.getOfflinePlayer(args[0]);
			String reason = Util.getFinalArg(args, 1);


			if(cs.hasPermission("blobcatraz.ban"))
			{
				if(banned == null)
				{
					cs.sendMessage(Util.blobcatraz + "§9" + args[0] + " §ris not a player");
					return true;
				}

				Database.ban(banned, reason);
				Util.broadcast("§6" + cs.getName() + " §rbanned §2" + banned.getName() + " §rfor: §e" + Util.color(reason));
				return true;
			}
			else
			{
				cs.sendMessage(Util.noPerms + "blobcatraz.ban");
				return true;
			}
		}

		if(label.equalsIgnoreCase("unban") || label.equalsIgnoreCase("pardon"))
		{
			if(cs.hasPermission("blobcatraz.unban"))
			{
				if(args.length == 1)
				{
					OfflinePlayer unbanned = Bukkit.getOfflinePlayer(args[0]);
					if(unbanned != null)
					{
						Database.unban(unbanned);
						Util.broadcast("§6" + cs.getName() + " §runbanned §2" + unbanned.getName());
						return true;
					}
					else
					{
						cs.sendMessage(Util.blobcatraz + "§9" + args[0] + " §ris not a player");
						return true;
					}
				}
			}
			else
			{
				cs.sendMessage(Util.noPerms + "blobcatraz.unban");
				return true;
			}
		}

		return false;
	}

	private void broadcastTempban(CommandSender cs, OfflinePlayer toBeBanned, String reason)
	{
		Util.broadcast(cs.getName() + "temporarily banned " + toBeBanned.getName() + " because: " + reason + "for: " + Database.getBanLengthFormatted(toBeBanned.getUniqueId()));
	}
}