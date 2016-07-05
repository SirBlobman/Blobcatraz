package com.SirBlobman.blobcatraz.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandBalance implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args) 
	{
		if(label.equalsIgnoreCase("balance") || label.equalsIgnoreCase("bal") || label.equalsIgnoreCase("money"))
		{
			double amount;
			
			if(args.length == 1 && cs.hasPermission("blobcatraz.economy.balance.others"))
			{
				OfflinePlayer p2 = Bukkit.getOfflinePlayer(args[0]);
				if(p2 == null)
				{
					cs.sendMessage(Util.blobcatraz + args[0] + " is not a Player");
					return true;
				}
				UUID uuid2 = p2.getUniqueId();
				
				amount = ConfigDatabase.getPlayerBalance(uuid2);
				cs.sendMessage(Util.blobcatraz + "§2" + p2.getName() + " §rhas §2$" + amount);
				return true;
			}
			else
			{
				if(!(cs instanceof Player))
				{
					cs.sendMessage(Util.commandExecutorNotAPlayer);
					return true;
				}
				Player p = (Player) cs;
				
				amount = ConfigDatabase.getPlayerBalance(p.getUniqueId());
				p.sendMessage(Util.blobcatraz + "You have §2$" + amount);
				return true;
			}
		}
		return false;
	}

}