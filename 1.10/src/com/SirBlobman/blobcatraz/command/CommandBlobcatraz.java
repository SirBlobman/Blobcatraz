package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.config.ConfigShop;
import com.SirBlobman.blobcatraz.config.ConfigSpawn;
import com.SirBlobman.blobcatraz.item.Items;

public class CommandBlobcatraz implements CommandExecutor
{
	Server S = Bukkit.getServer();
	PluginManager PM = S.getPluginManager();
	
    @Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
    	if(label.equalsIgnoreCase("blobcatraz"))
    	{
    		if(args.length > 0)
    		{
    			if(args[0].equalsIgnoreCase("reload"))
    			{
    				ConfigBlobcatraz.reloadConfig();
    				ConfigPortals.reloadPortals();
    				ConfigDatabase.reloadDatabase();
    				ConfigShop.loadPrices();
    				ConfigSpawn.loadSpawn();
    				
    				cs.sendMessage(Util.blobcatraz + "Configs have been reloaded");
    				return true;
    			}
    			if(args[0].equalsIgnoreCase("restart"))
    			{
    				PM.disablePlugin(Blobcatraz.instance);
    				PM.enablePlugin(Blobcatraz.instance);
    				return true;
    			}
    			if(args[0].equalsIgnoreCase("give"))
    			{
    				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
    				Player p = (Player) cs;
    				
    				if(args.length < 2) {cs.sendMessage(Util.notEnoughArguments); return true;}
    				
    				List<String> validItems = Arrays.asList("sonic_screwdriver", "lightning_rod", "op_sword", "op_bow", "loot_sword", "portal_wand");
    				String itemList = String.join("\n", validItems);
    				
    				if(args[1].equalsIgnoreCase("sonic_screwdriver")) {Util.giveItem(p, Items.sonicScrewdriver()); return true;}
    				if(args[1].equalsIgnoreCase("lightning_rod")) {Util.giveItem(p, Items.lightningRod()); return true;}
    				if(args[1].equalsIgnoreCase("op_sword")) {Util.giveItem(p, Items.opSword()); return true;}
    				if(args[1].equalsIgnoreCase("op_bow")) {Util.giveItem(p, Items.opBow()); return true;}
    				if(args[1].equalsIgnoreCase("loot_sword")) {Util.giveItem(p, Items.lootSword()); return true;}
    				if(args[1].equalsIgnoreCase("portal_wand")) {Util.giveItem(p, Items.portalWand()); return true;}
    				else
    				{
    					p.sendMessage(Util.blobcatraz + "§9" + args[1] + " §cdoesn't exist");
    					p.sendMessage(Util.blobcatraz + "§cValid Items:");
    					p.sendMessage(itemList);
    					return true;
    				}
    			}
    			if(args[0].equalsIgnoreCase("enchant"))
    			{
    				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
    				Player p = (Player) cs;
    				
    				if(args.length != 3) {p.sendMessage(Util.invalidArguments); return true;}
    				
    				String enchant = args[1];
    				int level = Integer.parseInt(args[2]);
    				
    				List<String> validEnchants = Arrays.asList("Cure", "Ender", "Fireball", "Glow", "Levitate", "Wither", "InstaKill");
    				
    				if(enchant.equals("XP_Drain")) {Util.blobcatrazEnchant(p, "XP Drain", level); return true;}
    				if(validEnchants.contains(enchant)) {Util.blobcatrazEnchant(p, enchant, level); return true;}
    				
    				p.sendMessage(Util.blobcatraz + "Invalid enchantment");
					return true;
    			}
    		}
    		else
    		{
    			cs.sendMessage(Util.blobcatraz + "Thanks for testing /blobcatraz");
    			cs.sendMessage(Util.blobcatraz + "Proper usage:\n");
    			cs.sendMessage(c.getDescription() + "\n");
    			cs.sendMessage(c.getUsage());
    			return true;
    		}
    	}
        return false;
    }
}