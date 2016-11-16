package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEconomy implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("economy"))
		{
			if(args.length > 1)
			{
				String sub = args[0].toLowerCase();
				switch(sub)
				{
				case "clearall":
					return clearall(cs);
				case "reset":
					return reset(cs, args);
				case "set":
					return set(cs, args);
				case "give":
					return give(cs, args);
				case "take":
					return take(cs, args);
				default: return false;
				}
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		return false;
	}
	
	private boolean clearall(CommandSender cs)
	{
		String permission = "blobcatraz.economy.clearall";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		ConfigDatabase.reload();
		ConfigDatabase.resetAllBalances();
		ConfigDatabase.reload();
		cs.sendMessage(Util.blobcatraz + "All balances have been reset!");
		return true;
	}
	
	private boolean reset(CommandSender cs, String[] args)
	{
		if(args.length >= 2)
		{
			String name = args[1];
			@SuppressWarnings("deprecation")
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);
			if(op == null)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
				return true;
			}
			
			ConfigDatabase.resetBalance(op);
			cs.sendMessage(Util.blobcatraz + "You reset the balance of " + op.getName());
		}
		cs.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean set(CommandSender cs, String[] args)
	{
		if(args.length >= 3)
		{
			String name = args[1];
			@SuppressWarnings("deprecation")
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);
			if(op == null)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
				return true;
			}
			
			double amount = 0.0D;
			try{amount = Double.parseDouble(args[2]);}
			catch(Exception ex)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + args[2] + " §ris not a Number");
				return true;
			}
			
			ConfigDatabase.setBalance(op, amount);
			String money = Util.monetize(amount);
			String msg = Util.blobcatraz + "§5" + op.getName() + "'s §rbalance has been set to §2" + money;
			String msg2 = Util.blobcatraz + "Your balance has been set to §2" + money;
			cs.sendMessage(msg);
			if(op.isOnline())
			{
				Player p = op.getPlayer();
				p.sendMessage(msg2);
			}
			return true;
		}
		cs.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean give(CommandSender cs, String[] args)
	{
		if(args.length >= 3)
		{
			String name = args[1];
			@SuppressWarnings("deprecation")
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);
			if(op == null)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
				return true;
			}
			
			double amount = 0.0D;
			try{amount = Double.parseDouble(args[2]);}
			catch(Exception ex)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + args[2] + " §ris not a Number");
				return true;
			}
			
			ConfigDatabase.addMoney(op, amount);
			String money = Util.monetize(amount);
			String msg = Util.blobcatraz + "§5" + op.getName() + " §rhas been given §2" + money;
			String msg2 = Util.blobcatraz + "You were given §2" + money;
			cs.sendMessage(msg);
			if(op.isOnline())
			{
				Player p = op.getPlayer();
				p.sendMessage(msg2);
			}
		}
		cs.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean take(CommandSender cs, String[] args)
	{
		if(args.length >= 3)
		{
			String name = args[1];
			@SuppressWarnings("deprecation")
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);
			if(op == null)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
				return true;
			}
			
			double amount = 0.0D;
			try{amount = Double.parseDouble(args[2]);}
			catch(Exception ex)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + args[2] + " §ris not a Number");
				return true;
			}
			
			ConfigDatabase.subtractMoney(op, amount);
			String money = Util.monetize(amount);
			String msg = Util.blobcatraz + "§2" + money + " §rwas taken from " + op.getName() + "'s account!";
			String msg2 = Util.blobcatraz + "You lost §2" + money;
			cs.sendMessage(msg);
			if(op.isOnline())
			{
				Player p = op.getPlayer();
				p.sendMessage(msg2);
			}
			return true;
		}
		cs.sendMessage(Util.NEA);
		return false;
	}
}