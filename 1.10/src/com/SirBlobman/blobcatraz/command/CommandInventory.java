package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Util;

public class CommandInventory implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(label.equalsIgnoreCase("clear") || label.equalsIgnoreCase("ci") || label.equalsIgnoreCase("clearinventory"))
		{
			if(!cs.hasPermission("blobcatraz.clearinventory")) {cs.sendMessage(Util.noPermission + "blobcatraz.clearinventory"); return true;}
			if(args.length == 0)
			{
				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
				Player p = (Player) cs;
				
				Util.clearInventory(p);
				return true;
			}
			
			if(!cs.hasPermission("blobcatraz.clearinventory.others")) {cs.sendMessage(Util.noPermission + "blobcatraz.clearinventory.others"); return true;}
			Player p = Bukkit.getPlayer(args[0]);
			if(p == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris offline or not a Player"); return true;}
			if(args.length == 1)
			{	
				Util.clearInventory(p);
				return true;
			}
			if(args.length == 2)
			{
				String clear = "inventory";
				clear = args[1].toLowerCase();
				if(clear.equals("enderchest"))
				{
					Util.clearEnderChest(p);
				}
				if(clear.equals("armor"))
				{
					Util.clearArmor(p);
				}
				if(clear.equals("inventory"))
				{
					Util.clearInventory(p);
				}
				return true;
			}
			if(args.length == 3)
			{
				String clear = "inventory";
				clear = args[1].toLowerCase();
				Material mat = Material.matchMaterial(args[2].toUpperCase());
				if(mat == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[2] + " §ris not an Item");}
				ItemStack is = new ItemStack(mat);
				if(clear.equals("enderchest"))
				{
					Inventory i = p.getEnderChest();
					Util.clearItem(p, i, is);
				}
				if(clear.equals("armor"))
				{
					cs.sendMessage(Util.blobcatraz + "Armor can't be cleared like that");
				}
				if(clear.equals("inventory"))
				{
					Util.clearItem(p, p.getInventory(), is);
				}
				return true;
			}
		}
		return false;
	}
}