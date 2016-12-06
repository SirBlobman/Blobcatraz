package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.config.ConfigWarps;
import com.SirBlobman.blobcatraz.config.Warp;
import com.SirBlobman.blobcatraz.gui.GuiWarps;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandWarp implements CommandExecutor
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
			case "warp": return warp(p, args);
			case "warps": return warps(p, args);
			case "createwarp": return createwarp(p, args);
			case "deletewarp": return deletewarp(p, args);
			default: return false;
			}
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	private boolean list(Player p)
	{
		String permission = "blobcatraz.warps";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		
		StringBuffer sb = new StringBuffer();
		List<String> list = ConfigWarps.swarps();
		for(int i = 0; i < list.size(); i++)
		{
			String warp = list.get(i);
			String perm = permission + "." + warp;
			if(p.hasPermission(perm))
			{
				if(i != 0) sb.append(Util.format("&r, "));
				sb.append("§2" + warp);
			}
		}
		
		p.sendMessage(Util.prefix + "List of warps:");
		p.sendMessage(sb.toString());
		return true;
	}
	
	private boolean warp(Player p, String[] args)
	{
		String permission = "blobcatraz.warp";
		if(!PlayerUtil.hasPermission(p, permission)) return true;
		if(args.length > 0)
		{
			String name = Util.getFinal(args, 0);
			if(ConfigWarps.exists(name))
			{
				String perm = "blobcatraz.warps." + name;
				if(!PlayerUtil.hasPermission(p, perm)) return true;
				
				Location warp = ConfigWarps.warp(name);
				p.teleport(warp);
				p.sendMessage(Util.prefix + Util.option("command.warp.success", name));
				return true;
			}
			p.sendMessage(Util.prefix + Util.option("error.warp.does not exist"));
			return true;
		}
		return list(p);
	}
	
	private boolean warps(Player p, String[] args)
	{
		if(args.length >= 1)
		{
			try
			{
				int page = 0;
				page = Integer.parseInt(args[0]);
				GuiWarps gui = new GuiWarps();
				List<Warp> list = ConfigWarps.warps();
				if(list.size() == 0)
				{
					p.sendMessage(Util.prefix + "error.warps.none");
					return true;
				}
				
				Inventory i = gui.warps(page);
				p.openInventory(i);
				return true;
			} catch(Exception ex)
			{
				p.sendMessage(Util.IA);
				return false;
			}
		}
		p.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean createwarp(Player p, String[] args)
	{
		if(args.length > 0)
		{
			String permission = "blobcatraz.createwarp";
			if(!PlayerUtil.hasPermission(p, permission)) return true;
			
			String name = Util.getFinal(args, 0);
			Location warp = p.getLocation();
			World w = warp.getWorld();
			String world = w.getName();
			int x = warp.getBlockX();
			int y = warp.getBlockY();
			int z = warp.getBlockZ();
			String swarp = Util.format("&6%s: %s, %s, %s", world, x, y, z);
			ItemStack icon = new ItemStack(Material.END_BRICKS);
			ConfigWarps.save(name, warp, icon);
			p.sendMessage(Util.prefix + Util.option("command.createwarp.success", name, swarp));
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
	
	private boolean deletewarp(Player p, String[] args)
	{
		if(args.length > 0)
		{
			String permission = "blobcatraz.deletewarp";
			if(!PlayerUtil.hasPermission(p, permission)) return true;
			
			String name = Util.getFinal(args, 0);
			ConfigWarps.delete(name);
			p.sendMessage(Util.prefix + Util.option("command.deletewarp.success", name));
			return true;
		}
		p.sendMessage(Util.NEA);
		return false;
	}
}