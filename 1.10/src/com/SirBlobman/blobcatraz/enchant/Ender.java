package com.SirBlobman.blobcatraz.enchant;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Ender implements Listener 
{
	@EventHandler
	public void onShootArrow(EntityShootBowEvent e)
	{
		LivingEntity le = e.getEntity();
		ItemStack bow = e.getBow();
		ItemMeta meta = bow.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		
		if(lore.contains("§7Ender I"))
		{
			shootPearl(le);
			e.setCancelled(true);
		}
	}
	
	private void shootPearl(LivingEntity le)
	{
		if(le instanceof Player)
		{
			Player p = (Player) le;
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
		
		EnderPearl enderPearl = le.launchProjectile(EnderPearl.class);
		enderPearl.setShooter(le);
	}
}