package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import com.SirBlobman.blobcatraz.enchant.event.ClickEnchantEvent;
import com.SirBlobman.blobcatraz.enchant.event.ClickEnchantEvent.ClickType;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class Fireball implements Listener
{
	public static final String FIREBALL = Enchant.FIREBALL.getName();
	private final String fireball1 = FIREBALL + " §fI";
	private final String fireball2 = fireball1 + "I";
	private final String fireball3 = fireball2 + "I";
	
	private final Class<org.bukkit.entity.Fireball> fireball = org.bukkit.entity.Fireball.class;
	private final Class<SmallFireball> sfireball = SmallFireball.class;
	
	@EventHandler
	public void fireball(ClickEnchantEvent e)
	{
		List<String> lore = e.getLore();
		ClickType ct = e.getClick();
		Player p = e.getPlayer();
		World w = p.getWorld();
		Location l = p.getLocation();
		if(ct != ClickType.LEFT)
		{
			Location eye = p.getLocation();
			Vector veye = eye.toVector();
			Vector dir = l.getDirection();
			Vector dir2 = dir.multiply(2);
			Vector dir3 = veye.add(dir2);
			Location shoot = dir3.toLocation(w, l.getYaw(), l.getPitch());
			if(lore.contains(fireball1)) w.spawn(shoot, sfireball);
			if(lore.contains(fireball2)) w.spawn(shoot, fireball);
			if(lore.contains(fireball3))
			{
				int i = 4;
				while(i > 0)
				{
					w.spawn(shoot, fireball);
					i--;
				}
			}
		}
	}
}