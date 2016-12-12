package com.SirBlobman.blobcatraz.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSkull implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String name = p.getName();
			String cmd = c.getName().toLowerCase();
			
			if(cmd.equals("skull"))
			{
				String permission = "blobcatraz.skull";
				if(!PlayerUtil.hasPermission(cs, permission)) return true;
				
				ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				ItemMeta meta = is.getItemMeta();
				SkullMeta skull = (SkullMeta) meta;
				
				if(args.length > 0)
				{
					String name2 = args[0];
					skull.setOwner(name2);
					skull.setDisplayName("§f" + name2);
					is.setItemMeta(skull);
					ItemUtil.give(p, is);
					return true;
				}
				
				skull.setOwner(name);
				skull.setDisplayName("§f" + name);
				is.setItemMeta(skull);
				ItemUtil.give(p, is);
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
}