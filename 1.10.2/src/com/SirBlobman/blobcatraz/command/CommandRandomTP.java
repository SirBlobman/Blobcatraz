package com.SirBlobman.blobcatraz.command;

import java.util.List;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.gui.GuiRandomTP;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.TeleportUtil;
import com.SirBlobman.blobcatraz.utility.TeleportUtil.Teleport;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandRandomTP implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("randomtp"))
			{
				if(args.length == 0)
				{
					p.sendMessage(Util.blobcatraz + "Sub Commands:");
					p.sendMessage("§6§l/rtp tp§f to teleport randomly");
					p.sendMessage("§6§l/rtp gui§f to use the teleport GUI");
					return true;
				}

				String sub = args[0].toLowerCase();

				YamlConfiguration config = ConfigBlobcatraz.load();
				List<String> worlds = config.getStringList("randomTP.enabled worlds");
				String world = p.getWorld().getName();
				if(!worlds.contains(world))
				{
					p.sendMessage(Util.notInThisWorld);
					return true;
				}

				if(sub.equals("tp"))
				{
					String permission = "blobcatraz.randomtp";
					if(!PlayerUtil.hasPermission(p, permission)) return true;
					TeleportUtil.randomTP(p, Teleport.NORMAL);
					return true;
				}
				if(sub.equals("gui"))
				{
					GuiRandomTP grtp = new GuiRandomTP();
					Inventory gui = grtp.randomtp();
					PlayerUtil.open(p, gui);
					return true;
				}
			}
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}