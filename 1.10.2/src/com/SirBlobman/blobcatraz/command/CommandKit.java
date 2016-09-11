package com.SirBlobman.blobcatraz.command;

import java.util.List;
import java.util.Set;

import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandKit implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("kit"))
			{
				String permission = "blobcatraz.kit";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				if(args.length == 0) return list(cs);
				
				String name = Util.getFinal(args, 0);
				if(ConfigKits.exists(name))
				{
					String kitperm = "blobcatraz.kits." + name;
					if(!PlayerUtil.hasPermission(p, kitperm)) return true;
					
					ConfigKits.giveKit(p, name);
					p.sendMessage(Util.blobcatraz + "You have received kit §c" + name);
					return true;
				}
				p.sendMessage(Util.blobcatraz + "That kit doesn't exist");
			}
			if(command.equals("createkit"))
			{
				String permission = "blobcatraz.createkit";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				if(args.length >= 1)
				{
					String name = Util.getFinal(args, 0);
					PlayerInventory pi = p.getInventory();
					ConfigKits.create(pi, name);
					p.sendMessage(Util.blobcatraz + "You have created a kit called §c" + name);
					return true;
				}
				p.sendMessage(Util.NEA);
				return false;
			}
			if(command.equals("deletekit"))
			{
				String permission = "blobcatraz.deletekit";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				if(args.length >= 1)
				{
					String name = Util.getFinal(args, 0);
					ConfigKits.delete(name);
					p.sendMessage(Util.blobcatraz + "Kit §c" + name + " §rwas deleted!");
					return true;
				}
				p.sendMessage(Util.NEA);
				return false;
			}
			if(command.equals("kittochest")) //Kit to chest
			{
				String permission = "blobcatraz.kit.toChest";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				if(args.length >= 1)
				{
					String name = Util.getFinal(args, 0);
					String kperm = "blobcatraz.kits." + name;
					if(!PlayerUtil.hasPermission(p, kperm)) return true;
					Block b = p.getTargetBlock((Set<Material>) null, 20);
					BlockState bs = b.getState();
					if(bs instanceof Chest)
					{
						Chest chest = (Chest) bs;
						ConfigKits.kitToChest(name, chest);
						String coords = chest.getX() + " " + chest.getY() + " " + chest.getZ();
						p.sendMessage(Util.blobcatraz + "The chest at §5" + coords + " §rwas filled with the kit §c" + name);
						return true;
					}
					ItemStack is = new ItemStack(b.getType());
					String dname = ItemUtil.getName(is);
					p.sendMessage(Util.blobcatraz + "You must look at a Chest, not a §5" + dname);
					return true;
				}
				p.sendMessage(Util.NEA);
				return false;
			}
			if(command.equals("chesttokit")) //Chest to kit
			{
				String permission = "blobcatraz.kit.fromChest";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				if(args.length >= 1)
				{
					String name = Util.getFinal(args, 0);
					Block b = p.getTargetBlock((Set<Material>) null, 20);
					BlockState bs = b.getState();
					if(bs instanceof Chest)
					{
						Chest chest = (Chest) bs;
						Inventory i = chest.getInventory();
						ConfigKits.create(i, name);
						String coords = chest.getX() + " " + chest.getY() + " " + chest.getZ();
						p.sendMessage(Util.blobcatraz + "The chest at §5" + coords + " §rwas turned into a kit named §c" + name);
						return true;
					}
					ItemStack is = new ItemStack(b.getType());
					String dname = ItemUtil.getName(is);
					p.sendMessage(Util.blobcatraz + "You must look at a Chest, not a §5" + dname);
				}
				p.sendMessage(Util.NEA);
				return false;
			}
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	private boolean list(CommandSender cs)
	{
		String permission = "blobcatraz.kits";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		StringBuffer list = new StringBuffer();
		List<String> kits = ConfigKits.getKits();
		for(int i = 0; i < kits.size(); i++)
		{
			String name = kits.get(i);
			String perm = "blobcatraz.kits." + name;
			if(cs.hasPermission(perm))
			{
				if(i != 0) list.append("§r, ");
				list.append("§2" + name);
			}
		}
		cs.sendMessage(Util.blobcatraz + "List of Kits:");
		cs.sendMessage(list.toString());
		return true;
	}
}