package com.SirBlobman.blobcatraz.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class CommandEnchant implements CommandExecutor, TabCompleter
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("enchant") || label.equalsIgnoreCase("ench"))
		{
			if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
			Player p = (Player) cs;
			if(!p.hasPermission("blobcatraz.enchant")) {p.sendMessage(Util.noPermission + "blobcatraz.enchant"); return true;}
			if(args.length < 2) {p.sendMessage(Util.notEnoughArguments); return false;}
			
			Enchantment e = Enchantment.getByName(args[0].toUpperCase());
			if(e == null) {p.sendMessage(Util.invalidArguments + ": §b" + args[0] + " §ris not an enchantment"); return true;}
			int lvl = 0;
			try{lvl = Integer.parseInt(args[1]);} catch(Exception ex) {p.sendMessage(Util.invalidArguments + ": §b" + args[1] + " §ris not a number"); return true;}
			
			if(lvl == 0)
			{
				Util.unEnchant(p, e);
				return true;
			}
			Util.enchant(p, e, lvl);
			return true;
		}
		if(label.equalsIgnoreCase("unbreakable"))
		{
			if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
			Player p = (Player) cs;
			if(!p.hasPermission("blobcatraz.enchant")) {p.sendMessage(Util.noPermission + "blobcatraz.enchant"); return true;}
			
			Util.toggleUnbreakable(p);
			return true;
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("enchant") || label.equalsIgnoreCase("ench"))
		{
			if(args.length == 1)
			{
				List<String> enchantments = new ArrayList<String>();
				for(Enchantment e : Enchantment.values())
				{
					enchantments.add(e.getName());
				}
				Collections.sort(enchantments);
				
				return Util.getMatchingStrings(enchantments, args[0].toUpperCase());
			}
		}
		return null;
	}
}