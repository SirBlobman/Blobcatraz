package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandLagg implements CommandExecutor
{
	private static final Server S = Bukkit.getServer();
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		if(cmd.equals("lagg"))
		{
			if(args.length == 0) return lagg(cs);
			if(args.length >= 1)
			{
				String sub = args[0];
				switch(sub)
				{
				case "unloadchunks": return unloadChunks(cs);
				default: return false;
				}
			}
		}
		return false;
	}
	
	private boolean lagg(CommandSender cs)
	{
		cs.sendMessage(Util.NEA);
		cs.sendMessage("Proper Usage:");
		cs.sendMessage(Util.color("&6&l/lagg unloadchunks&r - &lUnload all the unused server chunks"));
		return true;
	}
	
	private boolean unloadChunks(CommandSender cs)
	{
		String permission = "blobcatraz.lagg.unloadchunks";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		
		List<World> worlds = S.getWorlds();
		for(World w : worlds)
		{
			int i = 0;
			Chunk[] chunks = w.getLoadedChunks();
			for(Chunk c : chunks)
			{
				Entity[] entities = c.getEntities();
				int players = 0;
				for(Entity e : entities)
				{
					boolean b = (e instanceof Player);
					if(b) players++;
				}
				
				if(players == 0)
				{
					c.unload(true);
					i++;
				}
			}
			cs.sendMessage("[" + w.getName() + "] Unloaded §c" + i + " §rChunks!");
		}
		return true;
	}
}