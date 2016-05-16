package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.item.LightningRod;
import com.SirBlobman.blobcatraz.item.LootingSword;
import com.SirBlobman.blobcatraz.item.OverpoweredSword;
import com.SirBlobman.blobcatraz.item.SonicScrewdriver;

@SuppressWarnings("deprecation")
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
				if(args[0].equals("enchant"))
				{
					ItemStack hitem = p.getItemInHand();
					if(hitem == null || hitem.getType() == Material.AIR)
					{
						p.sendMessage("§1[§6Blobcatraz§1]§r You can't enchant air");
						return false;
					}
					
					if(args.length != 3)
					{
						p.sendMessage("§1[§6Blobcatraz§1]§r Not enough arguments!\n");
						return false;
					}
					
					String enchant = args[1];
					String level = args[2];
					String lore_level = null;

					if(enchant.equals("Fireball") || enchant.equals("Glow") || enchant.equals("Levitate") || enchant.equals("Cure") || enchant.equals("Wither"))
					{
						if(level.equalsIgnoreCase("1"))
						{
							lore_level = " I";
						}
						if(level.equalsIgnoreCase("2"))
						{
							lore_level = " II";
						}
						if(level.equalsIgnoreCase("3"))
						{
							lore_level = " III";
						}
						
						Util.addLore(p, "§7" + enchant + lore_level);
						return true;
					}
					if(enchant.equals("XP_Steal"))
					{
						enchant = "XP Drain";
						lore_level = " I";
						return true;
					}
					else
					{
						p.sendMessage("§1[§6Blobcatraz§1]§r Invalid enchantment");
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