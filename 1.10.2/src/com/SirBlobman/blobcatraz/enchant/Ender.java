package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import com.SirBlobman.blobcatraz.enchant.event.BowEnchantEvent;
import com.SirBlobman.blobcatraz.utility.ItemUtil;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.projectiles.ProjectileSource;

public class Ender implements Listener
{
	public static final String ENDER = Enchant.ENDER.getName();
	private final String ender1 = ENDER + " §fI";
	
	@EventHandler
	public void ender(BowEnchantEvent e)
	{
		List<String> lore = e.getLore();
		ProjectileSource shooter = e.getShooter();
		if(lore.contains(ender1)) shoot(shooter);
	}
	
	private void shoot(ProjectileSource pjs)
	{
		Class<EnderPearl> pearl = EnderPearl.class;
		if(pjs instanceof Player)
		{
			Player p = (Player) pjs;
			PlayerInventory pi = p.getInventory();
			GameMode gm = p.getGameMode();
			GameMode s = GameMode.SURVIVAL;
			GameMode a = GameMode.ADVENTURE;
			if(gm == s || gm == a)
			{
				ItemStack ender = new ItemStack(Material.ENDER_PEARL, 1);
				if(pi.contains(ender))
				{
					ItemStack held = ItemUtil.getHeld(p);
					Enchantment infinity = Enchantment.ARROW_INFINITE;
					if(!held.containsEnchantment(infinity)) pi.remove(ender);
				}
				else return;
			}
			Location location = p.getLocation();
			Sound sound = Sound.ENTITY_ENDERPEARL_THROW;
			p.playSound(location, sound, 1, 1);
		}
		
		EnderPearl ep = pjs.launchProjectile(pearl);
		ep.setShooter(pjs);
	}
}