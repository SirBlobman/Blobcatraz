package com.SirBlobman.blobcatraz.item;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BItems
{
	private static Map<Enchantment, Integer> openchants = ItemUtil.getOPEnchants();
	private static Enchantment looting = Enchantment.LOOT_BONUS_MOBS;
	
	public static ItemStack vote(Material mat, String service, String voter)
	{
		if(mat == null || service == null || voter == null) return null;
		ItemStack vote = new ItemStack(mat);
		ItemMeta meta = vote.getItemMeta();
		
		String display = Util.format("&1&ki&4Vote&1&ki&r");
		List<String> lore = Arrays.asList
		(
			Util.format("&1Thanks!"),
			Util.format("&fSite: &6" + service),
			Util.format("&fVoter: &3" + voter)
		);
		meta.setDisplayName(display);
		meta.setLore(lore);
		
		vote.setItemMeta(meta);
		return vote;
	}
	
	public static ItemStack loot(Material mat)
	{
		if(mat == null) return null;
		ItemStack loot = new ItemStack(mat);
		ItemMeta meta = loot.getItemMeta();
		
		String display = Util.format("&eDrop Everything");
		List<String> lore = Arrays.asList("More drops from mobs!");
		meta.setDisplayName(display);
		meta.setLore(lore);
		meta.addEnchant(looting, 1000, true);
		
		loot.setItemMeta(meta);
		loot.setDurability((short) 59);
		
		return loot;
	}
	
	public static ItemStack op(Material mat)
	{
		if(mat == null) return null;
		ItemStack op = new ItemStack(mat);
		ItemMeta meta = op.getItemMeta();
		
		String display = Util.format("&4&ki&9Overpowered&4&ki&r");
		List<String> lore = Arrays.asList("Overpowered!");
		meta.setDisplayName(display);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
		
		op.setItemMeta(meta);
		op.addUnsafeEnchantments(openchants);
		ItemUtil.toggleUnbreakable(op);
		
		return op;
	}
	
	public static ItemStack portalWand()
	{
		ItemStack wand = new ItemStack(Material.STICK);
		ItemMeta meta = wand.getItemMeta();
		
		String display = Util.format("&3Blobcatraz &cPortal &cWand");
		List<String> lore = Arrays.asList
		(
			"Position 1: Left Click",
			"Position 2: Right Click"
		);
		meta.setDisplayName(display);
		meta.setLore(lore);
		
		wand.setItemMeta(meta);
		
		return wand;
	}
	
	public static ItemStack sonicScrewdriver()
	{
		ItemStack sonic = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = sonic.getItemMeta();
		
		String display = Util.format("&fSonic Screwdriver");
		meta.setDisplayName(display);
		
		sonic.setItemMeta(meta);
		
		return sonic;
	}
	
	public static ItemStack lightningRod()
	{
		ItemStack rod = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = rod.getItemMeta();
		
		String display = Util.format("&6&lLightning &rRod");
		List<String> lore = Arrays.asList
		(
			"Summons lightning!",
			"Works up to 200 Blocks away"
		);
		meta.setDisplayName(display);
		meta.setLore(lore);
		
		rod.setItemMeta(meta);
		
		return rod;
	}
}