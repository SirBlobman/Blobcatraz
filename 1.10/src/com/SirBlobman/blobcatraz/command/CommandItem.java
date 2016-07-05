package com.SirBlobman.blobcatraz.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigShop;

public class CommandItem implements CommandExecutor, TabCompleter
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(label.equalsIgnoreCase("i") || label.equalsIgnoreCase("item") || label.equalsIgnoreCase("selfgive"))
		{
			if(args.length < 1) {cs.sendMessage(Util.notEnoughArguments);}
			
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("list"))
				{
					ArrayList<String> itemList = ConfigShop.getItemList();
					Collections.sort(itemList);
					String list = String.join(", ", itemList);

					cs.sendMessage(Util.blobcatraz + "Valid item list:");
					cs.sendMessage(list.toLowerCase());
					cs.sendMessage(Util.blobcatraz + "You might want to check your §6latest.log§r if you cannot see the list");
					return true;
				}
				else
				{
					if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
					Player p = (Player) cs;

					Material mat = Material.getMaterial(args[0].toUpperCase());
					if(mat == null) {p.sendMessage(Util.blobcatraz + "Invalid item: " + args[0]); return false;}

					ItemStack is = new ItemStack(mat);
					Util.giveItem(p, is);
					return true;
				}
			}
			
			if(args.length == 2)
			{
				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
				Player p = (Player) cs;
				
				Material mat = Material.getMaterial(args[0].toUpperCase());
				if(mat == null) {p.sendMessage(Util.blobcatraz + "Invalid item: " + args[0]); return false;}
				
				ItemStack is = new ItemStack(mat);
				int amount = 0;
				try{amount = Integer.parseInt(args[1]);}
				catch(Exception ex) {p.sendMessage(Util.invalidArguments); return false;}
				
				is.setAmount(amount);
				
				Util.giveItem(p, is);
				return true;
			}
			if(args.length == 3)
			{
				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
				Player p = (Player) cs;
				
				Material mat = Material.getMaterial(args[0].toUpperCase());
				if(mat == null) {p.sendMessage(Util.blobcatraz + "Invalid item: " + args[0]); return false;}
				
				ItemStack is = new ItemStack(mat);
				int amount = 0;
				short damage = 0;
				try
				{
					amount = Integer.parseInt(args[1]);
					damage = Short.parseShort(args[2]);
				}
				catch (Exception ex) {p.sendMessage(Util.invalidArguments); return false;}
				
				is.setAmount(amount);
				is.setDurability(damage);
				
				Util.giveItem(p, is);
				return true;
			}
			else
			{
				cs.sendMessage(Util.invalidArguments);
			}
		}
        return false;
    }

	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("i") || label.equalsIgnoreCase("item") || label.equalsIgnoreCase("selfgive"))
		{
			List<String> itemList = ConfigShop.getItemList();
			itemList.add("list");
			Collections.sort(itemList);
			
			List<String> actualList = new ArrayList<String>();
			
			for(String item : itemList) if(item.startsWith(args[0].toUpperCase())) actualList.add(item);
			
			if(args.length == 1)
			{
				return actualList;
			}
		}
		
		return null;
	}
}