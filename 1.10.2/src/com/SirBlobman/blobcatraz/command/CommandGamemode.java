package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGamemode implements CommandExecutor
{
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String command = c.getName().toLowerCase();
		
		if(command.equals("gamemode"))
		{
			if(args.length == 0)
			{
				cs.sendMessage(Util.NEA);
				return true;
			}
			
			String mode = args[0].toLowerCase();
			GameMode g = null;
			if(mode.equals("s")) g = GameMode.SURVIVAL;
			if(mode.equals("a")) g = GameMode.ADVENTURE;
			if(mode.equals("c")) g = GameMode.CREATIVE;
			if(mode.equals("sp")) g = GameMode.SPECTATOR;
			if(g == null)
			{
				try
				{
					int game = Integer.parseInt(mode);
					g = GameMode.getByValue(game);
				} catch(Exception ex)
				{
					cs.sendMessage(Util.blobcatraz + Util.message("command.gamemode.invalidID", mode));
					return true;
				}
			}
			if(g == null)
			{
				try {g = GameMode.valueOf(mode);}
				catch(Exception ex)
				{
					try
					{
						mode = mode.toUpperCase();
						g = GameMode.valueOf(mode);
					} catch(Exception ex2)
					{
						cs.sendMessage(Util.blobcatraz + Util.message("command.gamemode.invalidID", mode));
						return true;
					}
				}
			}
			
			if(args.length == 1) return self(cs, g);
			if(args.length >= 2) 
			{
				String name = args[1];
				Player p = Bukkit.getPlayer(name);
				if(p == null)
				{
					cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
					return true;
				}
				return change(p, cs, g);
			}
		}
		if(command.equals("gmc"))
		{
			if(args.length >= 1) 
			{
				String name = args[0];
				Player p = Bukkit.getPlayer(name);
				if(p == null)
				{
					cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
					return true;
				}
				return change(p, cs, GameMode.CREATIVE);
			}
			return self(cs, GameMode.CREATIVE);
		}
		if(command.equals("gms"))
		{
			if(args.length >= 1) 
			{
				String name = args[0];
				Player p = Bukkit.getPlayer(name);
				if(p == null)
				{
					cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
					return true;
				}
				return change(p, cs, GameMode.SURVIVAL);
			}
			return self(cs, GameMode.SURVIVAL);
		}
		if(command.equals("gmsp"))
		{
			if(args.length >= 1) 
			{
				String name = args[0];
				Player p = Bukkit.getPlayer(name);
				if(p == null)
				{
					cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
					return true;
				}
				return change(p, cs, GameMode.SPECTATOR);
			}
			return self(cs, GameMode.SPECTATOR);
		}
		if(command.equals("gma"))
		{
			if(args.length >= 1) 
			{
				String name = args[0];
				Player p = Bukkit.getPlayer(name);
				if(p == null)
				{
					cs.sendMessage(Util.blobcatraz + "§5" + name + " §ris not a Player");
					return true;
				}
				return change(p, cs, GameMode.ADVENTURE);
			}
			return self(cs, GameMode.ADVENTURE);
		}
		return false;
	}

	private boolean self(CommandSender cs, GameMode g)
	{
		String permission = "blobcatraz.gamemode.others";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			p.setGameMode(g);
			p.sendMessage(Util.blobcatraz + Util.message("command.gamemode.change", p.getGameMode()));
			return true;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}

	private boolean change(Player p, CommandSender cs, GameMode g)
	{
		String permission = "blobcatraz.gamemode.others";
		if(!PlayerUtil.hasPermission(cs, permission)) return true;

		String pname = p.getName();
		String cname = cs.getName();

		p.setGameMode(g);
		p.sendMessage(Util.blobcatraz + Util.message("command.gamemode.change", p.getGameMode()));
		if(!cname.equals(pname)) cs.sendMessage(Util.blobcatraz + Util.message("command.gamemode.change2", pname, p.getGameMode()));
		return true;
	}
}