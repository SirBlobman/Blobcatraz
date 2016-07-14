package com.SirBlobman.blobcatraz.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigShop;

public class CommandWorth implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(label.equalsIgnoreCase("worth"))
		{
			if(args.length == 0)
			{
				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
				Player p = (Player) cs;
				
				PlayerInventory pi = p.getInventory();
				ItemStack held = pi.getItemInMainHand();
				int amount = held.getAmount();
				Material mat = held.getType();
				
				p.sendMessage(Util.blobcatraz + "§5" + amount + " §rof §5" + mat.toString() + " §rcan be sold for §5$" + ConfigShop.getSellPrice(mat, amount));
			}
			if(args.length == 1)
			{
				Material mat = Material.getMaterial(args[0].toUpperCase());
				if(mat == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[0] + "§r is not a valid item"); return true;}
				
				cs.sendMessage(Util.blobcatraz + "§51§r of §5" + args[0] + " §rcan be sold for §5$" + ConfigShop.getSellPrice(mat, 1));
				return true;
			}
			if(args.length == 2)
			{
				Material mat = Material.getMaterial(args[0].toUpperCase());
				if(mat == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a valid item"); return true;}
				int amount = 0;
				try{amount = Integer.parseInt(args[1]);}
				catch (Exception ex) {cs.sendMessage(Util.invalidArguments); return true;}
				
				cs.sendMessage(Util.blobcatraz + "§5" + amount + " §rof §5" + args[0] + " §rcan be sold for §5$" + ConfigShop.getSellPrice(mat, amount));
				return true;
			}
		}
		if(label.equalsIgnoreCase("setworth"))
		{
			if(args.length == 1)
			{
				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
				Player p = (Player) cs;
				
				PlayerInventory pi = p.getInventory();
				ItemStack held = pi.getItemInMainHand();
				Material mat = held.getType();
				double price = 0;
				try{price = Double.parseDouble(args[0]);}
				catch(Exception ex) {cs.sendMessage(Util.invalidArguments); return true;}
				
				ConfigShop.setSellPrice(mat, price);
				p.sendMessage(Util.blobcatraz + "The sell price of §5" + mat.toString() + " §ris now §5$" + price);
				
				return true;
			}
			if(args.length == 2)
			{
				Material mat = Material.getMaterial(args[0].toUpperCase());
				if(mat == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a valid item"); return true;}
				double price = 0;
				try{price = Double.parseDouble(args[1]);}
				catch(Exception ex) {cs.sendMessage(Util.invalidArguments); return true;}
				
				ConfigShop.setSellPrice(mat, price);
				cs.sendMessage(Util.blobcatraz + "The sell price of §5" + mat.toString() + " §ris now §5$" + price);
				return true;
			}
		}
        return false;
    }
}