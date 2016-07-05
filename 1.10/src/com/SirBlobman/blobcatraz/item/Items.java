package com.SirBlobman.blobcatraz.item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.Util;

public class Items 
{
	static HashMap<Enchantment, Integer> opEnchants = Util.getAllOPEnchants();
	
	public static ItemStack voteDiamonds(String service, String voter)
	{
		ItemStack voteDiamonds = new ItemStack(Material.DIAMOND);
		ItemMeta meta = voteDiamonds.getItemMeta();
		
		meta.setDisplayName("§1§ki§4Vote§1§ki§r §3Diamonds");
		meta.setLore(Arrays.asList("§1Thanks!", "§fSite: §6" + service, "§fVoter: §1" + voter));
		
		voteDiamonds.setItemMeta(meta);
		voteDiamonds.setAmount(10);
		
		return voteDiamonds;
	}
	
	public static ItemStack voteWood(String service, String voter)
	{
		Random rand = new Random();
		short damage = (short) rand.nextInt(5);
		
		ItemStack voteWood = new ItemStack(Material.WOOD);
		ItemMeta meta = voteWood.getItemMeta();
		
		meta.setDisplayName("§1§ki§4Vote§1§ki§r §cWood");
		meta.setLore(Arrays.asList("§1Thanks!", "§fSite: §6" + service, "§fVoter: §1" + voter));
		
		voteWood.setItemMeta(meta);
		voteWood.setAmount(64);
		voteWood.setDurability(damage);
		
		return voteWood;
	}
	
	public static ItemStack lootSword()
	{
		ItemStack lootSword = new ItemStack(Material.WOOD_SWORD);
		ItemMeta meta = lootSword.getItemMeta();
		
		meta.setDisplayName("§eDrop Everything Sword");
		meta.setLore(Arrays.asList("Whatever this sword kills will drop all of its items"));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		lootSword.setItemMeta(meta);
		lootSword.setDurability((short) 59);
		lootSword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 1000);
		
		return lootSword;
	}
	
	public static ItemStack opSword()
	{
		ItemStack opSword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = opSword.getItemMeta();
		
		meta.setDisplayName("§4§ki§9Overpowered §9Sword§4§ki§r");
		meta.setLore(Arrays.asList("This sword will kill almost anything!"));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
		meta.spigot().setUnbreakable(true);
		
		opSword.setItemMeta(meta);
		opSword.addUnsafeEnchantments(opEnchants);
		opSword.removeEnchantment(Enchantment.LOOT_BONUS_MOBS);
		
		return opSword;
	}
	
	public static ItemStack opBow()
	{
		ItemStack opBow = new ItemStack(Material.BOW);
		ItemMeta meta = opBow.getItemMeta();
		meta.setDisplayName("§4§ki§9Overpowered §9Bow§4§ki§r");
		meta.setLore(Arrays.asList("This bow will kill almost anything!"));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
		meta.spigot().setUnbreakable(true);
		
		opBow.setItemMeta(meta);
		opBow.addUnsafeEnchantments(opEnchants);
		opBow.removeEnchantment(Enchantment.LOOT_BONUS_MOBS);
		
		return opBow;
	}
	
	public static ItemStack portalWand()
	{
		ItemStack portalWand = new ItemStack(Material.STICK);
		ItemMeta meta = portalWand.getItemMeta();
		meta.setDisplayName("§3Blobcatraz §cPortal §cWand");
		meta.setLore(Arrays.asList("Left Click to set Position 1", "Right Click to set Position 2"));
		
		portalWand.setItemMeta(meta);
		portalWand.setAmount(1);
		
		return portalWand;
	}
	
	public static ItemStack sonicScrewdriver()
	{
		ItemStack sonicScrewdriver = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = sonicScrewdriver.getItemMeta();
		
		meta.setDisplayName("§fSonic Screwdriver");
		
		sonicScrewdriver.setItemMeta(meta);
		sonicScrewdriver.setAmount(1);
		
		return sonicScrewdriver;
	}
	
	public static ItemStack lightningRod()
	{
		ItemStack lightningRod = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = lightningRod.getItemMeta();
		
		meta.setDisplayName("§6§lLightning §rRod");
		meta.setLore(Arrays.asList("Creates lightning where you are looking", "Only works up to 200 blocks away"));
		
		lightningRod.setItemMeta(meta);
		lightningRod.setAmount(1);
		
		return lightningRod;
	}
}