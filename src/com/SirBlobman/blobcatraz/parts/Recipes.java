package com.SirBlobman.blobcatraz.parts;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipes 
{
	public static void onEnable()
	{
	//Overpowered Sword
		ItemStack overpowered_sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta ops_meta = overpowered_sword.getItemMeta();
		ops_meta.setDisplayName("§4§ki§9Overpowered Sword§4§ki§r");
		ops_meta.setLore(Arrays.asList
				(
						"This sword will kill almost anything",
						"It is Awesome"
				));
		ops_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		overpowered_sword.setItemMeta(ops_meta);
		overpowered_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 32767);
		overpowered_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 32767);
		overpowered_sword.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 32767);
		overpowered_sword.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
		
		
		ShapedRecipe op_sword = new ShapedRecipe(overpowered_sword);
		op_sword.shape("D  ", " D ", "  B");
		op_sword.setIngredient('D', Material.DIAMOND_BLOCK);
		op_sword.setIngredient('B', Material.BLAZE_ROD);
	//Drop Everything Sword
		ItemStack des_sword = new ItemStack(Material.WOOD_SWORD);
		ItemMeta des_meta = des_sword.getItemMeta();
		des_meta.setDisplayName("§eDrop Everything Sword");
		des_meta.setLore(Arrays.asList
				(
						"Whatever this sword kills will drop all its items",
						"Only has 1 use because it creates some lag!"
				));
		des_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		des_sword.setItemMeta(des_meta);
		des_sword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 1000);
		des_sword.setDurability((short) 59);
		
		ShapedRecipe de_sword = new ShapedRecipe(des_sword);
		de_sword.shape("WW ", "WWW", " WD");
		de_sword.setIngredient('W', Material.WOOD);
		de_sword.setIngredient('D', Material.DIAMOND_SWORD);
	//Bukkit: Add Recipes
		Bukkit.addRecipe(op_sword);
		Bukkit.addRecipe(de_sword);
	}
}
