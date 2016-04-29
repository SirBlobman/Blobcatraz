package com.SirBlobman.blobcatraz.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes 
{
	public static void loadRecipes()
	{
	//Overpowered Sword
		ItemStack opSword = OverpoweredSword.opsword();
		
		ShapedRecipe op_sword = new ShapedRecipe(opSword);
		op_sword.shape
		(
			"D  ", 
			" D ", 
			"  B"
		);
		op_sword.setIngredient('D', Material.DIAMOND_BLOCK);
		op_sword.setIngredient('B', Material.BLAZE_ROD);
		
	//Drop Everything Sword
		ItemStack lSword = LootingSword.lootSword();
		
		ShapedRecipe de_sword = new ShapedRecipe(lSword);
		de_sword.shape
		(
			"WW ", 
			"WWW", 
			" WD"
		);
		de_sword.setIngredient('W', Material.WOOD);
		de_sword.setIngredient('D', Material.DIAMOND_SWORD);
	//Lightning Rod
		ItemStack lRod = LightningRod.lrod();
		
		ShapedRecipe l_rod = new ShapedRecipe(lRod);
		l_rod.shape
		(
			"B  ",
			"BBB",
			"B B"
		);
		l_rod.setIngredient('B', Material.BLAZE_ROD);
		
	//Bukkit: Add Recipes
		Bukkit.addRecipe(op_sword);
		Bukkit.addRecipe(de_sword);
		Bukkit.addRecipe(l_rod);
	}
}
