package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.item.LightningRod;
import com.SirBlobman.blobcatraz.item.LootingSword;
import com.SirBlobman.blobcatraz.item.OverpoweredSword;
import com.SirBlobman.blobcatraz.item.SonicScrewdriver;

public class CommandBlobcatraz implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage("§1[§6Blobcatraz§1]§r This command must be used by a player");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("blobcatraz"))
		{
			if(args.length > 0)
			{
				if(args[0].equalsIgnoreCase("reload"))
				{
					Blobcatraz.instance.reloadConfig();
					Portal.reloadPortalConfig();
					AFKCommandHandler.reloadAFKConfig();
					
					sender.sendMessage("§1[§6Blobcatraz§1]§r Configs have been reloaded");
				}
				if(args[0].equalsIgnoreCase("give"))
				{
					if(args.length > 1)
					{
						List<String> valid = Arrays.asList
						(
							"Sonic Screwdriver (sonic_screwdriver)",
							"Lightning Rod (lightning_rod)",
							"Overpowered Sword (op_sword)",
							"Drop Everything (loot_sword)"
						);
						String item_list = String.join("\n", valid);
						
						if(args[1].equals("sonic_screwdriver"))
						{
							Util.giveItem(p, SonicScrewdriver.sonic());
							return true;
						}
						if(args[1].equals("lightning_rod"))
						{
							Util.giveItem(p, LightningRod.lrod());
							return true;
						}
						if(args[1].equals("op_sword"))
						{
							Util.giveItem(p, OverpoweredSword.opsword());
							return true;
						}
						if(args[1].equals("loot_sword"))
						{
							Util.giveItem(p, LootingSword.lootSword());
							return true;
						}
						else
						{
							p.sendMessage("§1[§6Blobcatraz§1]§r §cThat item doesn't exist");
							p.sendMessage("§1[§6Blobcatraz§1]§r §cValid Items:");
							p.sendMessage(item_list + "\n");
							return false;
						}
					}
					else
					{
						p.sendMessage("§1[§6Blobcatraz§1]§r Not enough arguments!\n");
						return false;
					}
				}
			}
			else
			{
				p.sendMessage("§1[§6Blobcatraz§1]§r Thanks for testing CommandBlobcatraz");
				p.sendMessage("§1[§6Blobcatraz§1]§r Proper usage:\n");
				p.sendMessage(c.getDescription() + "\n");
				p.sendMessage(c.getUsage());
				return true;
			}
		}
		return false;
	}
}
