package com.SirBlobman.blobcatraz.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class Recipes 
{
	public static void loadRecipes()
	{
		//OP Sword
		ItemStack opSword = Items.opSword();
		MaterialData opData = opSword.getData();
		ShapedRecipe op_sword = new ShapedRecipe(opSword);
		op_sword.shape("D  ", " D ", "  B");
		op_sword.setIngredient('D', Material.DIAMOND_BLOCK);
		op_sword.setIngredient('B', Material.BLAZE_ROD);
		
		//OP Bow
		ItemStack opBow = Items.opBow();
		ShapedRecipe op_bow = new ShapedRecipe(opBow);
		op_bow.shape(" BS", "B S", " BS");
		op_bow.setIngredient('B', Material.BLAZE_ROD);
		op_bow.setIngredient('S', Material.STRING);
		
		//Loot Sword
		ItemStack lootSword = Items.lootSword();
		ShapedRecipe de_sword = new ShapedRecipe(lootSword);
		de_sword.shape("WW ", "WWW", " WD");
		de_sword.setIngredient('W', Material.WOOD);
		de_sword.setIngredient('D', opData);
		
		//Lightning Rod
		ItemStack lightningRod = LightningRod.lightningRod();
		ShapedRecipe l_rod = new ShapedRecipe(lightningRod);
		l_rod.shape("B  ", "BBB", "B B");
		l_rod.setIngredient('B', Material.BLAZE_ROD);
		
		//Bukkit: Add Recipes
		Bukkit.addRecipe(op_sword);
		Bukkit.addRecipe(op_bow);
		Bukkit.addRecipe(de_sword);
		Bukkit.addRecipe(l_rod);
	}
}