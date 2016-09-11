package com.SirBlobman.blobcatraz.gui;

import java.util.Arrays;
import java.util.List;

import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class TokenShopItems
{
	private static final ItemFlag[] HIDE = new ItemFlag[] {ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE};
	private static final Enchantment UNBREAKING = Enchantment.DURABILITY;
	private static final String SPACE = Util.format(" &f");
	
	public static ItemStack filler() {return new ItemStack(Material.AIR);}
	
	public static ItemStack mainArmor(Material type)
	{
		ItemStack armor = new ItemStack(type);
		ItemMeta meta = armor.getItemMeta();
		String name = Util.format("&f&l&m-&8&l&m[-&a&l %s &8&l&m-]&f&l&m-");
		if(type.name().contains("HELMET")) name = String.format(name, "Helmet");
		if(type.name().contains("CHESTPLATE")) name = String.format(name, "Chestplate");
		if(type.name().contains("LEGGINGS")) name = String.format(name, "Leggings");
		if(type.name().contains("BOOTS")) name = String.format(name, "Boots");
		meta.setDisplayName(name);
		meta.addEnchant(UNBREAKING, 1, false);
		meta.addItemFlags(HIDE);
		armor.setItemMeta(meta);
		return armor;
	}
	
	public static ItemStack mainWeapons()
	{
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = sword.getItemMeta();
		String name = Util.format("&f&l&m-&8&l&m[-&a&l Weapons &8&l&m-]&f&l&m-");
		meta.setDisplayName(name);
		meta.addEnchant(UNBREAKING, 1, false);
		meta.addItemFlags(HIDE);
		sword.setItemMeta(meta);
		return sword;
	}
	
	public static ItemStack mainTools()
	{
		ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta meta = pick.getItemMeta();
		String name = Util.format("&f&l&m-&8&l&m[-&a&l Tools &8&l&m-]&f&l&m-");
		meta.setDisplayName(name);
		meta.addEnchant(UNBREAKING, 1, false);
		meta.addItemFlags(HIDE);
		pick.setItemMeta(meta);
		return pick;
	}
	
	public static ItemStack upgrade(String type, Material mat)
	{
		if(type == null || mat == null) return filler();
		ItemStack up = new ItemStack(mat);
		ItemMeta meta = up.getItemMeta();
		String name = Util.format("&f&l&m---&8&l&m[-&4&l UPGRADE &8&l&m-]&f&l&m---");
		StringBuilder lorer = new StringBuilder();
		int i = Util.uncolor(name).length();
		while(i > 0) {lorer.append(SPACE); i--;}
		List<String> lore = Arrays.asList(lorer.toString() + type);
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addItemFlags(HIDE);
		up.setItemMeta(meta);
		return up;
	}
	
	public static ItemStack upgrade(String type, Enchantment e, Material mat, String description)
	{
		if(type == null || e == null || mat == null || description == null) return filler();
		ItemStack up = new ItemStack(mat);
		ItemMeta meta = up.getItemMeta();
		String name = Util.format("&f&l&m-&8&l&m[-&a&l " + type + " &8&l&m-]&f&l&m-");
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(description));
		meta.addEnchant(e, 10, false);
		meta.addItemFlags(HIDE);
		up.setItemMeta(meta);
		return up;
	}
	
	public static ItemStack helmetUpgrade(String type, Enchantment e)
	{
		if(type == null || e == null) return filler();
		ItemStack up = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta meta = up.getItemMeta();
		String name = Util.format("&f&l&m-&8&l&m[-&a&l " + type + " &8&l&m-]&f&l&m-");
		meta.setDisplayName(name);
		meta.addEnchant(e, 10, false);
		meta.addItemFlags(HIDE);
		up.setItemMeta(meta);
		return up;
	}
	
	public static ItemStack back()
	{
		ItemStack back = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = back.getItemMeta();
		String name = Util.format("&4&lGo Back");
		meta.setDisplayName(name);
		back.setItemMeta(meta);
		return back;
	}
}