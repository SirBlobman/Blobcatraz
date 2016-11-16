package com.SirBlobman.blobcatraz.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

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
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class CommandRecipe implements CommandExecutor,Listener
{
	Server S = Bukkit.getServer();
	static List<Player> in = new ArrayList<Player>();
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("recipe"))
			{
				String permission = "blobcatraz.recipe";
				if(!PlayerUtil.hasPermission(p, permission)) return true;
				if(args.length == 0)
				{
					cs.sendMessage(Util.NEA);
					return true;
				}
				
				Material mat = null;
				short meta = 0;
				int number = 1;
								
				if(args.length >= 1)
				{
					String[] info = args[0].split(":");
					mat = Material.matchMaterial(info[0]);
					if(mat == null)
					{
						cs.sendMessage(Util.blobcatraz + "Invalid Item Type: " + info[0]);
						return true;
					}
					
					if(info.length == 2)
					{
						try{meta = Short.parseShort(info[1]);}
						catch(Exception ex)
						{
							cs.sendMessage(Util.blobcatraz + "Invalid meta for " + mat.toString());
							return true;
						}
					}
				}
				if(args.length >= 2)
				{
					try{number = Integer.parseInt(args[1]);}
					catch(Exception ex)
					{
						cs.sendMessage(Util.blobcatraz + "Invalid recipe number " + args[1]);
						return true;
					}
				}
				
				ItemStack item = new ItemStack(mat, 1, meta);
				if(!ItemUtil.isAir(item)) recipe(p, item, number);
				return true;
			}
			return false;
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	@EventHandler
	public void show(InventoryClickEvent e)
	{
		if(e instanceof InventoryCreativeEvent) return;
		HumanEntity he = e.getWhoClicked();
		Inventory i = e.getClickedInventory();
		if(i == null) return;
		if(he instanceof Player)
		{
			Player p = (Player) he;
			InventoryType it = i.getType();
			if(it == null) return;
			InventoryType table = InventoryType.WORKBENCH;
			InventoryType furnace = InventoryType.FURNACE;
			if(it == table || it == furnace)
			{
				if(in.contains(p)) e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void show(InventoryCloseEvent e)
	{
		HumanEntity he = e.getPlayer();
		Inventory i = e.getInventory();
		if(he instanceof Player)
		{
			Player p = (Player) he;
			InventoryType it = i.getType();
			InventoryType table = InventoryType.WORKBENCH;
			InventoryType furnace = InventoryType.FURNACE;
			if(it == table || it == furnace)
			{
				if(in.contains(p))
				{
					i.clear();
					in.remove(p);
				}
			}
		}
	}
	
	private void recipe(Player p, ItemStack is, int number)
	{
		List<Recipe> list = S.getRecipesFor(is);
		if(list.size() > 0 && list.size() < number) 
		{
			p.sendMessage(Util.blobcatraz + "That item only has " + list.size() + " recipes");
			number = list.size();
		}
		switch(list.size())
		{
		case 0:
			p.sendMessage(Util.blobcatraz + "That item doesn't have any recipes");
			return;
		case 1:
			show(p, is, 1);
			return;
		default: 
			String msg = String.format(Util.blobcatraz + "That item has %s recipes", list.size());
			p.sendMessage(msg);
			show(p, is, number);
			return;
		}
	}
	
	private void show(Player p, ItemStack is, int number)
	{
		List<Recipe> list = S.getRecipesFor(is);
		if(number <= 0)
		{
			p.sendMessage(Util.blobcatraz + "Invalid Recipe: " + number);
			return;
		}
		
		Recipe recipe = list.get(number - 1);
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
		if(recipe instanceof ShapelessRecipe)
		{
			ShapelessRecipe sr = (ShapelessRecipe) recipe;
			shapeless(p, sr);
			return;
		}
		else
		{
			p.sendMessage(Util.blobcatraz + "Invalid Recipe: " + number);
			return;
		}
	}
	
	private void furnace(Player p, FurnaceRecipe fr)
	{
		if(p == null || fr == null) return;
		ItemStack i = fr.getInput();
		ItemStack o = fr.getResult();
		fix(i, o);
		p.closeInventory();
		
		in.add(p);
		Inventory gui = Bukkit.createInventory(null, InventoryType.FURNACE, "Furnace Recipe");
		gui.setItem(0, i);
		gui.setItem(2, o);
		p.openInventory(gui);
	}
	
	private void fix(ItemStack... items)
	{
		for(ItemStack is : items)
		{
			short meta = is.getDurability();
			if(meta == 32767 || meta == -1) is.setDurability((short) 0);
		}
	}
	
	private void shaped(Player p, ShapedRecipe sr)
	{
		if(p == null || sr == null) return;
		Map<Character, ItemStack> map = sr.getIngredientMap();
		String[] shape = sr.getShape();
		p.closeInventory();
		
		InventoryView iv = p.openWorkbench(null, true);
		in.add(p);
		for(int j = 0; j < shape.length; j++)
		{
			for(int k = 0; k < shape[j].length(); k++)
			{
				char c = shape[j].toCharArray()[k];
				ItemStack item = map.get(c);
				if(item != null)
				{
					short meta = item.getDurability();
					if(meta == Short.MAX_VALUE || meta == -1) item.setDurability((short) 0);
					Inventory top = iv.getTopInventory();
					top.setItem(j * 3 + k + 1, item);
				}
			}
		}
	}
	
	private void shapeless(Player p, ShapelessRecipe sr)
	{
		if(p == null || sr == null) return;
		List<ItemStack> list = sr.getIngredientList();
		p.closeInventory();
		
		InventoryView iv = p.openWorkbench(null, true);
		in.add(p);
		for(int i = 0; i < list.size(); i++)
		{
			ItemStack item = list.get(i);
			short meta = item.getDurability();
			if(meta == Short.MAX_VALUE || meta == -1) item.setDurability((short) 0);
			iv.setItem(i + 1, item);
		}
	}
}