package com.SirBlobman.blobcatraz.command;

import java.util.ArrayList;
import java.util.List;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandEnchant implements CommandExecutor,TabCompleter
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String permission = "blobcatraz.enchant";
			if(!PlayerUtil.hasPermission(p, permission)) return true;
			
			if(command.equals("enchant"))
			{
				if(args.length >= 2)
				{
					String ench = args[0].toUpperCase();
					Enchantment enchantment = Enchantment.getByName(ench);
					if(enchantment != null)
					{
						int level = 0;
						try
						{
							String lvl = args[1];
							level = Integer.parseInt(lvl);
						}
						catch(Exception ex)
						{
							p.sendMessage(Util.IA + ": §b" + args[1] + " §ris not a Number");
							return true;
						}
						
						ItemStack held = ItemUtil.getHeld(p);
						if(ItemUtil.isAir(held))
						{
							p.sendMessage(Util.blobcatraz + Util.message("command.enchant.failure"));
						}
						if(level == 0)
						{
							boolean b = ItemUtil.unEnchant(held, enchantment);
							if(b) p.sendMessage(Util.blobcatraz + Util.message("command.enchantment.un", enchantment.getName(), ItemUtil.getName(held)));
							else p.sendMessage(Util.blobcatraz + Util.message("command.enchant.failure"));
							return true;
						}
						boolean b = ItemUtil.enchant(held, enchantment, level);
						if(b) p.sendMessage(Util.blobcatraz + Util.message("command.enchantment.success", ItemUtil.getName(held), enchantment.getName()));
						else p.sendMessage(Util.blobcatraz + Util.message("command.enchant.failure"));
						return true;
					}
					p.sendMessage(Util.IA + ":§b" + ench + " §ris not an enchantment");
					return true;
				}
				p.sendMessage(Util.NEA);
				return false;
			}
			if(command.equals("unbreakable"))
			{
				ItemStack held = ItemUtil.getHeld(p);
				String item = ItemUtil.getName(held);
				ItemUtil.toggleUnbreakable(held);
				boolean b = ItemUtil.isUnbreakable(held);
				if(b) p.sendMessage(Util.blobcatraz + "Your §6" + item + " §ris now §9Unbreakable");
				else p.sendMessage(Util.blobcatraz + "Your §6" + item + " §ris no longer §9Unbreakable");
				return true;
			}
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		if(command.equals("enchant"))
		{
			if(args.length == 1)
			{
				List<String> enchants = new ArrayList<String>();
				for(Enchantment e : Enchantment.values()) enchants.add(e.getName());
				
				List<String> matching = Util.getMatchingStrings(enchants, args[0].toUpperCase());
				return matching;
			}
		}
		return null;
	}
}