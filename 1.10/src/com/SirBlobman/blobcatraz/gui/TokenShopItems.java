package com.SirBlobman.blobcatraz.gui;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.Util;

public class TokenShopItems 
{
	public static ItemStack mainFiller()
	{
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
		ItemMeta meta = filler.getItemMeta();
		meta.setDisplayName("§f");
		filler.setItemMeta(meta);
		return filler;
	}
	
	public static ItemStack mainHelmet()
	{
		ItemStack armor = new ItemStack(Material.DIAMOND_HELMET, 1);
		ItemMeta meta = armor.getItemMeta();
		meta.setDisplayName("§f§l§m-§8§l§m[-§a§l Helmet §8§l§m-]§f§l§m-");
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		armor.setItemMeta(meta);
		return armor;
	}

	public static ItemStack mainChestplate()
	{
		ItemStack armor = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
		ItemMeta meta = armor.getItemMeta();
		meta.setDisplayName("§f§l§m-§8§l§m[-§a§l Chestplate §8§l§m-]§f§l§m-");
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		armor.setItemMeta(meta);
		return armor;
	}
	
	public static ItemStack mainLeggings()
	{
		ItemStack armor = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
		ItemMeta meta = armor.getItemMeta();
		meta.setDisplayName("§f§l§m-§8§l§m[-§a§l Leggings §8§l§m-]§f§l§m-");
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		armor.setItemMeta(meta);
		return armor;
	}
	
	public static ItemStack mainBoots()
	{
		ItemStack armor = new ItemStack(Material.DIAMOND_BOOTS, 1);
		ItemMeta meta = armor.getItemMeta();
		meta.setDisplayName("§f§l§m-§8§l§m[-§a§l Boots §8§l§m-]§f§l§m-");
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		armor.setItemMeta(meta);
		return armor;
	}

	public static ItemStack mainWeapons()
	{
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta meta = sword.getItemMeta();
		meta.setDisplayName("§f§l§m-§8§l§m[-§a§l Weapons §8§l§m-]§f§l§m-");
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		sword.setItemMeta(meta);
		return sword;
	}
	
	public static ItemStack mainTools()
	{
		ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta meta = pick.getItemMeta();
		meta.setDisplayName("§f§l§m-§8§l§m[-§a§l Tools §8§l§m-]§f§l§m-");
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		pick.setItemMeta(meta);
		return pick;
	}
	
	public static ItemStack toolsFiller()
	{
		ItemStack filler = mainFiller();
		filler.setDurability((short) 5);
		return filler;
	}
	
	private static final String space = "§f ";
	public static ItemStack upgrade(String type, Material mat)
	{
		if(type == null || mat == null) return toolsFiller();
		ItemStack up = new ItemStack(mat, 1);
		ItemMeta meta = up.getItemMeta();
		meta.setDisplayName("§f§l§m---§8§l§m[-§4§l UPGRADE §8§l§m-]§f§l§m---");
		StringBuilder lore = new StringBuilder();
		int i = Util.uncolor(meta.getDisplayName()).length() + 5;
		while(i > 0) {lore.append(space); i--;}
		meta.setLore(Arrays.asList(lore.toString() + type));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		up.setItemMeta(meta);
		return up;
	}
	
	public static ItemStack upgradeEnchant(String type, Enchantment e, Material mat, String desc)
	{
		if(type == null || e == null || mat == null || desc == null) return toolsFiller();
		ItemStack up = new ItemStack(mat, 1);
		ItemMeta meta = up.getItemMeta();
		meta.setDisplayName("§f§l§m-§8§l§m[-§a§l " + type + " §8§l§m-]§f§l§m-");
		meta.setLore(Arrays.asList(desc));
		meta.addEnchant(e, 10, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		up.setItemMeta(meta);
		return up;
	}
	
	public static ItemStack armorFiller()
	{
		ItemStack filler = mainFiller();
		filler.setDurability((short) 15);
		return filler;
	}
	
	public static ItemStack upgradeHelmet(String type, Enchantment e)
	{
		if(type == null || e == null) return armorFiller();
		ItemStack up = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta meta = up.getItemMeta();
		meta.setDisplayName("§f§l§m-§8§l§m[-§a§l " + type + " §8§l§m-]§f§l§m-");
		meta.addEnchant(e, 10, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		up.setItemMeta(meta);
		return up;
	}
}