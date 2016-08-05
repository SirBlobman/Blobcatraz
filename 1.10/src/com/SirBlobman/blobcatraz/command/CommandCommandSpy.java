package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandCommandSpy implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("commandspy") || label.equalsIgnoreCase("spy"))
		{
			if(ConfigBlobcatraz.config.getBoolean("commandspy.enabled"))
			{
				ConfigDatabase.toggleCanSpy(p);
				ConfigDatabase.loadDatabase();
				if(ConfigDatabase.getCanSpy(p)) {p.sendMessage(Util.blobcatraz + "CommandSpy is on"); return true;}
				else {p.sendMessage(Util.blobcatraz + "CommandSpy is off"); return true;}
			}
			else
			{
				p.sendMessage(Util.blobcatraz + "CommandSpy is not enabled in the config!");
			}
		}
		return false;
	}
}