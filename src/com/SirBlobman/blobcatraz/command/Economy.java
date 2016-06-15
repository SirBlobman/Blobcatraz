package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.economy.Database;

public class Economy implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player))
		{
			cs.sendMessage(Util.notAPlayer);
			return true;
		}
		
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("economy"))
		{
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("clearall"))
				{
					Database.clearBalances();
					p.sendMessage(Util.blobcatraz + "All balances have been cleared");;
					return true;
				}
			}
			if(args.length == 2)
			{
				if(args[0].equalsIgnoreCase("reset"))
				{
					Player p2 = Bukkit.getPlayer(args[1]);
					Database.resetPlayerBalance(p2.getUniqueId());
					return true;
				}
			}
			if(args.length == 3)
			{
				Player p2 = Bukkit.getServer().getPlayer(args[1]);
				if(p2 == null || !p2.isOnline())
				{
					p.sendMessage(Util.blobcatraz + "§2" + args[1] + "§ris not Online");
					return true;
				}
				double amount = Double.parseDouble(args[2]);
				
				if(args[0].equalsIgnoreCase("set"))
				{
					Database.setPlayerBalance(p2.getUniqueId(), amount);
					p.sendMessage(Util.blobcatraz + "§9" + p2.getName() + "'s §rbalance has been set to §2$" + amount);
					p2.sendMessage(Util.blobcatraz + "Your balance has been set to §2$" + amount);
					return true;
				}
				if(args[0].equalsIgnoreCase("give"))
				{
					Database.addToPlayerBalance(p2.getUniqueId(), amount);
					p.sendMessage(Util.blobcatraz + "§9" + p2.getName() + " §rhas been given §2$" + amount);
					p2.sendMessage(Util.blobcatraz + "You have been given §2$" + amount);
					return true;
				}
				if(args[0].equalsIgnoreCase("take"))
				{
					Database.subtractFromPlayerBalance(p2.getUniqueId(), amount);
					p.sendMessage(Util.blobcatraz + "§2$" + amount + " §rhas been taken from §9" + p2.getName() + "'s §raccount");
					p2.sendMessage(Util.blobcatraz + "§2$" + amount + " §r has been taken from your account");
					return true;
				}
			}
			else
			{
				p.sendMessage(Util.notEnoughArguments);
				return false;
			}
		}
		
		return false;
	}
}
