package com.SirBlobman.blobcatraz.listener;

import java.util.ArrayList;
import java.util.List;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HubEffects implements Listener,Runnable,CommandExecutor
{	
	private static List<Player> on = new ArrayList<Player>();
	
	@Override
	public void run()
	{
		YamlConfiguration config = ConfigBlobcatraz.load();
		List<String> potions = config.getStringList("hub effects.effects");
		List<String> worlds = config.getStringList("hub effects.enabled worlds");
		for(Player p : on)
		{
			for(String pot : potions)
			{
				PotionEffectType pet = PotionEffectType.getByName(pot);
				PotionEffect effect = new PotionEffect(pet, 21, 3, true, false);
				World w = p.getWorld();
				String world = w.getName();
				if(worlds.contains(world)) p.addPotionEffect(effect, true);
			}
		}
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		on.add(p);
		p.sendMessage(Util.blobcatraz + "Hub Effects are enabled! Do §e/he off§r to turn them off");
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		on.remove(p);
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("he"))
			{
				YamlConfiguration config = ConfigBlobcatraz.load();
				boolean enabled = config.getBoolean("hub effects.enabled");
				if(!enabled)
				{
					p.sendMessage(Util.blobcatraz + "Hub Effects are not enabled in the config!");
					return true;
				}
				
				if(args.length > 0)
				{
					String sub = args[0].toLowerCase();
					if(sub.equals("off"))
					{
						on.remove(p);
						p.sendMessage(Util.blobcatraz + "You disabled Hub Effects for yourself");
						return true;
					}
					if(sub.equals("on"))
					{
						on.add(p);
						p.sendMessage(Util.blobcatraz + "You enabled Hub Effects for yourself");
						return true;
					}
					p.sendMessage(Util.IA);
					return false;
				}
				p.sendMessage(Util.NEA);
				return false;
			}
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
}