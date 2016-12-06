package com.SirBlobman.blobcatraz.listener;

import java.util.ArrayList;
import java.util.List;

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

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenHubEffects implements CommandExecutor, Listener, Runnable
{
	private static List<Player> on = new ArrayList<Player>();
	private static YamlConfiguration config = ConfigBlobcatraz.load();
	private static List<String> potions = config.getStringList("hub effects.effects");
	private static List<String> worlds = config.getStringList("hub effects.enabled worlds");
	
	@Override
	public void run()
	{
		for(Player p : on)
		{
			for(String po : potions)
			{
				PotionEffectType pet = PotionEffectType.getByName(po);
				PotionEffect pe = new PotionEffect(pet, 21, 3, true, false);
				World world = p.getWorld();
				String w = world.getName();
				if(worlds.contains(w)) p.addPotionEffect(pe, true);
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String cmd = c.getName().toLowerCase();
			if(cmd.equals("he"))
			{
				boolean enabled = config.getBoolean("hub effects.enabled");
				if(enabled)
				{
					if(args.length > 0)
					{
						String sub = args[0].toLowerCase();
						if(sub.equals("on"))
						{
							on.add(p);
							String msg = Util.prefix + Util.option("command.hub effects.on");
							p.sendMessage(msg);
							return true;
						}
						if(sub.equals("off"))
						{
							on.remove(p);
							String msg = Util.prefix + Util.option("command.hub effects.off");
							p.sendMessage(msg);
							return true;
						}
						p.sendMessage(Util.IA);
						return false;
					}
				}
				String msg = Util.prefix + Util.option("command.hub effects.not enabled");
				p.sendMessage(msg);
				return true;
			}
		}
		cs.sendMessage(Util.notPlayer);
		return true;
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		on.add(p);
		String msg = Util.prefix + Util.option("command.hub effects.on join");
		p.sendMessage(msg);
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		boolean b1 = on.contains(p);
		if(b1) on.remove(p);
	}
}