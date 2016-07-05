package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandEconomy implements CommandExecutor
{
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("economy") || label.equalsIgnoreCase("eco"))
		{
			if(args.length < 1) {cs.sendMessage(Util.notEnoughArguments); return false;}
			if(args.length > 3) {cs.sendMessage(Util.tooManyArguments); return false;}

			if(args[0].equalsIgnoreCase("clearall"))
			{
				ConfigDatabase.clearBalances();;
				cs.sendMessage(Util.blobcatraz + "All balances have been cleared");
				return true;
			}
			if(args[0].equalsIgnoreCase("reset"))
			{
				if(args.length != 2) {cs.sendMessage(Util.invalidArguments); return false;}

				Player p = Bukkit.getPlayer(args[1]);
				if(p == null) {cs.sendMessage(Util.blobcatraz + "§9" + args[1] + " §ris not a Player"); return true;}

				ConfigDatabase.resetPlayerBalance(p.getUniqueId());;
				return true;
			}

			if(args.length != 3) {cs.sendMessage(Util.invalidArguments); return false;}

			Player p = Bukkit.getPlayer(args[1]);
			if(p == null) {cs.sendMessage(Util.blobcatraz + "§9" + args[1] + " §ris not a Player"); return true;}
			double amount;
			try{amount = Double.parseDouble(args[2]);}
			catch(Exception ex) {cs.sendMessage(Util.blobcatraz + "§9" + args[2] + " §ris not a Number"); return true;}


			if(args[0].equalsIgnoreCase("set"))
			{
				ConfigDatabase.setPlayerBalance(p.getUniqueId(), amount);;
				cs.sendMessage(Util.blobcatraz + "§9" + p.getName() + "'s §rbalance has been set to §2$" + amount);
				p.sendMessage(Util.blobcatraz + "Your balance has been set to §2$" + amount);
				return true;
			}
			if(args[0].equalsIgnoreCase("give"))
			{
				ConfigDatabase.addToPlayerBalance(p.getUniqueId(), amount);;
				cs.sendMessage(Util.blobcatraz + "§9" + p.getName() + " §rhas been given §2$" + amount);
				p.sendMessage(Util.blobcatraz + "You have been given §2$" + amount);
				return true;
			}
			if(args[0].equalsIgnoreCase("take"))
			{
				ConfigDatabase.subtractFromPlayerBalance(p.getUniqueId(), amount);
				cs.sendMessage(Util.blobcatraz + "§2$" + amount + " §rhas been taken from §9" + p.getName() + "'s §raccount");
				p.sendMessage(Util.blobcatraz + "§2$" + amount + " §r has been taken from your account");
				return true;
			}
		}
		return false;
	}
}