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
			sender.sendMessage("§1[§6Blobcatraz§1]§r This command must be used by a player");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("rename"))
		{
			if(args.length > 0)
			{
				ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Util.getFinalArg(args, 0)));
				p.getInventory().getItemInMainHand().setItemMeta(meta);
				sender.sendMessage("§1[§6Blobcatraz§1]§r Currently held item was renamed to " + ChatColor.translateAlternateColorCodes('&', Util.getFinalArg(args, 0)) + "§r!");
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("setlore"))
		{
			if(p.getItemInHand().getType() == Material.AIR)
			{
				sender.sendMessage("§1[§6Blobcatraz§1]§r §cYou can't add a lore to AIR!");
			}
			else
			{
				Util.setLore(p, Util.getFinalArg(args, 0));
				
				sender.sendMessage("§1[§6Blobcatraz§1]§r Set lore to " + p.getItemInHand().getItemMeta().getLore());
				p.updateInventory();
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("addlore"))
		{
			if(p.getItemInHand().getType() == Material.AIR)
			{
				sender.sendMessage("§1[§6Blobcatraz§1]§r §cYou can't add a lore to AIR!");
			}
			else
			{
				Util.addLore(p, Util.getFinalArg(args, 0));
			
				sender.sendMessage("§1[§6Blobcatraz§1]§r Set lore to " + p.getItemInHand().getItemMeta().getLore());
				p.updateInventory();
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("repair"))
		{
			if(p.getItemInHand().getType() == Material.AIR)
			{
				sender.sendMessage("§1[§6Blobcatraz§1]§r Air can't be be repaired");
			}
			else
			{
				Util.repairItem(p);
				sender.sendMessage("§1[§6Blobcatraz§1]§r Your §e" + p.getItemInHand().getItemMeta().getDisplayName() + "§r has been repaired");
			}
		}
		
		if(label.equalsIgnoreCase("resetitem"))
		{
			if(p.getItemInHand().getType() == Material.AIR)
			{
				sender.sendMessage("§1[§6Blobcatraz§1]§r Air doesn't need to be cleared");
			}
			else
			{
				Util.clearItem(p);
				sender.sendMessage("§1[§6Blobcatraz§1]§r Your §e" + p.getItemInHand().getType().toString() + "§r has been reset!");
			}
			return true;
		}
		
		return false;
	}
}
