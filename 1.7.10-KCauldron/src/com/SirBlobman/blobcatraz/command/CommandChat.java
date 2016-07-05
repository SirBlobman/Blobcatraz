package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.listeners.Chat;

public class CommandChat implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		ConfigBlobcatraz.loadConfig();
		
		String name = cs.getName();
		
		if(label.equalsIgnoreCase("chat"))
		{
			if(args.length == 0) {cs.sendMessage(Util.notEnoughArguments); return true;}
			if(args[0].equalsIgnoreCase("status"))
			{
				boolean status = ConfigBlobcatraz.config.getBoolean("chat.disabled");

				if(status == true) cs.sendMessage(Util.blobcatraz + "The chat is: §4OFF");
				if(status == false) cs.sendMessage(Util.blobcatraz + "The chat is: §2ON");
				
				return true;
			}
			if(args[0].equalsIgnoreCase("on"))
			{
				ConfigBlobcatraz.config.set("chat.disabled", false);
				ConfigBlobcatraz.saveConfig();
				Chat.globalMute = false;
				
				Util.broadcast("§5" + name + " §ehas §9UN-MUTED§e the chat!");
				return true;
			}
			if(args[0].equalsIgnoreCase("off"))
			{
				ConfigBlobcatraz.config.set("chat.disabled", true);
				ConfigBlobcatraz.saveConfig();
				Chat.globalMute = true;
				
				Util.broadcast("§5" + name + " §ehas §9MUTED§e the chat!");
			}
			if(args[0].equalsIgnoreCase("clear"))
			{
				int line = 1;
				while(line <= 500)
				{
					Bukkit.broadcastMessage("§1");
					line++;
				}
				
				Util.broadcast("§5" + name + " §9cleared§e the chat!");
				return true;
			}
		}
		return false;
	}
}