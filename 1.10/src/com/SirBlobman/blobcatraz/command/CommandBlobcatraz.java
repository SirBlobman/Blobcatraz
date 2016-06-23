package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.Database;
import com.SirBlobman.blobcatraz.config.Portals;
import com.SirBlobman.blobcatraz.config.Shop;
import com.SirBlobman.blobcatraz.item.Items;
import com.SirBlobman.blobcatraz.item.LightningRod;
import com.SirBlobman.blobcatraz.item.SonicScrewdriver;

@SuppressWarnings("deprecation")
public class CommandBlobcatraz implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage(Util.notAPlayer);
			return true;
		}

		Player p = (Player) sender;

		if(label.equalsIgnoreCase("blobcatraz"))
		{
			if(args.length > 0)
			{
				if(args[0].equalsIgnoreCase("restart"))
				{
					Bukkit.getServer().getPluginManager().disablePlugin(Blobcatraz.instance);
					Bukkit.getServer().getPluginManager().enablePlugin(Blobcatraz.instance);
					return true;
				}
				if(args[0].equalsIgnoreCase("reload"))
				{
					Blobcatraz.instance.reloadConfig();
					Portals.loadPortals();
					Database.loadDatabase();
					Shop.loadPrices();

					sender.sendMessage(Util.blobcatraz + "Configs have been reloaded");
					return true;
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
							"Drop Everything (loot_sword)",
							"Portal Wand (portal_wand)"
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
							Util.giveItem(p, Items.opsword());
							return true;
						}
						if(args[1].equals("loot_sword"))
						{
							Util.giveItem(p, Items.lootSword());
							return true;
						}
						if(args[1].equals("portal_wand"))
						{
							Util.giveItem(p, Items.portalWand());
							return true;
						}
						else
						{
							p.sendMessage(Util.blobcatraz + "§cThat item doesn't exist");
							p.sendMessage(Util.blobcatraz + "§cValid Items:");
							p.sendMessage(item_list + "\n");
							return false;
						}
					}

					else
					{
						p.sendMessage(Util.notEnoughArguments + "\n");
						return false;
					}
				}
				if(args[0].equals("enchant"))
				{
					ItemStack hitem = p.getItemInHand();
					if(hitem == null || hitem.getType() == Material.AIR)
					{
						p.sendMessage(Util.blobcatraz + "You can't enchant air");
						return false;
					}

					if(args.length != 3)
					{
						p.sendMessage(Util.notEnoughArguments + "\n");
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
					}
					if(enchant.equals("XP_Steal"))
					{
						enchant = "XP Drain";
						lore_level = " I";
					}

					Util.addLore(p, "§7" + enchant + lore_level);
					p.sendMessage(Util.blobcatraz + "Your item has been enchanted with §7" + enchant + lore_level);
					return true;
				}
				else
				{
					p.sendMessage(Util.blobcatraz + "Invalid enchantment");
					return false;
				}
			}
		}

		else
		{
			p.sendMessage(Util.blobcatraz + "Thanks for testing /blobcatraz");
			p.sendMessage(Util.blobcatraz + "Proper usage:\n");
			p.sendMessage(c.getDescription() + "\n");
			p.sendMessage(c.getUsage());
			return true;
		}

		return false;
	}
}