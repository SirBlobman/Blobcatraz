package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.BlobcatrazConfig;

public class Vote implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args)
	{
		if (!(sender instanceof Player)) 
		{
			sender.sendMessage(Util.notAPlayer);
			return true;
		}
		
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("vote"))
		{
			List<String> links = BlobcatrazConfig.config.getStringList("vote.links");
			sender.sendMessage(Util.blobcatraz + "Vote Links:");
			for(String s : links)
			{
				p.sendMessage(s);
			}
			return true;
		}
		
		return false;
	}
}
