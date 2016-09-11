package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandBalance implements CommandExecutor,Listener
{
	@EventHandler
	public void deposit(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		Action a = e.getAction();
		Action d1 = Action.RIGHT_CLICK_AIR;
		Action d2 = Action.RIGHT_CLICK_BLOCK;
		if(a.equals(d1) || a.equals(d2))
		{
			ItemStack held = ItemUtil.getHeld(p);
			if(held == null) return;
			String name = ItemUtil.getName(held);
			String check = Util.message("item.banknote.name");
			if(name.equals(check))
			{
				List<String> lore = ItemUtil.getLore(held);
				if(lore == null) return;
				try
				{
					String money = lore.get(0);
					if(!money.contains("$")) return;
					money = Util.uncolor(money).replace("$", "").replace(",", "");
					double cash = Double.parseDouble(money);
					int amount = held.getAmount();
					if(amount == 1)
					{
						PlayerInventory pi = p.getInventory();
						pi.setItemInMainHand(new ItemStack(Material.AIR));
					}
					held.setAmount(amount - 1);
					ConfigDatabase.addMoney(p, cash);
					String deposit = Util.monetize(cash);
					p.sendMessage(Util.blobcatraz + "You deposited §2" + deposit);
					e.setCancelled(true);
				} catch(Exception ex)
				{
					p.sendMessage(Util.blobcatraz + "Invalid Bank Note");
				}
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		switch(command)
		{
		case "balance":
			return balance(cs, args);
		case "baltop":
			return baltop(cs);
		case "withdraw":
			return withdraw(cs, args);
		default:
			return false;
		}
	}
	
	private boolean balance(CommandSender cs, String[] args)
	{
		String permission = "blobcatraz.economy.balance.others";
		if(args.length > 0)
		{
			if(!PlayerUtil.hasPermission(cs, permission)) return true;
			@SuppressWarnings("deprecation")
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
			if(p == null)
			{
				cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Player");
				return true;
			}
			
			double amount = ConfigDatabase.getBalance(p);
			String display = Util.monetize(amount);
			String msg = Util.blobcatraz + "§5" + p.getName() + " §rhas §2" + display;
			cs.sendMessage(msg);
			return true;
		}
		else
		{
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				
				double amount = ConfigDatabase.getBalance(p);
				String display = Util.monetize(amount);
				String msg = Util.blobcatraz + "You have §2" + display;
				p.sendMessage(msg);
				return true;
			}
			cs.sendMessage(Util.csNotPlayer);
			return true;
		}
	}
	
	private boolean baltop(CommandSender cs)
	{
		String msg = Util.blobcatraz + "Top Ten Players: ";
		cs.sendMessage(msg);
		for(String b : ConfigDatabase.getBalanceTopTen()) cs.sendMessage(b);
		return true;
	}
	
	private boolean withdraw(CommandSender cs, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			if(args.length <= 0)
			{
				p.sendMessage(Util.NEA);
				return false;
			}
			
			double amount = 0.0D;
			try{amount = Double.parseDouble(args[0]);}
			catch(Exception ex)
			{
				p.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Number");
				return true;
			}
			double balance = ConfigDatabase.getBalance(p);
			if(balance >= amount)
			{
				ItemStack note = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = note.getItemMeta();
				String name = Util.message("item.banknote.name");
				meta.setDisplayName(name);
				meta.setLore(Arrays.asList("§2" + Util.monetize(amount)));
				note.setItemMeta(meta);
				
				HashMap<Integer, ItemStack> left = p.getInventory().addItem(note);
				if(!left.isEmpty()) p.sendMessage(Util.blobcatraz + "Make some space in your inventory");
				else ConfigDatabase.subtractMoney(p, amount);
				return true;
			}
			else
			{
				p.sendMessage(Util.blobcatraz + "You don't have enough money!");
				return true;
			}
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}