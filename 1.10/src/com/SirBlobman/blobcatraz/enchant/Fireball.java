package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import com.SirBlobman.blobcatraz.enchant.event.RightClickEnchantEvent;

public class Fireball implements Listener 
{
	public static final String fireball = "§7Fireball ";
	private final String fireball1 = fireball + "I";
	private final String fireball2 = fireball1 + "I";
	private final String fireball3 = fireball2 + "I";
	
	private final Class<org.bukkit.entity.Fireball> fireballc = org.bukkit.entity.Fireball.class;
	private final Class<SmallFireball> sfireball = SmallFireball.class;
	
	@EventHandler
	public void fireball(RightClickEnchantEvent e)
	{
		Player p = e.getPlayer();
		List<String> lore = e.getLore();
		World w = p.getWorld();
		Location l = p.getLocation();
		Location eye = p.getEyeLocation();
		Vector eyev = eye.toVector();
		Vector dir = l.getDirection().multiply(2);
		Vector dir2 = eyev.add(dir);
		Location shoot = dir2.toLocation(l.getWorld(), l.getYaw(), l.getPitch());
		if(lore.contains(fireball1)) w.spawn(shoot, sfireball);
		if(lore.contains(fireball2)) w.spawn(shoot, fireballc);
		if(lore.contains(fireball3))
		{
			int i = 4;
			while(i > 0) {w.spawn(shoot, fireballc); i--;}
		}
	}
}