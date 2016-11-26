package com.SirBlobman.blobcatraz.item;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class BItems
{
	private static Map<Enchantment, Integer> enchants = ItemUtil.overpowered();
	private static Enchantment looting = Enchantment.LOOT_BONUS_MOBS;
	
	public static ItemStack vote(Material mat, String service, String voter)
	{
		if(mat == null || service == null || voter == null) return null;
		ItemStack vote = new ItemStack(mat);
		ItemMeta meta = vote.getItemMeta();
		
		String name = Util.option("item.vote.name");
		List<String> lore = Arrays.asList
		(
			Util.format("&1Thanks!"),
			Util.format("&fSite: &6%s", service),
			Util.format("&fVoter: &3%s", voter)
		);
		meta.setDisplayName(name);
		meta.setLore(lore);
		
		vote.setItemMeta(meta);
		return vote;
	}
	
	public static ItemStack loot(Material mat)
	{
		if(mat == null) return null;
		ItemStack loot = new ItemStack(mat);
		ItemMeta meta = loot.getItemMeta();
		
		String name = Util.format("&eLooter");
		List<String> lore = Arrays.asList("More mob drops!");
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addEnchant(looting, 1000, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		loot.setItemMeta(meta);
		return loot;
	}
	
	public static ItemStack op(Material mat)
	{
		if(mat == null) return null;
		ItemStack op = new ItemStack(mat);
		op.addUnsafeEnchantments(enchants);
		ItemUtil.toggleUnbreakable(op);
		ItemMeta meta = op.getItemMeta();
		
		String name = Util.format("&4&ki&9Overpowered&4&ki&r");
		List<String> lore = Arrays.asList("Overpowered!");
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
		
		op.setItemMeta(meta);
		return op;
	}
	
	public static ItemStack portalWand()
	{
		ItemStack wand = new ItemStack(Material.STICK);
		ItemMeta meta = wand.getItemMeta();
		
		String name = Util.format("&cPortal Wand");
		List<String> lore = Arrays.asList
		(
			"Position 1: Left Click",
			"Position 2: Right Click"
		);
		meta.setDisplayName(name);
		meta.setLore(lore);
		
		wand.setItemMeta(meta);
		return wand;
	}
	
	public static ItemStack sonicScrewdriver()
	{
		ItemStack sonic = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = sonic.getItemMeta();
		
		String name = Util.format("&fSonic Screwdriver");
		meta.setDisplayName(name);
		
		sonic.setItemMeta(meta);
		return sonic;
	}
	
	public static ItemStack lightningRod()
	{
		ItemStack rod = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = rod.getItemMeta();
		
		String name = Util.format("&f&lLightning &rRod");
		List<String> lore = Arrays.asList
		(
			"Summons lightning!",
			"Works up to 200 Blocks away"
		);
		meta.setDisplayName(name);
		meta.setLore(lore);
		
		rod.setItemMeta(meta);
		return rod;
	}
	
	public static ItemStack sandWand()
	{
		ItemStack wand = new ItemStack(Material.CARROT_STICK);
		ItemMeta meta = wand.getItemMeta();
		
		String name = Util.format("&eSand &cWand");
		List<String> lore = Arrays.asList("Turns a block into sand");
		meta.setDisplayName(name);
		meta.setLore(lore);
		
		wand.setItemMeta(meta);
		return wand;
	}
	
	public static ItemStack uSandWand()
	{
		ItemStack wand = sandWand();
		ItemMeta meta = wand.getItemMeta();
		
		String name = Util.format("&5Upgraded &eSand &cWand");
		List<String> lore = Arrays.asList("Turns a 5x5x5 area of blocks into sand");
		meta.setDisplayName(name);
		meta.setLore(lore);
		
		wand.setItemMeta(meta);
		return wand;
	}
}