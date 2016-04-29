package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vote implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args)
	{
		if (!(sender instanceof Player)) 
		{
			sender.sendMessage("§1[§6Blobcatraz§1]§r That command cannot be executed via the console.");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("vote"))
		{
			List<String> links = com.SirBlobman.blobcatraz.Blobcatraz.instance.getConfig().getStringList("vote.links");
			sender.sendMessage("§1[§6Blobcatraz§1]§r Vote Links:");
			for(String s : links)
			{
				p.sendMessage(s);
			}
			return true;
		}
		
		return false;
	}
}
