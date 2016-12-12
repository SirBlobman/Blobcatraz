package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandBalance implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		switch(cmd)
		{
		case "balance": return balance(cs, args);
		case "baltop": return baltop(cs);
		case "withdraw": return withdraw(cs, args);
		default: return false;
		}
	}
	
	private boolean balance(CommandSender cs, String[] args)
	{
		if(args.length > 0)
		{
			String permission = "blobcatraz.balance.others";
			if(!PlayerUtil.hasPermission(cs, permission)) return true;
			
			String name = args[0];
			@SuppressWarnings("deprecation")
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);
			if(op == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", name);
				cs.sendMessage(msg);
				return true;
			}
			
			double amount = ConfigDatabase.balance(op);
			String display = Util.monetize(amount);
			String msg = Util.prefix + Util.option("command.balance.other", op.getName(), display);
			cs.sendMessage(msg);
			return true;
		}
		else
		{
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				double balance = ConfigDatabase.balance(p);
				String display = Util.monetize(balance);
				String msg = Util.prefix + Util.option("command.balance.self", display);
				p.sendMessage(msg);
				return true;
			}
			cs.sendMessage(Util.notPlayer);
			return true;
		}
	}
	
	private boolean baltop(CommandSender cs)
	{
		String msg = Util.prefix + "Top Players: ";
		cs.sendMessage(msg);
		List<String> list = ConfigDatabase.balTop();
		for(String s : list) cs.sendMessage(s);
		return true;
	}
	
	private boolean withdraw(CommandSender cs, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			if(args.length > 0)
			{
				String amt = args[0];
				double amount = 0.0D;
				try {amount = Double.parseDouble(amt);}
				catch(Exception ex)
				{
					String msg = Util.prefix + Util.option("error.withdraw", amt);
					p.sendMessage(msg);
					return true;
				}
				
				double balance = ConfigDatabase.balance(p);
				if(balance >= amount)
				{
					ItemStack is = new ItemStack(Material.DOUBLE_PLANT);
					ItemMeta meta = is.getItemMeta();
					String name = Util.option("item.bank note.name");
					meta.setDisplayName(name);
					meta.setLore(Arrays.asList("§2" + Util.monetize(amount)));
					is.setItemMeta(meta);
					
					PlayerInventory pi = p.getInventory();
					Map<Integer, ItemStack> left = pi.addItem(is);
					if(left.isEmpty()) ConfigDatabase.withdraw(p, amount);
					else
					{
						String msg = Util.prefix + Util.option("player.inventory.full");
						p.sendMessage(msg);
					}
					return true;
				}
				cs.sendMessage(Util.prefix + Util.option("error.economy.not enough money"));
				return true;
			}
			cs.sendMessage(Util.NEA);
			return true;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
}