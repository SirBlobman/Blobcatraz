package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.config.ConfigShop;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandWorth implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("worth")) return worth(cs, args);
		if(command.equals("setworth")) return set(cs, args);
		return false;
	}
	
	private boolean worth(CommandSender cs, String[] args)
	{
		Material mat = Material.AIR;
		int amount = 1;
		short meta = 0;
		if(args.length == 0)
		{
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				ItemStack held = ItemUtil.getHeld(p);
				amount = held.getAmount();
				mat = held.getType();
			}
			else
			{
				cs.sendMessage(Util.csNotPlayer);
				return true;
			}		
		}
		if(args.length >= 1)
		{
			String material = args[0].toUpperCase();
			mat = Material.matchMaterial(material);
			if(mat == null)
			{
				cs.sendMessage(Util.blobcatraz + "Invalid Item: §2" + material);
				return true;
			}		
		}
		if(args.length >= 2)
		{
			String number = args[1];
			try{amount = Integer.parseInt(number);}
			catch(Exception ex)
			{
				cs.sendMessage(Util.IA);
				return true;
			}
		}
		if(args.length >= 3)
		{
			String number = args[2];
			try{meta = Short.parseShort(number);}
			catch(Exception ex)
			{
				cs.sendMessage(Util.IA);
				return true;
			}
		}
		
		ItemStack is = new ItemStack(mat, amount, meta);
		
		double price = ConfigShop.getPrice(is);
		String name = ItemUtil.getName(is);
		String money = Util.monetize(price);
		cs.sendMessage(Util.blobcatraz + "§5" + amount + " §rof §5" + name + " §rcan be sold for §5" + money);
		return true;
	}
	
	private boolean set(CommandSender cs, String[] args)
	{
		Material mat = Material.AIR;
		short meta = 0;
		double price = 0.0D;
		
		if(args.length == 1)
		{
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				ItemStack held = ItemUtil.getHeld(p);
				if(ItemUtil.isAir(held))
				{
					p.sendMessage(Util.blobcatraz + "Air has no value");
					return true;
				}
				mat = held.getType();
				meta = held.getDurability();
			}
			else
			{
				cs.sendMessage(Util.csNotPlayer);
				return true;
			}
		}
		if(args.length == 2)
		{
			String name = args[0].toUpperCase();
			String mon = args[1];
			mat = Material.matchMaterial(name);
			if(mat == null)
			{
				cs.sendMessage(Util.blobcatraz + "Invalid Item: §e" + name);
				return true;
			}
			try{price = Double.parseDouble(mon);}
			catch(Exception ex)
			{
				cs.sendMessage(Util.blobcatraz + "Invalid Price: §e" + mon);
				return true;
			}
		}
		if(args.length >= 3)
		{
			String name = args[0].toUpperCase();
			String dam = args[1];
			String mon = args[2];
			mat = Material.matchMaterial(name);
			if(mat == null)
			{
				cs.sendMessage(Util.blobcatraz + "Invalid Item: §e" + name);
				return true;
			}
			try
			{
				price = Double.parseDouble(mon);
				meta = Short.parseShort(dam);
			}
			catch(Exception ex)
			{
				cs.sendMessage(Util.IA);
				return false;
			}
		}
		
		ItemStack is = new ItemStack(mat, 1, meta);
		ConfigShop.setPrice(is, price);
		String money = Util.monetize(price);
		String name = ItemUtil.getName(is);
		cs.sendMessage(Util.blobcatraz + "The sell price of §5" + name + " §ris now §2" + money);
		return true;
	}
}