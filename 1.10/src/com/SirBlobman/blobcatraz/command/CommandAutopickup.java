package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandAutopickup implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("autopickup") || label.equalsIgnoreCase("ap"))
		{
			if(!p.hasPermission("blobcatraz.autopickup.toggle")) {Util.noPermission(p, "blobcatraz.autopickup.toogle"); return true;}
			if(ConfigDatabase.toggleAutoPickup(p)) {p.sendMessage(Util.blobcatraz + "Your Auto Pickup is now §2§lEnabled");}
			else {p.sendMessage(Util.blobcatraz + "Your Auto Pickup is now §4§lDisabled");}
			return true;
		}
		return false;
	}
}