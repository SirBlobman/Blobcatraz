package com.SirBlobman.blobcatraz.command;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.gui.RandomTPGui;

@SuppressWarnings({"unused", "deprecation"})
public class RandomTP implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player))
		{
			cs.sendMessage(Util.notAPlayer);
			return true;
		}
		Player p = (Player) cs;
		
		if(label.equalsIgnoreCase("randomtp") || label.equalsIgnoreCase("rtp"))
		{
			if(args.length == 0)
			{
				p.sendMessage(Util.blobcatraz + "§5/randomtp tp§f to teleport randomly");
				p.sendMessage(Util.blobcatraz + "§5/randomtp gui§f to see the GUI");
				return true;
			}
			if(args[0].equalsIgnoreCase("tp"))
			{
				List<String> enabled_worlds = Blobcatraz.instance.getConfig().getStringList("randomtp.enabledWorlds");
				if(enabled_worlds.contains(p.getWorld().getName()))
				{
					p.setFallDistance(-100.0F);
					Location ol = p.getLocation();
					Random r = new Random();
					
					int x = r.nextInt(Blobcatraz.instance.getConfig().getInt("randomtp.maxNormalDistance")) + 1;
					int y = 110;
					int z = r.nextInt(Blobcatraz.instance.getConfig().getInt("randomtp.maxNormalDistance")) + 1;
					
					Location tl = new Location(p.getWorld(), x, y, z);
					tl.getWorld().refreshChunk(tl.getChunk().getX(), tl.getChunk().getZ());
					
					p.teleport(tl);
					p.setFallDistance(0.0F);
					
					p.sendMessage(Util.blobcatraz + "§rYou were teleported to §5" + x + "§r,§5" + y + "§r,§5" + z + "§r!");	
				}
				else
				{
					p.sendMessage(Util.randomTPNotEnabledInWorld);
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("gui"))
			{
				List<String> enabled_worlds = Blobcatraz.instance.getConfig().getStringList("randomtp.enabledWorlds");
				if(enabled_worlds.contains(p.getWorld().getName()))
				{
					RandomTPGui.open(p);
				}
				else
				{
					p.sendMessage(Util.randomTPNotEnabledInWorld);
				}
				return true;
			}
		}
		
		return false;
	}
}
