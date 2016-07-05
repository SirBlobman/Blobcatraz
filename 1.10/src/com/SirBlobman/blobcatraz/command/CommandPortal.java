package com.SirBlobman.blobcatraz.command;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigPortals;
import com.SirBlobman.blobcatraz.item.Items;
import com.SirBlobman.blobcatraz.item.PortalWand;

public class CommandPortal implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
		if(label.equalsIgnoreCase("portal"))
		{
			if(args.length < 1) {cs.sendMessage(Util.blobcatraz + "Something went wrong!"); return false;}
			
			if(args[0].equalsIgnoreCase("wand"))
			{
				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
				Player p = (Player) cs;
				
				Util.giveItem(p, Items.portalWand());
				return true;
			}
			if(args[0].equalsIgnoreCase("list"))
			{
				ConfigPortals.loadPortals();
				if(ConfigPortals.portals.isEmpty()) {cs.sendMessage(Util.blobcatraz + "You don't have any portals!"); return true;}
				
				StringBuffer portalList = new StringBuffer();
				for(int i = 0; i < ConfigPortals.portals.size(); i++)
				{
					if(i != 0) portalList.append("§r, §2");
					portalList.append(ConfigPortals.portals.get(i));
				}
				
				cs.sendMessage(Util.blobcatraz + "List of portals: \n§2" + portalList.toString());
				return true;
			}
			
			if(args.length < 2) {cs.sendMessage(Util.blobcatraz + "Something went wrong!"); return false;}
			if(args[0].equalsIgnoreCase("create"))
			{
				if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
				Player p = (Player) cs;
				UUID uuid = p.getUniqueId();

				if(args.length > 2)
				{
					String command = Util.getFinalArg(args, 2);
					
					ConfigPortals.portalConfig.set(args[1] + ".pos1.world", PortalWand.pos1.get(uuid).getWorld().getName());
					ConfigPortals.portalConfig.set(args[1] + ".pos1.x", PortalWand.pos1.get(uuid).getX());
					ConfigPortals.portalConfig.set(args[1] + ".pos1.y", PortalWand.pos1.get(uuid).getY());
					ConfigPortals.portalConfig.set(args[1] + ".pos1.z", PortalWand.pos1.get(uuid).getZ());
					ConfigPortals.portalConfig.set(args[1] + ".pos2.world", PortalWand.pos2.get(uuid).getWorld().getName());
					ConfigPortals.portalConfig.set(args[1] + ".pos2.x", PortalWand.pos2.get(uuid).getX());
					ConfigPortals.portalConfig.set(args[1] + ".pos2.y", PortalWand.pos2.get(uuid).getY());
					ConfigPortals.portalConfig.set(args[1] + ".pos2.z", PortalWand.pos2.get(uuid).getZ());
					ConfigPortals.portalConfig.set(args[1] + ".command", "/" + command);
					ConfigPortals.savePortals();
					
					p.sendMessage(Util.blobcatraz + "Created a command portal called §2§l" + args[1] + "§a!");
					return true;
				}
				
				ConfigPortals.portalConfig.set(args[1] + ".pos1.world", PortalWand.pos1.get(uuid).getWorld().getName());
				ConfigPortals.portalConfig.set(args[1] + ".pos1.x", PortalWand.pos1.get(uuid).getX());
				ConfigPortals.portalConfig.set(args[1] + ".pos1.y", PortalWand.pos1.get(uuid).getY());
				ConfigPortals.portalConfig.set(args[1] + ".pos1.z", PortalWand.pos1.get(uuid).getZ());
				ConfigPortals.portalConfig.set(args[1] + ".pos2.world", PortalWand.pos2.get(uuid).getWorld().getName());
				ConfigPortals.portalConfig.set(args[1] + ".pos2.x", PortalWand.pos2.get(uuid).getX());
				ConfigPortals.portalConfig.set(args[1] + ".pos2.y", PortalWand.pos2.get(uuid).getY());
				ConfigPortals.portalConfig.set(args[1] + ".pos2.z", PortalWand.pos2.get(uuid).getZ());
				ConfigPortals.portalConfig.set(args[1] + ".pos3.world", p.getWorld().getName());
				ConfigPortals.portalConfig.set(args[1] + ".pos3.x", p.getLocation().getX());
				ConfigPortals.portalConfig.set(args[1] + ".pos3.y", p.getLocation().getY());
				ConfigPortals.portalConfig.set(args[1] + ".pos3.z", p.getLocation().getZ());
				ConfigPortals.portalConfig.set(args[1] + ".pos3.pitch", p.getLocation().getPitch());
				ConfigPortals.portalConfig.set(args[1] + ".pos3.yaw", p.getLocation().getYaw());
				ConfigPortals.savePortals();
				
				p.sendMessage(Util.blobcatraz + "Created a teleporting portal called §2§l" + args[1] + "§a!");
				return true;
			}
			if(args[0].equalsIgnoreCase("remove"))
			{
				ConfigPortals.loadPortals();
				if(!ConfigPortals.portals.contains(args[1])) {cs.sendMessage(Util.blobcatraz + "That portal doesn't exist"); return true;}
				
				ConfigPortals.portalConfig.set(args[1], null);
				ConfigPortals.savePortals();
				
				cs.sendMessage(Util.blobcatraz + "Removed a portal called §2§l" + args[1] + "§a!");
				return true;
			}
		}
        return false;
    }
}