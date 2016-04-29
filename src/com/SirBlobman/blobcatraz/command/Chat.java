package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.listeners.ChatMute;

public class Chat implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args)
	{
		String nsender = sender.getName();
		
		if(label.equalsIgnoreCase("chat"))
		{
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("status"))
				{
					boolean status = com.SirBlobman.blobcatraz.Blobcatraz.instance.config.getBoolean("chat.disabled");
					if(status == true)
					{
						sender.sendMessage("§1[§6Blobcatraz§1]§r §eThe chat is: §4Off");
					}
					if(status == false)
					{
						sender.sendMessage("§1[§6Blobcatraz§1]§r §eThe chat is: §2On");
					}
				}
				if(args[0].equalsIgnoreCase("on"))
				{
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", false);
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", false);
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", false);
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", false);
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", false);
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", false);
					ChatMute.isGlobalMute = false;
					com.SirBlobman.blobcatraz.Blobcatraz.instance.saveConfig();
					com.SirBlobman.blobcatraz.Blobcatraz.instance.saveConfig();
					com.SirBlobman.blobcatraz.Blobcatraz.instance.saveConfig();
					com.SirBlobman.blobcatraz.Blobcatraz.instance.reloadConfig();
					Util.broadcast("§1[§6Blobcatraz§1]§r §5" + nsender + " §ehas §9un-muted§e the chat!");
					return true;
				}
				if(args[0].equalsIgnoreCase("off"))
				{
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", true);
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", true);
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", true);
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", true);
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", true);
					com.SirBlobman.blobcatraz.Blobcatraz.instance.config.set("chat.disabled", true);
					ChatMute.isGlobalMute = true;
					com.SirBlobman.blobcatraz.Blobcatraz.instance.saveConfig();
					com.SirBlobman.blobcatraz.Blobcatraz.instance.saveConfig();
					com.SirBlobman.blobcatraz.Blobcatraz.instance.saveConfig();
					com.SirBlobman.blobcatraz.Blobcatraz.instance.reloadConfig();
					Util.broadcast("§1[§6Blobcatraz§1]§r §5" + nsender + " §ehas §9muted§e the chat!");
					return true;
				}
				if(args[0].equalsIgnoreCase("clear"))
				{
					int line = 1;
					while(line <= 500)
					{
						Util.broadcast("§l");
						line++;
					}
					Util.broadcast("§1[§6Blobcatraz§1]§r §5" + nsender + " §ehas §9cleared§e the chat!");
					return true;
				}
			}
		}
		
		return false;
	}
}
