package com.SirBlobman.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Util;

public class CommandGamemode implements CommandExecutor
{
	public String changed = Util.blobcatraz + "Your gamemode has been set to §6";
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		if(!p.hasPermission("blobcatraz.gamemode")) {p.sendMessage(Util.noPermission + "blobcatraz.gamemode"); return true;}
		
		if(label.equalsIgnoreCase("gamemode") || label.equalsIgnoreCase("gm"))
		{
			if(args.length == 0) {cs.sendMessage(Util.notEnoughArguments); return false;}
			if(args.length >= 2)
			{
				p = Bukkit.getPlayer(args[1]);
				if(p == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[1] + " §ris not a Player"); return true;}
			}
			
			GameMode g = null;
			if(args[0].equalsIgnoreCase("s")) g = GameMode.SURVIVAL;
			if(args[0].equalsIgnoreCase("c")) g = GameMode.CREATIVE;
			if(args[0].equalsIgnoreCase("a")) g = GameMode.ADVENTURE;
			if(args[0].equalsIgnoreCase("sp")) g = GameMode.SPECTATOR;
			if(g == null)
			{
				try{g = GameMode.getByValue(Integer.parseInt(args[0]));}
				catch(Exception ex) 
				{
					try{g = GameMode.valueOf(args[0].toUpperCase());}
					catch(Exception ex2) {cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a GameMode!"); return true;}
				}
			}
			
			if(g != null)
			{
				p.setGameMode(g);
				p.sendMessage(changed + p.getGameMode());
				if(!p.equals(cs)) cs.sendMessage(Util.blobcatraz + "You set §b" + p.getName() + " §rto §6" + p.getGameMode());
			}
			else
			{
				cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a GameMode!");
			}
			
			return true;
		}
		if(label.equalsIgnoreCase("gms"))
		{
			if(args.length >= 1)
			{
				p = Bukkit.getPlayer(args[0]);
				if(p == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Player"); return true;}
			}
			
			p.setGameMode(GameMode.SURVIVAL);
			p.sendMessage(changed + p.getGameMode());
			if(!p.equals(cs)) cs.sendMessage(Util.blobcatraz + "You set §b" + p.getName() + " §rto §6" + p.getGameMode());
			return true;
		}
		if(label.equalsIgnoreCase("gmc"))
		{
			if(args.length >= 1)
			{
				p = Bukkit.getPlayer(args[0]);
				if(p == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Player"); return true;}
			}
			
			p.setGameMode(GameMode.CREATIVE);
			p.sendMessage(changed + p.getGameMode());
			if(!p.equals(cs)) cs.sendMessage(Util.blobcatraz + "You set §b" + p.getName() + " §rto §6" + p.getGameMode());
			return true;
		}
		if(label.equalsIgnoreCase("gma"))
		{
			if(args.length >= 1)
			{
				p = Bukkit.getPlayer(args[0]);
				if(p == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Player"); return true;}
			}
			
			p.setGameMode(GameMode.ADVENTURE);
			p.sendMessage(changed + p.getGameMode());
			if(!p.equals(cs)) cs.sendMessage(Util.blobcatraz + "You set §b" + p.getName() + " §rto §6" + p.getGameMode());
			return true;
		}
		if(label.equalsIgnoreCase("gmsp"))
		{
			if(args.length >= 1)
			{
				p = Bukkit.getPlayer(args[0]);
				if(p == null) {cs.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Player"); return true;}
			}
			
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage(changed + p.getGameMode());
			if(!p.equals(cs)) cs.sendMessage(Util.blobcatraz + "You set §b" + p.getName() + " §rto §6" + p.getGameMode());
			return true;
		}
		return false;
	}
}