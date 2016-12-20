package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSpawner implements CommandExecutor, TabCompleter
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String cmd = c.getName().toLowerCase();
			if(cmd.equals("spawner"))
			{
				String permission = "blobcatraz.spawner";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				
				if(args.length > 0)
				{
					String ent = args[0].toUpperCase();
					EntityType et = EntityType.valueOf(ent);
					if(et == null)
					{
						String msg = Util.prefix + Util.option("error.invalid entity", ent);
						p.sendMessage(msg);
						return true;
					}
					
					Block b = PlayerUtil.looking(p);
					BlockState bs = b.getState();
					if(bs instanceof CreatureSpawner)
					{
						CreatureSpawner csp = (CreatureSpawner) bs;
						csp.setSpawnedType(et);
						bs.update(true);
						String msg = Util.prefix + Util.option("command.spawner.success", et.name());
						p.sendMessage(msg);
						return true;
					}
					else
					{
						String msg = Util.prefix + Util.option("command.spawner.error");
						p.sendMessage(msg);
						return true;
					}
				}
				cs.sendMessage(Util.NEA);
				return false;
			}
			return false;
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("spawner"))
		{
			if(args.length == 1)
			{
				String arg = args[0];
				List<String> living = Util.living();
				List<String> list = Util.getMatching(living, arg);
				return list;
			}
		}
		return null;
	}
}