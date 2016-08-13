package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandFreeze implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("freeze"))
		{
			if(!cs.hasPermission("blobcatraz.freeze"))
			{
				cs.sendMessage(Util.blobcatraz + "You are not allowed to freeze people!");
				return true;
			}
			if(args.length < 1) {cs.sendMessage(Util.notEnoughArguments); return false;}
			
			Player p = Bukkit.getPlayer(args[0]);
			if(p == null) {cs.sendMessage(Util.blobcatraz + "§4" + args[0] + " §ris not a Player"); return true;}
			
			ConfigDatabase.setFrozen(p, true);
			cs.sendMessage(Util.blobcatraz + "You froze §5" + p.getName());
			return true;
		}
		if(label.equalsIgnoreCase("unfreeze") || label.equalsIgnoreCase("defrost"))
		{
			if(!cs.hasPermission("blobcatraz.unfreeze"))
			{
				cs.sendMessage(Util.blobcatraz + "You are not allowed to unfreeze anyone!");
				return true;
			}
			if(args.length < 1) {cs.sendMessage(Util.notEnoughArguments); return false;}
			
			Player p = Bukkit.getPlayer(args[0]);
			if(p == null) {cs.sendMessage(Util.blobcatraz + "§4" + args[0] + " §ris not a Player"); return true;}
			
			ConfigDatabase.setFrozen(p, false);
			cs.sendMessage(Util.blobcatraz + "§5" + p.getName() + " has been unfrozen");
			return true;
		}
		return false;
	}
}