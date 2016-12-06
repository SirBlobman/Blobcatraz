package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.item.BItems;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandPortal implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("portal"))
		{
			if(args.length > 0)
			{
				String sub = args[0].toLowerCase();
				switch(sub)
				{
				case "wand": return wand(cs);
				case "list": return list(cs);
				case "create": return create(cs, args);
				case "remove": return remove(cs, args);
				}
			}
			cs.sendMessage(Util.NEA);
			return false;
		}
		return false;
	}
	
	private boolean wand(CommandSender cs)
	{
		String permission = "blobcatraz.portal.wand";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			ItemStack wand = BItems.portalWand();
			ItemUtil.give(p, wand);
			return true;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	private boolean list(CommandSender cs)
	{
		String permission = "blobcatraz.portal.list";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		List<String> list = ConfigPortals.portals();
		StringBuffer portals = new StringBuffer();
		for(int i = 0; i < list.size(); i++)
		{
			if(i != 0) portals.append(Util.color("&r, &2"));
			String portal = list.get(i);
			portals.append(portal);
		}
		
		cs.sendMessage(Util.prefix + "Portal List: ");
		cs.sendMessage(portals.toString());
		return true;
	}
	
	private boolean create(CommandSender cs, String[] args)
	{
		String permission = "blobcatraz.portal.create";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			UUID uuid = p.getUniqueId();
			if(args.length > 1)
			{
				YamlConfiguration config = ConfigPortals.load();
				String a1 = args[1];
				String path1 = a1 + ".pos1.";
				String path2 = a1 + ".pos2.";
				String path3 = a1 + ".pos3.";
				
				Location l1 = PortalWand
			}
			p.sendMessage(Util.NEA);
			return false;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	private boolean remove(CommandSender cs, String[] args)
	{
		
	}
}