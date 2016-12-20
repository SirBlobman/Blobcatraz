package com.SirBlobman.blobcatraz.command;

import java.util.List;

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

import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.gui.GuiKits;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandKit implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String cmd = c.getName().toLowerCase();
			switch(cmd)
			{
			case "kit": return kit(p, args);
			case "kits": return kits(p, args);
			case "createkit": return createkit(p, args);
			case "deletekit": return deletekit(p, args);
			case "kittochest": return kitToChest(p, args);
			case "chesttokit": return chestToKit(p, args);
			default: return false;
			}
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	private boolean list(Player p)
	{
		String permission = "blobcatraz.kits";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		
		StringBuffer sb = new StringBuffer();
		List<String> list = ConfigKits.skits();
		for(int i = 0; i < list.size(); i++)
		{
			String name = list.get(i);
			String perm = "blobcatraz.kits." + name;
			if(p.hasPermission(perm))
			{
				if(i != 0) sb.append(Util.format("&r, "));
				sb.append(Util.format("&2" + name));
			}
		}
		
		p.sendMessage(Util.prefix + "List of Kits:");
		p.sendMessage(sb.toString());
		return true;
	}
	
	private boolean kit(Player p, String[] args)
	{
		String permission = "blobcatraz.kit";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		if(args.length == 0) return list(p);
		
		String name = Util.getFinal(args, 0);
		if(ConfigKits.exists(name))
		{
			String perm = "blobcatraz.kits." + name;
			if(!PlayerUtil.hasPermission(p, perm)) return true;
			
			ConfigKits.give(p, name);
			p.sendMessage(Util.prefix + Util.option("command.kit.recieve", name));
			return true;
		}
		p.sendMessage(Util.prefix + Util.option("error.kit.does not exist"));
		return true;
	}
	
	private boolean kits(Player p, String[] args)
	{
		String permission = "blobcatraz.kits";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		
		if(args.length > 0)
		{
			String pg = args[0];
			int page = 1;
			try{page = Integer.parseInt(pg);}
			catch(Exception ex)
			{
				p.sendMessage(Util.IA);
				return true;
			}
			
			List<String> kits = ConfigKits.skits();
			if(kits.isEmpty())
			{
				p.sendMessage(Util.prefix + Util.option("error.kits.none"));
				return true;
			}

			GuiKits gui = new GuiKits();
			Inventory i = gui.gui(page);
			p.openInventory(i);
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean createkit(Player p, String[] args)
	{
		String permission = "blobcatraz.createkit";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		if(args.length >= 1)
		{
			String name = Util.getFinal(args, 0);
			PlayerInventory pi = p.getInventory();
			ConfigKits.create(pi, name);
			p.sendMessage(Util.prefix + Util.option("command.createkit.success", name));
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean deletekit(Player p, String[] args)
	{
		String permission = "blobcatraz.delekit";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		if(args.length >= 1)
		{
			String name = Util.getFinal(args, 0);
			if(ConfigKits.exists(name))
			{
				ConfigKits.delete(name);
				p.sendMessage(Util.prefix + Util.option("command.delekit.success", name));
				return true;
			}
			p.sendMessage(Util.prefix + Util.option("error.kit.does not exist"));
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean kitToChest(Player p, String[] args)
	{
		String permission = "blobcatraz.kitToChest";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		if(args.length >= 1)
		{
			String name = Util.getFinal(args, 0);
			String perm = "blobcatraz.kits." + name;
			if(!PlayerUtil.hasPermission(p, perm)) return true;
			
			Block b = PlayerUtil.looking(p);
			BlockState bs = b.getState();
			if(bs instanceof Chest)
			{
				Chest chest = (Chest) bs;
				ConfigKits.kitToChest(name, chest);
				int x = chest.getX();
				int y = chest.getY();
				int z = chest.getZ();
				String coords = x + " " + y + " " + z;
				p.sendMessage(Util.prefix + Util.option("command.kittochest.success", coords, name));
				return true;
			}
			
			Material mat = b.getType();
			ItemStack is = new ItemStack(mat);
			String iname = ItemUtil.name(is);
			p.sendMessage(Util.prefix + Util.option("error.chest.looking", iname));
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean chestToKit(Player p, String[] args)
	{
		String permission = "blobcatraz.chesttokit";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		if(args.length >= 1)
		{
			String name = Util.getFinal(args, 0);
			Block b = PlayerUtil.looking(p);
			BlockState bs = b.getState();
			if(bs instanceof Chest)
			{
				Chest chest = (Chest) bs;
				Inventory i = chest.getInventory();
				ConfigKits.create(i, name);
				int x = chest.getX();
				int y = chest.getY();
				int z = chest.getZ();
				String coords = x + " " + y + " " + z;
				p.sendMessage(Util.prefix + Util.option("command.chesttokit.success", coords, name));
				return true;
			}
			
			Material mat = b.getType();
			ItemStack is = new ItemStack(mat);
			String iname = ItemUtil.name(is);
			p.sendMessage(Util.prefix + Util.option("error.chest.looking", iname));
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
}