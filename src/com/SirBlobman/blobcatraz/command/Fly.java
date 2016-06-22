package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class Fly implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args) 
	{
		if(!(cs instanceof Player))
		{
			cs.sendMessage(Util.notAPlayer);
			return true;
		}
		Player p = (Player)cs;

		if(label.equalsIgnoreCase("fly") || label.equalsIgnoreCase("soar"))
		{
			if(args.length == 1)
			{
				if(!p.hasPermission("blobcatraz.fly.toggle"))
				{
					p.sendMessage(Util.noPerms + "blobcatraz.fly.toggle");
					return true;
				}
				if(args[0].equalsIgnoreCase("off"))
				{
					p.setAllowFlight(false);
					p.sendMessage(Util.blobcatraz + "Flight has been §4DISABLED§r");
					return true;
				}
				if(args[0].equals("on"))
				{
					p.setAllowFlight(true);
					p.sendMessage(Util.blobcatraz + "Flight has been §2ENABLED§r");
					return true;
				}
				return false;
			}

			String on = "Your flight is §2ENABLED";
			String off = "Your flight is §4DISABLED";
			if(p.getAllowFlight())
			{
				p.sendMessage(Util.blobcatraz + on);
			}
			else
			{
				p.sendMessage(Util.blobcatraz + off);
			}
			return true;
		}
		return false;
	}	
}
