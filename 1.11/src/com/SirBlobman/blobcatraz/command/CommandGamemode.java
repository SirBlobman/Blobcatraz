package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandGamemode implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		String cmd = c.getName().toLowerCase();
		switch(cmd)
		{
		case "gamemode": return gamemode(cs, args);
		case "gma": return gma(cs, args);
		case "gmc": return gmc(cs, args);
		case "gms": return gms(cs, args);
		case "gmsp": return gmsp(cs, args);
		default: return false;
		}
	}
	
	private GameMode gamemode(String s)
	{
		s = s.toUpperCase();
		String mode = GameMode.SURVIVAL.name();
		switch(s)
		{
		case "A":
		case "2": {mode = GameMode.ADVENTURE.name(); break;}
		case "C":
		case "1": {mode = GameMode.CREATIVE.name(); break;}
		case "S":
		case "0": {mode = GameMode.SURVIVAL.name(); break;}
		case "SP":
		case "3": {mode = GameMode.SPECTATOR.name(); break;}
		default: {mode = GameMode.SURVIVAL.name(); break;}
		}
		
		GameMode gm = GameMode.valueOf(mode);
		return gm;
	}
	
	private boolean gamemode(CommandSender cs, String[] args)
	{
		if(args.length == 1)
		{
			String permission = "blobcatraz.gamemode";
			if(!PlayerUtil.hasPermission(cs, permission)) return true;
			
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				String mode = args[0];
				GameMode gm = gamemode(mode);
				if(gm == null)
				{
					String error = Util.prefix + Util.option("error.gamemode.doesn't exist", mode);
					p.sendMessage(error);
					return true;
				}
				
				p.setGameMode(gm);
				p.sendMessage(Util.prefix + Util.option("command.gamemode.1", gm.name()));
				return true;
			}
			cs.sendMessage(Util.notPlayer);
			return true;
		}
		if(args.length > 1)
		{
			String permission = "blobcatraz.gamemode.others";
			if(!PlayerUtil.hasPermission(cs, permission)) return true;
			

			String mode = args[0];
			GameMode gm = gamemode(mode);
			if(gm == null)
			{
				String error = Util.prefix + Util.option("error.gamemode.doesn't exist", mode);
				cs.sendMessage(error);
				return true;
			}
			
			String target = args[1];
			Player p = Bukkit.getPlayer(target);
			if(p == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", target);
				cs.sendMessage(msg);
				return true;
			}
			

			p.setGameMode(gm);
			String msg1 = Util.prefix + Util.option("command.gamemode.sender", p.getDisplayName(), gm.name());
			String msg2 = Util.prefix + Util.option("command.gamemode.target", gm.name());
			cs.sendMessage(msg1);
			p.sendMessage(msg2);
			return true;
		}
		cs.sendMessage(Util.NEA);
		return false;
	}

	private static final String permission1 = "blobcatraz.gamemode";
	private static final String permission2 = "blobcatraz.gamemode.others";
	private boolean gma(CommandSender cs, String[] args)
	{
		GameMode gm = GameMode.ADVENTURE;
		if(!PlayerUtil.hasPermission(cs, permission1)) return true;
		if(args.length > 0)
		{
			if(!PlayerUtil.hasPermission(cs, permission2)) return true;
			
			String target = args[0];
			Player p = Bukkit.getPlayer(target);
			if(p == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", target);
				cs.sendMessage(msg);
				return true;
			}
			
			p.setGameMode(gm);
			String msg1 = Util.prefix + Util.option("command.gamemode.sender", p.getDisplayName(), gm.name());
			String msg2 = Util.prefix + Util.option("command.gamemode.target", gm.name());
			cs.sendMessage(msg1);
			p.sendMessage(msg2);
			return true;
		}
		else
		{
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				p.setGameMode(gm);
				p.sendMessage(Util.prefix + Util.option("command.gamemode.1", gm.name()));
				return true;
			}
			cs.sendMessage(Util.notPlayer);
			return true;
		}
	}
	
	private boolean gmc(CommandSender cs, String[] args)
	{
		GameMode gm = GameMode.CREATIVE;
		if(!PlayerUtil.hasPermission(cs, permission1)) return true;
		if(args.length > 0)
		{
			if(!PlayerUtil.hasPermission(cs, permission2)) return true;
			
			String target = args[0];
			Player p = Bukkit.getPlayer(target);
			if(p == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", target);
				cs.sendMessage(msg);
				return true;
			}
			
			p.setGameMode(gm);
			String msg1 = Util.prefix + Util.option("command.gamemode.sender", p.getDisplayName(), gm.name());
			String msg2 = Util.prefix + Util.option("command.gamemode.target", gm.name());
			cs.sendMessage(msg1);
			p.sendMessage(msg2);
			return true;
		}
		else
		{
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				p.setGameMode(gm);
				p.sendMessage(Util.prefix + Util.option("command.gamemode.1", gm.name()));
				return true;
			}
			cs.sendMessage(Util.notPlayer);
			return true;
		}
	}
	
	private boolean gms(CommandSender cs, String[] args)
	{
		GameMode gm = GameMode.SURVIVAL;
		if(!PlayerUtil.hasPermission(cs, permission1)) return true;
		if(args.length > 0)
		{
			if(!PlayerUtil.hasPermission(cs, permission2)) return true;
			
			String target = args[0];
			Player p = Bukkit.getPlayer(target);
			if(p == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", target);
				cs.sendMessage(msg);
				return true;
			}
			
			p.setGameMode(gm);
			String msg1 = Util.prefix + Util.option("command.gamemode.sender", p.getDisplayName(), gm.name());
			String msg2 = Util.prefix + Util.option("command.gamemode.target", gm.name());
			cs.sendMessage(msg1);
			p.sendMessage(msg2);
			return true;
		}
		else
		{
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				p.setGameMode(gm);
				p.sendMessage(Util.prefix + Util.option("command.gamemode.1", gm.name()));
				return true;
			}
			cs.sendMessage(Util.notPlayer);
			return true;
		}
	}
	
	private boolean gmsp(CommandSender cs, String[] args)
	{
		GameMode gm = GameMode.SPECTATOR;
		if(!PlayerUtil.hasPermission(cs, permission1)) return true;
		if(args.length > 0)
		{
			if(!PlayerUtil.hasPermission(cs, permission2)) return true;
			
			String target = args[0];
			Player p = Bukkit.getPlayer(target);
			if(p == null)
			{
				String msg = Util.prefix + Util.option("error.target.does not exist", target);
				cs.sendMessage(msg);
				return true;
			}
			
			p.setGameMode(gm);
			String msg1 = Util.prefix + Util.option("command.gamemode.sender", p.getDisplayName(), gm.name());
			String msg2 = Util.prefix + Util.option("command.gamemode.target", gm.name());
			cs.sendMessage(msg1);
			p.sendMessage(msg2);
			return true;
		}
		else
		{
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				p.setGameMode(gm);
				p.sendMessage(Util.prefix + Util.option("command.gamemode.1", gm.name()));
				return true;
			}
			cs.sendMessage(Util.notPlayer);
			return true;
		}
	}
}