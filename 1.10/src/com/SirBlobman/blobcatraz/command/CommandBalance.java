package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandBalance implements CommandExecutor
{
    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
    	if(label.equalsIgnoreCase("balance") || label.equalsIgnoreCase("bal") || label.equalsIgnoreCase("money"))
    	{
    		if(args.length == 1 && cs.hasPermission("blobcatraz.economy.balance.others"))
    		{
    			OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
    			if(p == null)
    			{
    				cs.sendMessage(Util.blobcatraz + args[0] + " is not a Player");
    				return true;
    			}
    			
    			double amount = ConfigDatabase.getBalance(p);
    			
    			cs.sendMessage(Util.blobcatraz + "§2" + p.getName() + " §rhas §2$" + amount);
    			return true;
    		}
    		else
    		{
    			if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
    			Player p = (Player) cs;
    			
    			double amount = ConfigDatabase.getBalance(p);
    			p.sendMessage(Util.blobcatraz + "You have §2$" + amount);
    			return true;
    		}
    	}
    	if(label.equalsIgnoreCase("baltop") || label.equalsIgnoreCase("balancetop"))
    	{
    		cs.sendMessage(Util.blobcatraz + "Top Ten Players: ");
    		for(String t : ConfigDatabase.getBalanceTopTen())
    		{
    			cs.sendMessage(t);
    		}
    		return true;
    	}
        return false;
    }
}
