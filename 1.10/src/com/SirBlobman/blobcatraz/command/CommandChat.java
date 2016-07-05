package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;

public class CommandChat implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
    	String name = cs.getName();
    	ConfigBlobcatraz.loadConfig();
    	
    	if(label.equalsIgnoreCase("chat"))
    	{
    		if(args.length < 1) {cs.sendMessage(Util.notEnoughArguments); return true;}
    		if(args.length > 1) {cs.sendMessage(Util.tooManyArguments); return true;}
    		
    		if(args[0].equalsIgnoreCase("status"))
    		{
    			boolean status = ConfigBlobcatraz.config.getBoolean("chat.disabled");
    			if(status == true) cs.sendMessage(Util.blobcatraz + "§eThe chat is: §4Off");
    			if(status == false) cs.sendMessage(Util.blobcatraz + "§eThe chat is: §2On");
    			return true;
    		}
    		if(args[0].equalsIgnoreCase("on"))
    		{
    			ConfigBlobcatraz.config.set("chat.disabled", false);
    			ConfigBlobcatraz.saveConfig();
    			ConfigBlobcatraz.loadConfig();
    			Util.broadcast("§5" + name + " §ehas §9UNMUTED§e the chat!");
    			return true;
    		}
    		if(args[0].equalsIgnoreCase("off"))
    		{
    			ConfigBlobcatraz.config.set("chat.disabled", true);
    			ConfigBlobcatraz.saveConfig();
    			ConfigBlobcatraz.loadConfig();
    			Util.broadcast("§5" + name + " §ehas §9MUTED§e the chat!");
    			return true;
    		}
    		if(args[0].equalsIgnoreCase("clear"))
    		{
    			int line = 1;
    			while(line <= 500) {Bukkit.broadcastMessage("§1"); line++;}
    			
    			Util.broadcast("§5" + name + " §ehas §9CLEARED§e the chat!");
    			return true;
    		}
    	}
        return false;
    }
}