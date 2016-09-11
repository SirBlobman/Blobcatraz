package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFreeze implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		switch(command)
		{
		case "freeze":
			return freeze(cs, args);
		case "unfreeze":
			return unfreeze(cs, args);
		default:
			return false;
		}
	}
	
	private boolean freeze(CommandSender cs, String[] args)
	{
		String permission = "blobcatraz.freeze";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		if(args.length >= 1)
		{
			String target = args[0];
			Player p = Bukkit.getPlayer(target);
			if(target != null)
			{
				ConfigDatabase.setFrozen(p, true);
				cs.sendMessage(Util.blobcatraz + "You froze §5" + p.getDisplayName());
				p.sendMessage(Util.blobcatraz + "You have been frozen for breaking the rules!");
				return true;
			}
			cs.sendMessage(Util.blobcatraz + "§5" + target + " §ris not a Player");
			return true;
		}
		cs.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean unfreeze(CommandSender cs, String[] args)
	{
		String permission = "blobcatraz.unfreeze";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		if(args.length >= 1)
		{
			String target = args[0];
			Player p = Bukkit.getPlayer(target);
			if(target != null)
			{
				ConfigDatabase.setFrozen(p, false);
				cs.sendMessage(Util.blobcatraz + "You UNfroze §5" + p.getDisplayName());
				p.sendMessage(Util.blobcatraz + "Your character has defrosted!");
				return true;
			}
			cs.sendMessage(Util.blobcatraz + "§5" + target + " §ris not a Player");
			return true;
		}
		cs.sendMessage(Util.NEA);
		return false;
	}
}