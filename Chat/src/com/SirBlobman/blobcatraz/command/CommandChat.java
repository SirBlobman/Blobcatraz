package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.config.ConfigChat;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandChat implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("chat"))
		{
			if(args.length > 0)
			{
				String sub = args[0].toLowerCase();
				switch(sub)
				{
				case "on": return on(cs);
				case "off": return off(cs);
				case "clear": return clear(cs);
				case "check": return check(cs);
				default:
				{
					cs.sendMessage(Util.IA);
					return false;
				}
				}
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		return false;
	}
	
	private boolean on(CommandSender cs)
	{
		String permission = "blobcatraz.chat.mute";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		ConfigChat.set("disabled", false, true);
		String msg = Util.option("command.unmute.success", cs.getName());
		Util.broadcast(msg);
		return true;
	}
	
	private boolean off(CommandSender cs)
	{
		String permission = "blobcatraz.chat.mute";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		ConfigChat.set("disabled", true, true);
		String msg = Util.option("command.mute.success", cs.getName());
		Util.broadcast(msg);
		return true;
	}
	
	private boolean clear(CommandSender cs)
	{
		String permission = "blobcatraz.chat.clear";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		int line = 0;
		while(line < 500)
		{
			Bukkit.broadcastMessage("§f");
			line++;
		}
		Bukkit.broadcastMessage(Util.prefix + Util.option("command.chat.clear", cs.getName()));
		return true;
	}
	
	private boolean check(CommandSender cs)
	{
		return true;
	}
}