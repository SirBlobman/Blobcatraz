package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandEdit implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			
			String command = c.getName().toLowerCase();
			if(command.equals("rename"))
			{
				String permission = "blobcatraz.rename";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				if(args.length > 0)
				{
					String name = Util.getFinal(args, 0);
					ItemStack held = ItemUtil.getHeld(p);
					ItemUtil.rename(held, name);
					String msg = Util.message("command.rename.success", held.getType(), ItemUtil.getName(held));
					p.sendMessage(msg);
					return true;
				}
				p.sendMessage(Util.NEA);
				return false;
			}
			if(command.equals("setlore"))
			{
				String permission = "blobcatraz.setlore";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				if(args.length > 0)
				{
					String lore = Util.getFinal(args, 0);
					ItemStack held = ItemUtil.getHeld(p);
					ItemUtil.setLore(held, lore);
					String msg = Util.message("command.setlore.success", held.getType(), ItemUtil.getLore(held));
					p.sendMessage(msg);
					return true;
				}
				p.sendMessage(Util.NEA);
				return false;
			}
			if(command.equals("addlore"))
			{
				String permission = "blobcatraz.addlore";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				if(args.length > 0)
				{
					String lore = Util.getFinal(args, 0);
					ItemStack held = ItemUtil.getHeld(p);
					ItemUtil.addLore(held, lore);
					String msg = Util.message("command.addlore.success", held.getType(), ItemUtil.getLore(held));
					p.sendMessage(msg);
					return true;
				}
				p.sendMessage(Util.NEA);
				return false;				
			}
			if(command.equals("removelore"))
			{
				String permission = "blobcatraz.removelore";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				ItemStack held = ItemUtil.getHeld(p);
				if(args.length == 1)
				{
					int line = Integer.parseInt(args[0]);
					ItemUtil.removeLore(held, line);
					String msg = Util.message("command.removelore.success", line, ItemUtil.getName(held));
					p.sendMessage(msg);
					return true;
				}
				ItemUtil.clearLore(held);
				String msg = Util.message("command.clearlore.success", ItemUtil.getName(held));
				p.sendMessage(msg);
				return true;
			}
			if(command.equals("repair"))
			{
				String permission = "blobcatraz.repair";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				ItemStack held = ItemUtil.getHeld(p);
				ItemUtil.repair(held);
				String msg = Util.message("command.repair.success", ItemUtil.getName(held));
				p.sendMessage(msg);
				return true;
			}
			if(command.equals("resetitem"))
			{
				String permission = "blobcatraz.resetitem";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				ItemStack held = ItemUtil.getHeld(p);
				ItemUtil.reset(held);
				String msg = Util.message("command.resetitem.success", ItemUtil.getName(held));
				p.sendMessage(msg);
				return true;
			}
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
    }
}