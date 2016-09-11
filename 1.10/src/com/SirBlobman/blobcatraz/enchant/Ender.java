package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.GameMode;
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

import com.SirBlobman.blobcatraz.enchant.event.BowEnchantEvent;

public class Ender implements Listener 
{
	public static final String ender = "§7Ender ";
	private final String ender1 = ender + "I";
	
	@EventHandler
	public void ender(BowEnchantEvent e)
	{
		List<String> lore = e.getLore();
		ProjectileSource shooter = e.getShooter();
		if(lore.contains(ender1)) shootPearl(shooter);
	}
	
	private void shootPearl(ProjectileSource pjs)
	{
		if(pjs instanceof Player)
		{
			Player p = (Player) pjs;
			PlayerInventory pi = p.getInventory();
			GameMode gm = p.getGameMode();
			
			if(gm == GameMode.SURVIVAL || gm == GameMode.ADVENTURE)
			{
				ItemStack enderPearl = new ItemStack(Material.ENDER_PEARL);
				enderPearl.setAmount(1);
				if(!pi.contains(enderPearl)) return;
				
				if(!pi.getItemInMainHand().getEnchantments().containsKey(Enchantment.ARROW_INFINITE)) pi.remove(enderPearl);
			}
			p.playSound(p.getLocation(), Sound.ENTITY_ENDERPEARL_THROW, 1, 1);
		}
		
		EnderPearl enderPearl = pjs.launchProjectile(EnderPearl.class);
		enderPearl.setShooter(pjs);
	}
}