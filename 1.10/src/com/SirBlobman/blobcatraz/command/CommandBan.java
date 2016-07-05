package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandBan implements CommandExecutor
{
    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
    	String banner = cs.getName();
    	
    	if(label.equalsIgnoreCase("ban") || label.equalsIgnoreCase("banish") || label.equalsIgnoreCase("exile"))
    	{
    		if(args.length < 2) {cs.sendMessage(Util.invalidArguments); return false;}
    		
    		OfflinePlayer toBeBanned = Bukkit.getOfflinePlayer(args[0]);
    		String reason = Util.getFinalArg(args, 1);
    		
    		if(!cs.hasPermission("blobcatraz.ban")) {cs.sendMessage(Util.noPermission + "blobcatraz.ban"); return true;}
    		if(toBeBanned == null) {cs.sendMessage(Util.blobcatraz + "§9" + args[0] + " §ris not a Player"); return true;}
    		
    		ConfigDatabase.ban(banner, toBeBanned, reason);
    		return true;
    	}
    	if(label.equalsIgnoreCase("tempban"))
    	{
    		if(args.length < 3) {cs.sendMessage(Util.invalidArguments); return false;}
    		
    		OfflinePlayer toBeBanned = Bukkit.getOfflinePlayer(args[0]);
    		if(!cs.hasPermission("blobcatraz.tempban")) {cs.sendMessage(Util.noPermission + "blobcatraz.tempban"); return true;}
    		try
    		{
    			String unit = args[1].substring(args[1].length() - 1);
    			int time = Integer.parseInt(args[1].substring(0, args[1].length() - 1));
    			
    			if(unit.equalsIgnoreCase("s")) ConfigDatabase.tempban(banner, toBeBanned, time * 1000L, Util.getFinalArg(args, 2));
    			if(unit.equalsIgnoreCase("m")) ConfigDatabase.tempban(banner, toBeBanned, time * 1000L * 60, Util.getFinalArg(args, 2));
    			if(unit.equalsIgnoreCase("h")) ConfigDatabase.tempban(banner, toBeBanned, time * 1000L * 60 * 60, Util.getFinalArg(args, 2));
    			if(unit.equalsIgnoreCase("d")) ConfigDatabase.tempban(banner, toBeBanned, time * 1000L * 60 * 60 * 24, Util.getFinalArg(args, 2));
    			if(unit.equalsIgnoreCase("w")) ConfigDatabase.tempban(banner, toBeBanned, time * 1000L * 60 * 60 * 24 * 7, Util.getFinalArg(args, 2));
    			if(unit.equalsIgnoreCase("y")) ConfigDatabase.tempban(banner, toBeBanned, time * 1000L * 60 * 60 * 24 * 7 * 4 * 12, Util.getFinalArg(args, 2));
    			if(unit.equalsIgnoreCase("c")) ConfigDatabase.tempban(banner, toBeBanned, time * 1000L * 60 * 60 * 24 * 7 * 4 * 12 * 10 * 10, Util.getFinalArg(args, 2));
    			return true;
    		}
    		catch (Exception ex)
    		{
    			cs.sendMessage(Util.invalidArguments);
    			return false;
    		}
    	}
    	if(label.equalsIgnoreCase("unban") || label.equalsIgnoreCase("pardon"))
    	{
    		if(!cs.hasPermission("blobcatraz.unban")) {cs.sendMessage(Util.noPermission + "blobcatraz.unban"); return true;}
    		
    		if(args.length == 1)
    		{
    			OfflinePlayer toUnban = Bukkit.getOfflinePlayer(args[0]);
    			if(toUnban == null) {cs.sendMessage(Util.blobcatraz + "§9" + args[0] + " §ris not a Player"); return true;}
    			
    			ConfigDatabase.unban(banner, toUnban);
    			return true;
    		}
    	}
        return false;
    }
}