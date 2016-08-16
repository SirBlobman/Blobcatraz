package com.SirBlobman.blobcatraz.listener;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.WorldGuardChecker;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.config.ConfigLanguage;
import com.google.common.collect.Maps;

public class AutoPickup implements Listener 
{
	@EventHandler(priority=EventPriority.HIGHEST)
	public void autoPickup(BlockBreakEvent e)
	{
		Block b = e.getBlock();
		Player p = e.getPlayer();
		int xp = e.getExpToDrop();
		
		final Material original = b.getType();
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		ItemMeta meta = held.getItemMeta();
		Map<Enchantment, Integer> enchants = Maps.newHashMap();
		try
		{
			enchants = meta.getEnchants();
		} catch(Exception ex) {}
		if(enchants == null) enchants = Maps.newHashMap();
		Enchantment silk = Enchantment.SILK_TOUCH;
		Enchantment fortune = Enchantment.LOOT_BONUS_BLOCKS;
		Collection<ItemStack> drops = b.getDrops(held);
		if(p.getGameMode() == GameMode.CREATIVE) return;
		if(enchants.containsKey(silk)) return;
		if(!ConfigDatabase.canAutoPickup(p)) return;
		if(!WorldGuardChecker.canBreak(p, b)) return;
		int fortuneLevel = 0;
		try{fortuneLevel = meta.getEnchantLevel(fortune);}
		catch(Exception ex) {}
		p.giveExp(xp);
		e.setExpToDrop(0);
		for(ItemStack drop : drops)
		{
			int firstEmpty = pi.firstEmpty();
			if(firstEmpty == -1) {inventoryFull(p); e.setCancelled(true); break;}
			drop.setAmount(Math.min(64, getDropCount(original, fortuneLevel, new Random())));
			pi.addItem(drop);
			b.setType(Material.AIR);
		}
	}
	
	private void inventoryFull(Player p)
	{
		if(p == null) return;
		Util.pingPlayer(p);
		String title = ConfigLanguage.getMessage("title.inventoryFull");
		String subtitle = ConfigLanguage.getMessage("title.inventoryFull2");
		Util.sendTitle(p, title, subtitle);
		Util.sendAction1_10(p, title);
	}
	
	private int getDropCount(Material m, int fortune, Random r)
	{
		if(fortune > 0)
		{
			int drops = r.nextInt(fortune + 2) - 1;
			if(drops < 0) drops = 0;
			return specialFortune(m, r) * (drops + 1);
		}
		return specialFortune(m, r);
	}
	
	private int specialFortune(Material mat, Random r)
	{
		return mat == Material.LAPIS_ORE ? 4 + r.nextInt(5) : 1;
	}
}