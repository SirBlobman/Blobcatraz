package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

@SuppressWarnings("deprecation")
public class CommandEconomy implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("economy"))
		{
			if(args.length > 0)
			{
				String sub = args[0].toLowerCase();
				switch(sub)
				{
				case "clearall": return clearall(cs);
				case "reset": return reset(cs, args);
				case "set": return set(cs, args);
				case "give": return give(cs, args);
				case "take": return take(cs, args);
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
		
		ConfigDatabase.resetAllBalances();
		cs.sendMessage(Util.prefix + Util.option("command.economy.all reset"));
		return true;
	}
	
	private boolean reset(CommandSender cs, String[] args)
	{
		if(args.length > 1)
		{
			String name = args[1];
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);
			if(op == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", name);
				cs.sendMessage(msg);
				return true;
			}
			
			ConfigDatabase.resetBalance(op);
			String msg = Util.prefix + Util.option("command.economy.reset", op.getName());
			cs.sendMessage(msg);
			return true;
		}
		cs.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean set(CommandSender cs, String[] args)
	{
		if(args.length > 2)
		{
			String name = args[1];
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);
			if(op == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", name);
				cs.sendMessage(msg);
				return true;
			}
			
			String amt = args[2];
			double amount = 0.0D;
			try{amount = Double.parseDouble(amt);}
			catch(Exception ex)
			{
				String msg = Util.prefix + Util.option("error.economy.not a number", amt);
				cs.sendMessage(msg);
				return true;
			}
			
			ConfigDatabase.setBalance(op, amount);
			String money = Util.monetize(amount);
			String msg = Util.prefix + Util.option("command.economy.set.success", op.getName(), money);
			String msg2 = Util.prefix + Util.option("command.economy.set.online", money);
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
		if(args.length > 2)
		{
			String name = args[1];
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);
			if(op == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", name);
				cs.sendMessage(msg);
				return true;
			}
			
			String amt = args[2];
			double amount = 0.0D;
			try{amount = Double.parseDouble(amt);}
			catch(Exception ex)
			{
				String msg = Util.prefix + Util.option("error.economy.not a number", amt);
				cs.sendMessage(msg);
				return true;
			}
			
			ConfigDatabase.deposit(op, amount);
			String money = Util.monetize(amount);
			String msg = Util.prefix + Util.option("command.economy.give.success", op.getName(), money);
			String msg2 = Util.prefix + Util.option("command.economy.give.online", money);
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
	
	private boolean take(CommandSender cs, String[] args)
	{
		if(args.length > 2)
		{
			String name = args[1];
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);
			if(op == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", name);
				cs.sendMessage(msg);
				return true;
			}
			
			String amt = args[2];
			double amount = 0.0D;
			try{amount = Double.parseDouble(amt);}
			catch(Exception ex)
			{
				String msg = Util.prefix + Util.option("error.economy.not a number", amt);
				cs.sendMessage(msg);
				return true;
			}
			
			ConfigDatabase.withdraw(op, amount);
			String money = Util.monetize(amount);
			String msg = Util.prefix + Util.option("command.economy.take.success", op.getName(), money);
			String msg2 = Util.prefix + Util.option("command.economy.take.online", money);
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