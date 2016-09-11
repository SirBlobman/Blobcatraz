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

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WorldGuardUtil;
import com.google.common.collect.Maps;

public class AutoPickup implements Listener
{
	private final Enchantment silk = Enchantment.SILK_TOUCH;
	private final Enchantment fortune = Enchantment.LOOT_BONUS_BLOCKS;
	private Random r = new Random();
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void autoPickup(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		Block b = e.getBlock();
		try{if(!WorldGuardUtil.canBreak(p, b)) return;} catch(Exception | Error ex) {}
		if(!ConfigDatabase.canAutoPickup(p)) return;
		if(p.getGameMode() == GameMode.CREATIVE) return;
		
		PlayerInventory pi = p.getInventory();
		int xp = e.getExpToDrop();
		final Material original = b.getType();
		ItemStack held = ItemUtil.getHeld(p);
		ItemMeta meta = held.getItemMeta();
		Map<Enchantment, Integer> enchants = Maps.newHashMap();
		try{enchants = meta.getEnchants();} catch(Exception ex) {}
		if(enchants == null) enchants = Maps.newHashMap();
		if(enchants.containsKey(silk)) return;
		
		Collection<ItemStack> drops = b.getDrops(held);
		int fortuneLvl = 0;
		try{fortuneLvl = meta.getEnchantLevel(fortune);} catch(Exception ex) {}
		p.giveExp(xp);
		e.setExpToDrop(0);
		for(ItemStack drop : drops)
		{
			int first = pi.firstEmpty();
			if(first == -1) {full(p); e.setCancelled(true); break;}
			int dropCount = getDropCount(original, fortuneLvl, r);
			int amount = Math.min(64, dropCount);
			drop.setAmount(amount);
			pi.addItem(drop);
			b.setType(Material.AIR);
		}
	}
	
	private void full(Player p)
	{
		if(p == null) return;
		PlayerUtil.ping(p);
		String title = Util.message("title.inventoryFull");
		String subtitle = Util.message("subtitle.inventoryFull");
		PlayerUtil.sendTitle(p, title, subtitle);
		PlayerUtil.sendAction1_10(p, title);
	}
	
	private int getDropCount(Material m, int fortune, Random r)
	{
		if(fortune == 0) return 1;
		if(fortune > 64) fortune = 64;
		if(m == Material.LAPIS_ORE) return (int) ((4 * fortune) * r.nextDouble()) + 1;
		else return (int) (fortune * r.nextDouble()) + 1;
	}
}