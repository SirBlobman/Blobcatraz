package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandPrefix implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("setprefix"))
		{
			String permission = "blobcatraz.setprefix";
			if(!PlayerUtil.hasPermission(cs, permission)) return true;
			
			if(args.length > 1)
			{
				String name = args[0];
				String prefix = Util.getFinal(args, 1);
				Player p = Bukkit.getPlayer(name);
				if(p == null)
				{
					String msg = Util.prefix + Util.option("error.target.does not exist", name);
					cs.sendMessage(msg);
					return true;
				}
				
				YamlConfiguration config = ConfigDatabase.load(p);
				config.set("prefix", prefix);
				ConfigDatabase.save(p, config);
				
				prefix = Util.color(prefix);
				p.setDisplayName(prefix + p.getName());
				return true;
			}
		}
		return false;
	}
}