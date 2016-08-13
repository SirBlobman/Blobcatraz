package com.SirBlobman.blobcatraz.listener;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.WorldGuardChecker;
import com.SirBlobman.blobcatraz.config.ConfigLanguage;

public class AutoPickup implements Listener 
{
	@EventHandler(priority=EventPriority.HIGHEST)
	public void autoPickup(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		PlayerInventory pi = p.getInventory();
		Block b = e.getBlock();
		ItemStack tool = pi.getItemInMainHand();
		if(WorldGuardChecker.canBreak(p, b))
		{
			e.setCancelled(true);
			Collection<ItemStack> drops = b.getDrops(tool);
			int firstEmpty = pi.firstEmpty();
			if(p.getGameMode() == GameMode.CREATIVE) firstEmpty = 0;
			if(firstEmpty == -1) {inventoryFull(p); return;}
			b.setType(Material.AIR);
			for(ItemStack is : drops)
			{
				HashMap<Integer, ItemStack> notAdded = pi.addItem(is);
				if(!notAdded.keySet().isEmpty()) {inventoryFull(p); break;}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void inventoryFull(Player p)
	{
		Util.pingPlayer(p);
		String title = ConfigLanguage.getMessage("title.inventoryFull");
		String subtitle = ConfigLanguage.getMessage("title.inventoryFull2");
		p.sendTitle(title, subtitle);
	}
}