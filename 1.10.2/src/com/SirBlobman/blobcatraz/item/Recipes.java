package com.SirBlobman.blobcatraz.item;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes
{
	private static ItemStack opSword = BItems.op(Material.DIAMOND_SWORD);
	private static ItemStack lootSword = BItems.loot(Material.WOOD_SWORD);
	private static ItemStack opBow = BItems.op(Material.BOW);
	private static ItemStack lightningRod = BItems.lightningRod();
	
	public static void load()
	{
		ShapedRecipe op_sword = new ShapedRecipe(opSword);
		op_sword.shape("D##", "#D#", "##B");
		op_sword.setIngredient('D', Material.DIAMOND_BLOCK);
		op_sword.setIngredient('B', Material.BLAZE_ROD);
		
		ShapedRecipe op_bow = new ShapedRecipe(opBow);
		op_bow.shape("#BW", "B#W", "#BW");
		op_bow.setIngredient('B', Material.BLAZE_POWDER);
		op_bow.setIngredient('W', Material.WOOL);
		
		ShapedRecipe loot_sword = new ShapedRecipe(lootSword);
		loot_sword.shape("WW#", "WWW", "#WD");
		loot_sword.setIngredient('D', opSword.getData());
		loot_sword.setIngredient('W', Material.WOOD);
		
		ShapedRecipe rod = new ShapedRecipe(lightningRod);
		rod.shape("B##", "BBB", "B#B");
		rod.setIngredient('B', Material.BLAZE_ROD);
		
		Bukkit.addRecipe(op_sword);
		Bukkit.addRecipe(op_bow);
		Bukkit.addRecipe(loot_sword);
		Bukkit.addRecipe(rod);
	}
	
	public static void unload()
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