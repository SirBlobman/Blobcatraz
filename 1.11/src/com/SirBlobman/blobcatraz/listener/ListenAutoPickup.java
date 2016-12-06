package com.SirBlobman.blobcatraz.listener;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
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

public class ListenAutoPickup implements Listener
{
	private static final Enchantment SILK = Enchantment.SILK_TOUCH;
	private static final Enchantment BONUS = Enchantment.LOOT_BONUS_BLOCKS;
	private static final Random R = new Random();
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void ap(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		try
		{
			boolean b1 = WorldGuardUtil.canBreak(p, b);
			if(!b1) return;
		} catch(Throwable ex) {}
		
		boolean b2 = !ConfigDatabase.autopickup(p);
		if(b2) return;
		
		GameMode gm = p.getGameMode();
		boolean b3 = (gm == GameMode.CREATIVE);
		if(b3) return;
		
		int xp = e.getExpToDrop();
		autopickup(p, b, xp, e);
		e.setExpToDrop(0);
	}
	
	private void autopickup(Player p, Block b, int xp, Cancellable c)
	{
		PlayerInventory pi = p.getInventory();
		Material original = b.getType();
		
		ItemStack held = ItemUtil.held(p);
		ItemMeta meta = held.getItemMeta();
		Map<Enchantment, Integer> enchants = Maps.newHashMap();
		try{enchants = meta.getEnchants();}
		catch(Exception ex) {enchants = Maps.newHashMap();}
		if(enchants.containsKey(SILK)) return;
		
		Collection<ItemStack> drops = b.getDrops(held);
		int fortune = 0;
		try{fortune = enchants.get(BONUS);}
		catch(Exception ex) {}
		
		p.giveExp(xp);
		for(ItemStack is : drops)
		{
			int drop = count(original, fortune);
			drop = Math.min(drop, 64);
			is.setAmount(drop);
			b.setType(Material.AIR);
			Map<Integer, ItemStack> left = pi.addItem(is);
			if(!left.isEmpty())
			{
				full(p);
				c.setCancelled(true);
				break;
			}
		}
	}
	
	private void full(Player p)
	{
		if(p == null) return;
		PlayerUtil.ping(p);
		String title = Util.option("command.autopickup.inventory full1");
		String suble = Util.option("command.autopickup.inventory full2");
		PlayerUtil.title(p, title, suble);
		PlayerUtil.action1_11(p, title);
	}
	
	private int count(Material mat, int fortune)
	{
		if(fortune == 0) return 1;
		if(fortune > 64) fortune = 64;
		if(mat == Material.LAPIS_ORE)
		{
			double drop = ((4 * fortune) * R.nextDouble()) + 1;
			return ((int) drop);
		}
		
		double drop = (fortune * R.nextDouble()) + 1;
		return ((int) drop);
	}
}