package com.SirBlobman.blobcatraz.enchants;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class XPStealEnchant implements Listener
{
	
	@EventHandler
	public void onPlayerHitWithXPStealEnchant(EntityDamageByEntityEvent e)
	{
		if(!(e.getDamager().getType() == EntityType.PLAYER))
		{
			return;
		}
		if(!(e.getEntity().getType() == EntityType.PLAYER))
		{
			return;
		}
		
		Player der = (Player) e.getDamager();
		Player ded = (Player) e.getEntity();
		
		ItemStack held_item = der.getItemInHand();
		if(held_item == null)
		{
			return;
		}
		ItemMeta meta = held_item.getItemMeta();
		if(meta == null)
		{
			return;
		}
		List<String> lore = meta.getLore();
		if(lore == null)
		{
			return;
		}
		
		if(lore.contains("§66XP Drain I"))
		{
			if(ded.getLevel() >= 5)
			{
				ded.setLevel(ded.getLevel() - 5);
				der.setLevel(der.getLevel() + 5);
			}
		}
	}
}
