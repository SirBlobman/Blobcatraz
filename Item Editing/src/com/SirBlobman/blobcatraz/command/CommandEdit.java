package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandEdit implements CommandExecutor
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
			case "rename": return rename(p, args);
			case "addlore": return addlore(p, args);
			case "setlore": return setlore(p, args);
			case "clearlore": return clearlore(p);
			case "removelore": return removelore(p, args);
			case "resetitem": return reset(p);
			default: return false;
			}
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	private boolean rename(Player p, String[] args)
	{
		String permission = "blobcatraz.rename";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		
		if(args.length > 0)
		{
			String name = Util.getFinal(args, 0);
			ItemStack held = ItemUtil.held(p);
			if(ItemUtil.air(held))
			{
				String msg = Util.prefix + Util.option("command.editing.air");
				p.sendMessage(msg);
				return true;
			}
			
			String oname = ItemUtil.name(held);
			ItemUtil.rename(held, name);
			p.updateInventory();
			String cname = Util.format(name);
			String msg = Util.prefix + Util.option("command.editing.rename", oname, cname);
			p.sendMessage(msg);
			return true;	
		}
		p.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean addlore(Player p, String[] args)
	{
		String permission = "blobcatraz.addlore";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		
		if(args.length > 0)
		{
			String lore = Util.getFinal(args, 0);
			ItemStack held = ItemUtil.held(p);
			if(ItemUtil.air(held))
			{
				String msg = Util.prefix + Util.option("command.editing.air");
				p.sendMessage(msg);
				return true;
			}
			
			ItemUtil.addLore(held, lore);
			p.updateInventory();
			List<String> nlore = held.getItemMeta().getLore();
			String snlore = nlore.toString();
			String name = ItemUtil.name(held);
			String msg = Util.prefix + Util.option("command.editing.addlore", name, snlore);
			p.sendMessage(msg);
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean setlore(Player p, String[] args)
	{
		String permission = "blobcatraz.addlore";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		
		if(args.length > 0)
		{
			String lore = Util.getFinal(args, 0);
			ItemStack held = ItemUtil.held(p);
			if(ItemUtil.air(held))
			{
				String msg = Util.prefix + Util.option("command.editing.air");
				p.sendMessage(msg);
				return true;
			}
			
			ItemUtil.setLore(held, lore);
			p.updateInventory();
			List<String> nlore = held.getItemMeta().getLore();
			String snlore = nlore.toString();
			String name = ItemUtil.name(held);
			String msg = Util.prefix + Util.option("command.editing.setlore", name, snlore);
			p.sendMessage(msg);
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean clearlore(Player p)
	{
		String permission = "blobcatraz.clearlore";
		if(!PlayerUtil.hasPermission(p, permission)) return true;

		ItemStack held = ItemUtil.held(p);
		if(ItemUtil.air(held))
		{
			String msg = Util.prefix + Util.option("command.editing.air");
			p.sendMessage(msg);
			return true;
		}
		
		ItemUtil.clearLore(held);
		p.updateInventory();
		String name = ItemUtil.name(held);
		String msg = Util.prefix + Util.option("command.editing.clearlore", name);
		p.sendMessage(msg);
		return true;
	}
	
	private boolean removelore(Player p, String[] args)
	{
		String permission = "blobcatraz.clearlore";
		if(!PlayerUtil.hasPermission(p, permission)) return true;

		if(args.length > 0)
		{
			ItemStack held = ItemUtil.held(p);
			if(ItemUtil.air(held))
			{
				String msg = Util.prefix + Util.option("command.editing.air");
				p.sendMessage(msg);
				return true;
			}
			
			String l = args[0];
			int line = 0;
			try{line = Integer.parseInt(l);}
			catch(Exception ex)
			{
				String msg = Util.prefix + Util.option("error.removelore.line", l);
				p.sendMessage(msg);
				return true;
			}
			
			String name = ItemUtil.name(held);
			ItemUtil.removeLore(held, line);
			String msg = Util.prefix + Util.option("command.editing.removelore", name, line);
			p.sendMessage(msg);
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean reset(Player p)
	{
		String permission = "blobcatraz.resetitem";
		if(!PlayerUtil.hasPermission(p, permission)) return true;

		ItemStack held = ItemUtil.held(p);
		if(ItemUtil.air(held))
		{
			String msg = Util.prefix + Util.option("command.editing.air");
			p.sendMessage(msg);
			return true;
		}
		
		ItemUtil.reset(held);
		String name = ItemUtil.name(held);
		String msg = Util.prefix + Util.option("command.editing.reset", name);
		p.sendMessage(msg);
		return true;
	}
}