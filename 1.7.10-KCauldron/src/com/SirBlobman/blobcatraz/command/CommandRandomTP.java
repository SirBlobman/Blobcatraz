package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.gui.RandomTPGui;

public class CommandRandomTP implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		ConfigBlobcatraz.loadConfig();
		if(!(cs instanceof Player))
		{
			cs.sendMessage(Util.commandExecutorNotAPlayer);
			return true;
		}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("randomtp") || label.equalsIgnoreCase("rtp"))
		{
			if(args.length == 0)
			{
				p.sendMessage(Util.blobcatraz + "§5/randomtp tp§f to teleport randomly");
				p.sendMessage(Util.blobcatraz + "§5/randomtp gui§f to see the GUI");
				return true;
			}
			if(args[0].equalsIgnoreCase("tp"))
			{
				List<String> enabledWorlds = ConfigBlobcatraz.config.getStringList("randomtp.enabledWorlds");
				if(enabledWorlds.contains(p.getWorld().getName())) Util.normalRandomTP(p);
				else p.sendMessage(Util.notEnabledInWorld);
				return true;
			}
			if(args[0].equalsIgnoreCase("gui"))
			{
				List<String> enabledWorlds = ConfigBlobcatraz.config.getStringList("randomtp.enabledWorlds");
				
				if(enabledWorlds.contains(p.getWorld().getName())) RandomTPGui.open(p);
				else p.sendMessage(Util.notEnabledInWorld);
				
				return true;
			}
		}
		return false;
	}
}