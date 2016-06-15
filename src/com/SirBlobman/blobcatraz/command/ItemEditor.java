package com.SirBlobman.blobcatraz.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.Util;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class ItemEditor implements CommandExecutor 
{

	
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) 
	{
		if (!(sender instanceof Player)) 
		{
			sender.sendMessage(Util.notAPlayer);
			return true;
		}
		
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("rename"))
		{
			if(args.length > 0)
			{
				ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
				meta.setDisplayName(Util.color(Util.getFinalArg(args, 0)));
				p.getInventory().getItemInMainHand().setItemMeta(meta);
				sender.sendMessage(Util.blobcatraz + "Currently held item was renamed to " + Util.color(Util.getFinalArg(args, 0)) + "§r!");
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("setlore"))
		{
			if(p.getItemInHand().getType() == Material.AIR)
			{
				sender.sendMessage(Util.blobcatraz + "§cYou can't add a lore to AIR!");
			}
			else
			{
				Util.setLore(p, Util.getFinalArg(args, 0));
				
				sender.sendMessage(Util.blobcatraz + "Set lore to " + p.getItemInHand().getItemMeta().getLore());
				p.updateInventory();
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("addlore"))
		{
			if(p.getItemInHand().getType() == Material.AIR)
			{
				sender.sendMessage(Util.blobcatraz + "§cYou can't add a lore to AIR!");
			}
			else
			{
				Util.addLore(p, Util.getFinalArg(args, 0));
			
				sender.sendMessage(Util.blobcatraz + "Set lore to " + p.getItemInHand().getItemMeta().getLore());
				p.updateInventory();
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("repair"))
		{
			if(p.getItemInHand().getType() == Material.AIR)
			{
				sender.sendMessage(Util.blobcatraz + "Air can't be be repaired");
			}
			else
			{
				Util.repairItem(p);
				sender.sendMessage(Util.blobcatraz + "Your §e" + p.getItemInHand().getItemMeta().getDisplayName() + "§r has been repaired");
			}
		}
		
		if(label.equalsIgnoreCase("resetitem"))
		{
			if(p.getItemInHand().getType() == Material.AIR)
			{
				sender.sendMessage(Util.blobcatraz + "Air doesn't need to be cleared");
			}
			else
			{
				Util.clearItem(p);
				sender.sendMessage(Util.blobcatraz + "Your §e" + p.getItemInHand().getType().toString() + "§r has been reset!");
			}
			return true;
		}
		
		return false;
	}
}
