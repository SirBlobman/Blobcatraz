package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.Database;

@SuppressWarnings({"deprecation"})
public class Balance implements CommandExecutor
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
		
		if(label.equalsIgnoreCase("balance") || label.equalsIgnoreCase("bal") || label.equalsIgnoreCase("money"));
		{
			if(args.length == 1 && p.hasPermission("blobcatraz.economy.balance.others"))
			{
				OfflinePlayer p2 = Bukkit.getOfflinePlayer(args[0]);
				if(p2 == null)
				{
					p.sendMessage(Util.blobcatraz + args[0] + "is not a Player");
					return true;
				}
				double amount = Database.getPlayerBalance(p2.getUniqueId());
				
				p.sendMessage(Util.blobcatraz + "§2" + p2.getName() + " §rhas §2$" + amount);
				return true;
			}
			else
			{
				double amount = Database.getPlayerBalance(p.getUniqueId());
				
				p.sendMessage(Util.blobcatraz + "You have §2$" + amount);
				return true;
			}
		}
	}
}