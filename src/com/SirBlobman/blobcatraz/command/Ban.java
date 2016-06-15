package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.economy.Database;

@SuppressWarnings({"deprecation"})
public class Ban implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{

		if(label.equalsIgnoreCase("ban"))
		{
			if(args.length < 2) 
			{
				cs.sendMessage(Util.notEnoughArguments); 
				return false;
			}

			OfflinePlayer banned = Bukkit.getOfflinePlayer(args[0]);
			String reason = Util.color(Util.getFinalArg(args, 1));


			if(cs.hasPermission("blobcatraz.ban"))
			{
				if(banned == null)
				{
					cs.sendMessage(Util.blobcatraz + "§9" + args[0] + " §ris not a player");
					return true;
				}

				Database.banPlayer(banned, reason);
				Util.broadcast("§6" + cs.getName() + " §rbanned §2" + banned.getName() + " §rfor: §e" + reason);
				return true;
			}
			else
			{
				cs.sendMessage(Util.noPerms + "blobcatraz.ban");
				return true;
			}
		}

		if(label.equalsIgnoreCase("unban"))
		{
			if(cs.hasPermission("blobcatraz.unban"))
			{
				if(args.length == 1)
				{
					OfflinePlayer unbanned = Bukkit.getOfflinePlayer(args[0]);
					if(unbanned != null)
					{
						Database.unbanPlayer(unbanned);
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
}