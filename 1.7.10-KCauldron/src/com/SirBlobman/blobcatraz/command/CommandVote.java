package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class CommandVote implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player))
		{
			cs.sendMessage(Util.commandExecutorNotAPlayer);
			return true;
		}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("vote"))
		{
			List<String> links = ConfigBlobcatraz.config.getStringList("vote.links");
			p.sendMessage(Util.blobcatraz + "Vote Links:");
			for(String s : links) p.sendMessage(s);
			
			return true;
		}
		
		return false;
	}
}