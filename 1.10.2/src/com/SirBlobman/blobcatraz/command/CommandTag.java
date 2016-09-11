package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTag implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("tag"))
			{
				String permission = "bobcatraz.tag";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				if(args.length < 1)
				{
					p.setPlayerListName(null);
					p.setCustomName(null);
					p.sendMessage(Util.blobcatraz + "Your name in TAB is now back to default");
					return true;
				}
				
				String name = Util.getFinal(args, 0);
				name = Util.format(name);
				if(name.length() > 16) name = name.substring(0, 16);
				p.setPlayerListName(name);
				p.setCustomName(name);
				p.sendMessage(Util.blobcatraz + "Your name is now " + name);
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}