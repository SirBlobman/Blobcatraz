package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class CommandTag implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("tag"))
		{
			if(!p.hasPermission("blobcatraz.tag")) {p.sendMessage(Util.noPermission + "blobcatraz.tag"); return true;}
			if(args.length < 1) {p.setPlayerListName(null); return true;}
			String newName = Util.color(Util.getFinalArg(args, 0));
			if(newName.length() > 16) newName = newName.substring(0, 16);
			p.setPlayerListName(newName);
			p.sendMessage(Util.blobcatraz + "Your name is now " + newName);
			return true;
		}
		return false;
	}
}