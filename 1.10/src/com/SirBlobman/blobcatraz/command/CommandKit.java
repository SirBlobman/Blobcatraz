package com.SirBlobman.blobcatraz.command;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigKits;

public class CommandKit implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		if(label.equalsIgnoreCase("kit"))
		{
			if(!p.hasPermission("blobcatraz.kit")) {Util.noPermission(p, "blobcatraz.kit"); return true;}
			if(args.length == 0)
			{
				if(p.hasPermission("blobcatraz.kits"))
				{
					StringBuffer kitList = new StringBuffer();
					for(int i = 0; i < ConfigKits.getKits().size(); i++)
					{
						String kitName = ConfigKits.getKits().get(i);
						if(cs.hasPermission("blobcatraz.kits."  + kitName))
						{
							if(i != 0) kitList.append("§r, ");
							kitList.append("§2" + kitName);
						}
					}
					p.sendMessage(Util.blobcatraz + "List of Kits:");
					p.sendMessage(kitList.toString());
					return true;
				}
				else
				{
					Util.noPermission(p, "blobcatraz.kits");
					return true;
				}
			}
			String kitName = Util.getFinalArg(args, 0);
			if(ConfigKits.doesKitExist(kitName))
			{
				if(p.hasPermission("blobcatraz.kits." + kitName))
				{
					ConfigKits.giveKitToPlayer(p, kitName);
					p.sendMessage(Util.blobcatraz + "You have been given kit §c" + kitName);
					return true;
				}
				else
				{
					Util.noPermission(p, "blobcatraz.kits." + kitName);
					return true;
				}
			}
			else
			{
				p.sendMessage(Util.blobcatraz + "That kit doesn't exist");
				return true;
			}
		}
		if(label.equalsIgnoreCase("createkit"))
		{
			if(!p.hasPermission("blobcatraz.createkit")) {Util.noPermission(p, "blobcatraz.createkit"); return true;}
			if(args.length == 0) {p.sendMessage(Util.notEnoughArguments); return false;}
			String kitName = Util.getFinalArg(args, 0);
			PlayerInventory pi = p.getInventory();
			ConfigKits.createKit(pi, kitName);
			p.sendMessage(Util.blobcatraz + "You have created a kit called §c" + kitName);
			return true;
		}
		if(label.equalsIgnoreCase("deletekit"))
		{
			if(!p.hasPermission("blobcatraz.deletekit")) {Util.noPermission(p, "blobcatraz.createkit"); return true;}
			if(args.length == 0) {p.sendMessage(Util.notEnoughArguments); return false;}
			String kitName = Util.getFinalArg(args, 0);
			ConfigKits.deleteKit(kitName);
			p.sendMessage(Util.blobcatraz + "Kit '§c" + kitName + "§r' has been deleted!");
			return true;
		}
		if(label.equalsIgnoreCase("KitToChest"))
		{
			if(!Util.hasPermission(p, "blobcatraz.kit.toChest")) return true;
			if(args.length == 0) {p.sendMessage(Util.notEnoughArguments); return true;}
			String kitName = Util.getFinalArg(args, 0);
			if(!Util.hasPermission(p, "blobcatraz.kits." + kitName)) return true;
			Block b = p.getTargetBlock((Set<Material>) null, 20);
			if(b.getType() == Material.CHEST)
			{
				Chest chest = (Chest) b.getState();
				ConfigKits.putKitInChest(kitName, chest);
				String coords = chest.getX() + " " + chest.getY() + " " + chest.getZ();
				cs.sendMessage(Util.blobcatraz + "The chest at §5" + coords + "§r has been filled with the kit §c" + kitName);
				return true;
			}
			else {p.sendMessage(Util.blobcatraz + "You are not looking at a chest!"); p.sendMessage("You are looking at a " + b.getClass().getSimpleName()); return true;}
			
		}
		if(label.equalsIgnoreCase("ChestToKit"))
		{
			if(!Util.hasPermission(p, "blobcatraz.kit.fromChest")) return true;
			if(args.length == 0) {p.sendMessage(Util.notEnoughArguments); return true;}
			String kitName = Util.getFinalArg(args, 0);
			Block b = p.getTargetBlock((Set<Material>) null, 20);
			if(b.getType() == Material.CHEST)
			{
				Chest chest = (Chest) b.getState();
				Inventory i = chest.getInventory();
				ConfigKits.createKit(i, kitName);
				String coords = chest.getX() + " " + chest.getY() + " " + chest.getZ();
				cs.sendMessage(Util.blobcatraz + "You have created a kit called §c" + kitName + "§r from the chest at: §5" + coords);
				return true;
			}
			else {p.sendMessage(Util.blobcatraz + "You are not looking at a chest!"); p.sendMessage("You are looking at a " + b.getType()); return true;}
		}
		return false;
	}
}