package com.SirBlobman.blobcatraz.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import com.SirBlobman.blobcatraz.Util;

public class CommandRecipe implements CommandExecutor,Listener
{
	Server s = Bukkit.getServer();
	static List<Player> inRecipe = new ArrayList<Player>();
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;

		if(label.equalsIgnoreCase("recipe") || label.equalsIgnoreCase("formula"))
		{
			if(!Util.hasPermission(cs, "recipe")) return true;
			if(args.length == 0) {cs.sendMessage(Util.notEnoughArguments); return false;}
			Material mat = null;
			short meta = 0;
			int recipe_number = 1;
			if(args.length >= 1)
			{
				String[] info = args[0].split(":");
				mat = Material.matchMaterial(info[0]);
				if(mat == null) {cs.sendMessage(Util.blobcatraz + "Invalid Item Type" + info[0]); return true;}
				if(info.length == 2) try{meta = Short.parseShort(info[1]);} catch(Exception ex) {cs.sendMessage(Util.blobcatraz + "Invalid metadata for " + mat.toString()); return true;}
			}
			if(args.length >= 2)
			{
				try{recipe_number = Integer.parseInt(args[1]);} catch(Exception ex) {cs.sendMessage(Util.blobcatraz + "Invalid recipe number " + args[1]); return true;}
			}
			ItemStack is = new ItemStack(mat, 1, meta);
			if(is != null) recipe(p, is, recipe_number);
			return true;
		}
		return false;
	}
	
	private void recipe(Player p, ItemStack is, int number)
	{
		List<Recipe> recipes = s.getRecipesFor(is);
		switch(recipes.size())
		{
		case 0:
			p.sendMessage(Util.blobcatraz + "That item doesn't have any recipes");
			return;
		case 1:
			show(p, is, 1);
			return;
		default:
			p.sendMessage(String.format(Util.blobcatraz + "That item has %s recipes", recipes.size()));
			show(p, is, number);
			return;
		}
	}
	
	private void show(Player p, ItemStack is, int number)
	{
		List<Recipe> recipes = s.getRecipesFor(is);
		if(number <= 0) {p.sendMessage(Util.blobcatraz + "Invalid recipe number " + number); return;}
		Recipe recipe = recipes.get(number - 1);
		if(recipe == null) return;
		if(recipe instanceof FurnaceRecipe)
		{
			FurnaceRecipe fr = (FurnaceRecipe) recipe;
			furnace(p, fr);
			return;
		}
		if(recipe instanceof ShapedRecipe)
		{
			ShapedRecipe sr = (ShapedRecipe) recipe;
			shaped(p, sr);
			return;
		}
		else
		{
			ShapelessRecipe sr = (ShapelessRecipe) recipe;
			shapeless(p, sr);
			return;
		}
	}
	
	
	public void furnace(Player p, FurnaceRecipe fr)
	{
		if(p == null || fr == null) return;
		ItemStack i = fr.getInput();
		String input = Util.getItemName(i);
		String output = Util.getItemName(fr.getResult());
		float xp = fr.getExperience();
		p.sendMessage(Util.blobcatraz + "Furnace Recipe:");
		p.sendMessage("  §lInput:§r " + input);
		p.sendMessage("  §lOutput:§r " + output);
		p.sendMessage("  §lExperience:§r " + xp);
	}
	
	public void shaped(Player p, ShapedRecipe sr)
	{
		if(p == null || sr == null) return;
		Map<Character, ItemStack> ingredients = sr.getIngredientMap();
		String[] shape = sr.getShape();
		p.closeInventory();
		InventoryView iv = p.openWorkbench(null, true);
		inRecipe.add(p);
		for(int j = 0; j < shape.length; j++)
		{
			for(int k = 0; k < shape[j].length(); k++)
			{
				ItemStack is = ingredients.get(shape[j].toCharArray()[k]);
				if(is != null)
				{
					if(is.getDurability() == Short.MAX_VALUE) is.setDurability((short) 0);
					iv.getTopInventory().setItem(j * 3 + k + 1, is);
				}
			}
		}
	}
	
	public void shapeless(Player p, ShapelessRecipe sr)
	{
		List<ItemStack> ingredients = sr.getIngredientList();
		p.closeInventory();
		InventoryView iv = p.openWorkbench(null, true);
		inRecipe.add(p);
		for(int i = 0; i < ingredients.size(); i++)
		{
			ItemStack is = ingredients.get(i);
			if(is.getDurability() == Short.MAX_VALUE) is.setDurability((short) 0);
			iv.setItem(i + 1, is);
		}
	}
	
	@EventHandler
	public void showRecipe(InventoryClickEvent e)
	{
		HumanEntity he = e.getWhoClicked();
		Inventory i = e.getClickedInventory();
		if(!(he instanceof Player)) return;
		Player p = (Player) he;
		if(i.getType() == InventoryType.WORKBENCH && inRecipe.contains(p))
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void showRecipe(InventoryCloseEvent e)
	{
		HumanEntity he = e.getPlayer();
		if(!(he instanceof Player)) return;
		Player p = (Player) he;
		Inventory i = e.getInventory();
		if(i.getType() == InventoryType.WORKBENCH && inRecipe.contains(p)) 
		{
			i.clear();
			inRecipe.remove(p);
		}
	}
}