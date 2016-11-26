package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandAFK implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String name = p.getName();
			String cmd = c.getName().toLowerCase();
			if(cmd.equals("afk"))
			{
				if(args.length == 0)
				{
					ConfigDatabase.afk(p, true);
					String msg = Util.format("&6&l* &7%s is now AFK", name);
					Bukkit.broadcastMessage(msg);
					return true;
				}
				else
				{
					ConfigDatabase.afk(p, true);
					String reason = Util.getFinal(args, 0);
					String format = Util.format(reason);
					String msg = Util.format("&6&l* &7%s is now AFK:\n %s", name, format);
					Bukkit.broadcastMessage(msg);
					return true;
				}
			}
			return false;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
}