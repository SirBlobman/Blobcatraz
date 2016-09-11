package com.SirBlobman.blobcatraz.gui;

import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiTokenShop implements Listener
{
	private final String TITLE = Util.message("gui.tokenshop");
	
	private final ItemStack FILLER = TokenShopItems.filler();
	private final ItemStack BACK = TokenShopItems.back();
	
	//Main
	private final ItemStack MAIN_HELMET = TokenShopItems.mainArmor(Material.DIAMOND_HELMET);
	private final ItemStack MAIN_CHESTPLATE = TokenShopItems.mainArmor(Material.DIAMOND_CHESTPLATE);
	private final ItemStack MAIN_LEGGINGS = TokenShopItems.mainArmor(Material.DIAMOND_LEGGINGS);
	private final ItemStack MAIN_BOOTS = TokenShopItems.mainArmor(Material.DIAMOND_BOOTS);
	private final ItemStack MAIN_TOOLS = TokenShopItems.mainTools();
	private final ItemStack MAIN_WEAPONS = TokenShopItems.mainWeapons();
	
	//Tools
	private final ItemStack GOLD = TokenShopItems.upgrade("&6&lGold", Material.GOLD_PICKAXE);
	private final ItemStack STONE = TokenShopItems.upgrade("&7&lStone", Material.STONE_PICKAXE);
	private final ItemStack IRON = TokenShopItems.upgrade("&f&lIron", Material.IRON_PICKAXE);
	private final ItemStack DIAMOND = TokenShopItems.upgrade("&3&lDiamond", Material.DIAMOND_PICKAXE);

	private final ItemStack EFFICIENCY = TokenShopItems.upgrade("Efficiency", Enchantment.DIG_SPEED, Material.OBSIDIAN, "§b§l§oMine blocks faster...");
	private final ItemStack SHARPNESS = TokenShopItems.upgrade("Sharpness", Enchantment.DIG_SPEED, Material.SMOOTH_BRICK, "§b§l§oDo more damage...");
	private final ItemStack FORTUNE = TokenShopItems.upgrade("Fortune", Enchantment.DIG_SPEED, Material.EMERALD_BLOCK, "§b§l§oGet items faster...");
	private final ItemStack SILK_TOUCH = TokenShopItems.upgrade("Silk Touch", Enchantment.DIG_SPEED, Material.IRON_ORE, "§b§l§oGet the ore instead...");
	//Weapons
	//Helmet
	private final ItemStack HELMET_PP = TokenShopItems.helmetUpgrade("Projectile Protection", Enchantment.PROTECTION_PROJECTILE);
	private final ItemStack HELMET_BP = TokenShopItems.helmetUpgrade("Blast Protection", Enchantment.PROTECTION_EXPLOSIONS);
	private final ItemStack HELMET_FP = TokenShopItems.helmetUpgrade("Fire Protection", Enchantment.PROTECTION_FIRE);
	private final ItemStack HELMET_P = TokenShopItems.helmetUpgrade("Protection", Enchantment.PROTECTION_ENVIRONMENTAL);
	private final ItemStack HELMET_WB = TokenShopItems.helmetUpgrade("Water Breathing", Enchantment.OXYGEN);
	private final ItemStack HELMET_T = TokenShopItems.helmetUpgrade("Thorns", Enchantment.THORNS);
	private final ItemStack HELMET_KB = TokenShopItems.helmetUpgrade("Knockback", Enchantment.KNOCKBACK);
	//Chestplate
	//Leggings
	//Boots
	
	public Inventory main()
	{
		ItemStack[] items = new ItemStack[]
		{
			MAIN_HELMET, FILLER, FILLER, MAIN_CHESTPLATE, FILLER, MAIN_LEGGINGS, FILLER, FILLER, MAIN_BOOTS,//1st Row
			FILLER, FILLER, FILLER, FILLER, FILLER, FILLER, FILLER, FILLER, FILLER,//2nd Row
			FILLER, MAIN_WEAPONS, FILLER, FILLER, FILLER, FILLER, FILLER, MAIN_TOOLS, FILLER//3rd Row
		};
		
		Inventory shop = Bukkit.createInventory(null, items.length, TITLE);
		shop.setContents(items);
		return shop;
	}
	
	public Inventory tools()
	{
		ItemStack[] items = new ItemStack[]
		{
			STONE, FILLER, FILLER, GOLD, FILLER, IRON, FILLER, FILLER, DIAMOND, //1st Row
			FILLER, FILLER, FILLER, FILLER, EFFICIENCY, FILLER, FILLER, FILLER, FILLER, //2nd Row
			FILLER, FILLER, SHARPNESS, FILLER, FORTUNE, FILLER, SILK_TOUCH, FILLER, BACK //3rd Row
		};
		
		Inventory shop = Bukkit.createInventory(null, items.length, TITLE);
		shop.setContents(items);
		return shop;
	}
	
	public Inventory weapons()
	{
		ItemStack[] items = new ItemStack[] 
		{
			FILLER, FILLER, FILLER, FILLER, BACK, FILLER, FILLER, FILLER, FILLER //1st Row
		};
		Inventory shop = Bukkit.createInventory(null, items.length, TITLE);
		shop.setContents(items);
		return shop;
	}
	
	public Inventory helmet()
	{
		ItemStack[] items = new ItemStack[] 
		{
			FILLER, FILLER, FILLER, FILLER, BACK, FILLER, FILLER, FILLER, FILLER, //1st Row	
			FILLER, HELMET_PP, HELMET_FP, HELMET_P, HELMET_WB, HELMET_T, HELMET_BP, HELMET_KB, FILLER, //2nd Row
			FILLER, FILLER, FILLER, FILLER, FILLER, FILLER, FILLER, FILLER, FILLER //3rd Row
		};
		
		Inventory shop = Bukkit.createInventory(null, items.length, TITLE);
		shop.setContents(items);
		return shop;
	}
	
	@EventHandler
	public void click(InventoryClickEvent e)
	{
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player)
		{
			Player p = (Player) he;
			Inventory i = e.getClickedInventory();
			String iname = i.getName();
			ItemStack is = e.getCurrentItem();
			if(iname.equalsIgnoreCase(TITLE))
			{
				e.setCancelled(true);
				if(is.equals(MAIN_TOOLS)) PlayerUtil.open(p, tools());
				if(is.equals(MAIN_WEAPONS)) PlayerUtil.open(p, weapons());
				if(is.equals(MAIN_HELMET)) PlayerUtil.open(p, helmet());
				if(is.equals(BACK)) PlayerUtil.open(p, main());
			}
		}
	}
}