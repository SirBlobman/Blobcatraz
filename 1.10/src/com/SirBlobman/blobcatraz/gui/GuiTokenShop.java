package com.SirBlobman.blobcatraz.gui;

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

import com.SirBlobman.blobcatraz.config.ConfigLanguage;

public class GuiTokenShop implements Listener
{
	private final String TITLE = ConfigLanguage.getMessage("title.tokenShop");
	
	private final ItemStack MAIN_FILLER = TokenShopItems.mainFiller();
	private final ItemStack MAIN_HELMET = TokenShopItems.mainHelmet();
	private final ItemStack MAIN_CHESTPLATE = TokenShopItems.mainChestplate();
	private final ItemStack MAIN_LEGGINGS = TokenShopItems.mainLeggings();
	private final ItemStack MAIN_BOOTS = TokenShopItems.mainBoots();
	private final ItemStack MAIN_WEAPONS = TokenShopItems.mainWeapons();
	private final ItemStack MAIN_TOOLS = TokenShopItems.mainTools();
	private final ItemStack BACK = TokenShopItems.back();
	
	private final ItemStack TOOL_FILLER = TokenShopItems.toolsFiller();
	private final ItemStack UPGRADE_STONE = TokenShopItems.upgrade("§7§lStone", Material.STONE_PICKAXE);
	private final ItemStack UPGRADE_GOLD = TokenShopItems.upgrade("§6§lGold", Material.GOLD_PICKAXE);
	private final ItemStack UPGRADE_IRON = TokenShopItems.upgrade("§f§lIron", Material.IRON_PICKAXE);
	private final ItemStack UPGRADE_DIAMOND = TokenShopItems.upgrade("§3§lDiamond", Material.DIAMOND_PICKAXE);
	private final ItemStack UPGRADE_EFFICIENCY = TokenShopItems.upgradeEnchant("Efficiency", Enchantment.DIG_SPEED, Material.OBSIDIAN, "§b§l§oMine Blocks Faster");
	private final ItemStack UPGRADE_SHARPNESS = TokenShopItems.upgradeEnchant("Sharpness", Enchantment.DAMAGE_ALL, Material.SMOOTH_BRICK, "§b§l§oDo More Damage");
	private final ItemStack UPGRADE_FORTUNE = TokenShopItems.upgradeEnchant("Fortune", Enchantment.LOOT_BONUS_BLOCKS, Material.EMERALD_BLOCK, "§b§l§oGet Blocks Faster");
	private final ItemStack UPGRADE_SILK_TOUCH = TokenShopItems.upgradeEnchant("Silk Touch", Enchantment.SILK_TOUCH, Material.IRON_BLOCK, "§b§l§oGet The Ore Instead");
	
	private final ItemStack ARMOR_FILLER = TokenShopItems.armorFiller();
	private final ItemStack HELMET_PP = TokenShopItems.upgradeHelmet("Projectile Protection", Enchantment.PROTECTION_PROJECTILE);
	private final ItemStack HELMET_FP = TokenShopItems.upgradeHelmet("Fire Protection", Enchantment.PROTECTION_FIRE);
	private final ItemStack HELMET_P = TokenShopItems.upgradeHelmet("Protection", Enchantment.PROTECTION_ENVIRONMENTAL);
	private final ItemStack HELMET_WB = TokenShopItems.upgradeHelmet("Water Breathing", Enchantment.OXYGEN);
	private final ItemStack HELMET_T = TokenShopItems.upgradeHelmet("Thorns", Enchantment.THORNS);
	private final ItemStack HELMET_BP = TokenShopItems.upgradeHelmet("Blast Protection", Enchantment.PROTECTION_EXPLOSIONS);
	private final ItemStack HELMET_KB = TokenShopItems.upgradeHelmet("Knockback", Enchantment.KNOCKBACK);
	
	
	public void open(Player p, Inventory i)
	{
		p.openInventory(i);
	}
	
	public Inventory mainGUI()
	{
		ItemStack[] items = new ItemStack[]
		{
			MAIN_HELMET, MAIN_FILLER, MAIN_FILLER, MAIN_CHESTPLATE, MAIN_FILLER, MAIN_LEGGINGS, MAIN_FILLER, MAIN_FILLER, MAIN_BOOTS,//1st Row
			MAIN_FILLER, MAIN_FILLER, MAIN_FILLER, MAIN_FILLER, MAIN_FILLER, MAIN_FILLER, MAIN_FILLER, MAIN_FILLER, MAIN_FILLER, //2nd Row
			MAIN_FILLER, MAIN_WEAPONS, MAIN_FILLER, MAIN_FILLER, MAIN_FILLER, MAIN_FILLER, MAIN_FILLER, MAIN_TOOLS, MAIN_FILLER//3rd Row
		};
		Inventory shop = Bukkit.createInventory(null, items.length, TITLE);
		shop.setContents(items);
		return shop;
	}
	
	public Inventory tools()
	{
		ItemStack[] items = new ItemStack[]
		{
			UPGRADE_STONE, TOOL_FILLER, TOOL_FILLER, UPGRADE_GOLD, TOOL_FILLER, UPGRADE_IRON, TOOL_FILLER, TOOL_FILLER, UPGRADE_DIAMOND,//1st Row
			TOOL_FILLER, TOOL_FILLER, TOOL_FILLER, TOOL_FILLER, UPGRADE_EFFICIENCY, TOOL_FILLER, TOOL_FILLER, TOOL_FILLER, TOOL_FILLER, //2nd Row
			TOOL_FILLER, TOOL_FILLER, UPGRADE_SHARPNESS, TOOL_FILLER, UPGRADE_FORTUNE, TOOL_FILLER, UPGRADE_SILK_TOUCH, TOOL_FILLER, BACK//3rd Row
		};
		Inventory shop = Bukkit.createInventory(null, items.length, TITLE);
		shop.setContents(items);
		return shop;
	}
	
	public Inventory weapons()
	{
		ItemStack[] items = new ItemStack[]
		{
				
		};
		Inventory shop = Bukkit.createInventory(null, items.length, TITLE);
		shop.setContents(items);
		return shop;
	}
	
	public Inventory helmet()
	{
		ItemStack[] items = new ItemStack[]
		{
			ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER, BACK, ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER, //1st Row
			ARMOR_FILLER, HELMET_PP, HELMET_FP, HELMET_P, HELMET_WB, HELMET_T, HELMET_BP, HELMET_KB, ARMOR_FILLER, //2nd Row
			ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER, ARMOR_FILLER //3rd Row
		};
		Inventory shop = Bukkit.createInventory(null, items.length, TITLE);
		shop.setContents(items);
		return shop;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
		Inventory i = e.getInventory();
		ItemStack is = e.getCurrentItem();
		HumanEntity he = e.getWhoClicked();
		if(!(he instanceof Player)) return;
		Player p = (Player) he;
		if(i.getName().equalsIgnoreCase(TITLE))
		{
			e.setCancelled(true);
			if(is.equals(MAIN_TOOLS)) open(p, tools());
			if(is.equals(MAIN_WEAPONS)) open(p, weapons());
			if(is.equals(MAIN_HELMET)) open(p, helmet());
			if(is.equals(BACK)) open(p, mainGUI());
		}
	}
}