package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class CommandHeal implements CommandExecutor
{
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("heal"))
		{
			if(args.length == 0)
			{
				if(!(cs instanceof Player))
				{
					cs.sendMessage(Util.commandExecutorNotAPlayer);
					return true;
				}
				Player p = (Player) cs;
				
				if(p.hasPermission("blobcatraz.heal")) Util.heal(p);
				else p.sendMessage(Util.noPermission + "blobcatraz.heal");
				
				return true;
			}
			if(args.length == 1)
			{
				Player toHeal = Bukkit.getPlayer(args[0]);
				if(toHeal == null)
				{
					cs.sendMessage(Util.blobcatraz + args[0] + " is not a Player");
					return false;
				}
				
				Util.heal(toHeal);
				return true;
			}
			else
			{
				cs.sendMessage(Util.invalidArguments);
				return false;
			}
		}
		return false;
	}
}