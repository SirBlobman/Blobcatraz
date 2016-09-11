package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAFK implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String name = p.getName();
			String command = c.getName();
			if(command.equalsIgnoreCase("afk"))
			{
				if(args.length == 0)
				{
					String msg = Util.format("&6&l* &7" + name + " &6is now AFK");
					Bukkit.broadcastMessage(msg);
					ConfigDatabase.setAFK(p, true);
				}
				else
				{
					String reason = Util.getFinal(args, 0);
					String reason1 = Util.format(reason);
					String msg = Util.format("&6&l* &7" + name + " &6is now AFK:");
					String msg2 = " " + reason1;
					Bukkit.broadcastMessage(msg);
					Bukkit.broadcastMessage(msg2);
					ConfigDatabase.setAFK(p, true);
				}
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}