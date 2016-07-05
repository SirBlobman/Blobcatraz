package com.SirBlobman.blobcatraz.item;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class Recipes 
{
	static ItemStack opSword = Items.opSword();
	static ItemStack opBow = Items.opBow();
	static ItemStack lootSword = Items.lootSword();
	static ItemStack lightningRod = Items.lightningRod();
	static MaterialData opSwordData = opSword.getData();
	
	public static void loadRecipes()
	{
		ShapedRecipe op_sword = new ShapedRecipe(opSword);
		op_sword.shape("D  ", " D ", "  B");
		op_sword.setIngredient('D', Material.DIAMOND_BLOCK);
		op_sword.setIngredient('B', Material.BLAZE_ROD);
		
		ShapedRecipe op_bow = new ShapedRecipe(opBow);
		op_bow.shape(" BW", "B W", " BW");
		op_bow.setIngredient('B', Material.BLAZE_ROD);
		op_bow.setIngredient('W', Material.WOOL);
		
		ShapedRecipe loot_sword = new ShapedRecipe(lootSword);
		loot_sword.shape("WW ", "WWW", " WD");
		loot_sword.setIngredient('D', opSwordData);
		loot_sword.setIngredient('W', Material.WOOD);
		
		ShapedRecipe lightning_rod = new ShapedRecipe(lightningRod);
		lightning_rod.shape("B  ", "BBB", "B B");
		lightning_rod.setIngredient('B', Material.BLAZE_ROD);
		
		
		Bukkit.addRecipe(op_sword);
		Bukkit.addRecipe(op_bow);
		Bukkit.addRecipe(loot_sword);
		Bukkit.addRecipe(lightning_rod);
	}
	
	public static void unloadRecipes()
	{
		Iterator<Recipe> ir = Bukkit.recipeIterator();
		while(ir.hasNext())
		{
			Recipe r = ir.next();
			
			if(r.getResult() == opSword) ir.remove();
			if(r.getResult() == opBow) ir.remove();
			if(r.getResult() == lootSword) ir.remove();
			if(r.getResult() == lightningRod) ir.remove();
		}
	}
}