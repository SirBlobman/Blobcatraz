package com.SirBlobman.blobcatraz.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class ConfigLanguage
{
	private static File file = new File(Blobcatraz.folder, "messages.yml");
	private static YamlConfiguration messages = YamlConfiguration.loadConfiguration(file);
	
	public static FileConfiguration load()
	{
		if(!file.exists()) save();
		try{messages.load(file);} catch(Exception ex)
		{
			System.out.println("Error creating " + file + ": " + ex.getCause());
		}
		return messages;
	}
	
	public static void save()
	{
		if(!file.exists())
		{
			try
			{
				System.out.println(file + " doesn't exists! Attepting to create!");
				file.createNewFile();
				writeDefaults();
			} catch(Exception ex)
			{
				System.out.println("Failed to create " + file + ": " + ex.getCause());
				return;
			}
		}
		try{messages.save(file);} catch(Exception ex)
		{
			System.out.println("Failed to save " + file + ": " + ex.getCause());
		}
	}
	
	public static void reload()
	{
		load();
		save();
	}
	
	public static void writeDefaults()
	{
		set("blobcatraz.prefix", "&8&l{&b&lBlobcatraz&8&l} &r");
		set("blobcatraz.enabled", "&2&lEnabled");
		set("blobcatraz.disabled", "&4&lDisabled");
		set("blobcatraz.motd", "Default MOTD");
		
		set("command.notEnoughArguments", "&4Not enough arguments!");
		set("command.tooManyArgyments", "&4Too many arguments!");
		set("command.invalidArguments", "&4Invalid Arguments!");
		set("command.noPermission", "&4You don't have permission: %s");
		set("command.error.nonPlayer", "&4You must be a Player to run this command!");
		set("command.error.nonLiving", "&4You must be a LivingEntity to run this command!");
		set("command.error.worldNotEnabled", "&4You can't do that in this world");
		
		set("command.msg.format.to", "&7[&6me &3>>&r {to}&7] &r{message}");
		set("command.msg.format.from", "&7[&r{from} &3>>&r &6me&7] &r{message}");
		
		set("command.enchant.success", "Successfully enchanted your &6%s&r with &7%s&r!");
		set("command.enchant.un", "Successfully removed &7%s&r from your &6%s&r!");
		set("command.enchant.failure", "You can't enchant AIR");
		
		set("command.heal.success", "You have been healed!");
		set("command.heal.success.other", "You healed &5%s&r!");
		
		set("command.feed.success", "You are no longer hungry");
		set("command.feed.success.other", "&5%s&r is no longer hungry!");
		
		set("command.item.invalidMaterial", "Invalid material name: &b%s");
		set("command.item.invalidAmount", "Invalid item amount: &b%s");
		set("command.item.invalidMeta", "Invalid item data: &b%s");
		
		set("command.rename.success", "Renamed your &6%s&r to &f%s&r");
		set("command.setlore.success", "Set the lore of your &6%s&r to &f%s&r");
		set("command.addlore.success", "Set the lore of your &6%s&r to &f%s&r");
		set("command.removelore.success", "Removed line &9%s&r from your &6%s&r");
		set("command.clearlore.success", "Cleared the lore of your &6%s&r");
		set("command.repair.success", "Your &6%s&r was repaired");
		set("command.resetitem.success", "Your &6%s&r is now reset!");
		
		set("player.join", "&8&l[&a&l+&8&l]&e&l {USERNAME}&8&l [&a&l+&8&l]");
		set("player.quit", "&8&l[&4&l-&8&l]&e&l {USERNAME}&8&l [&4&l-&8&l]");
		set("player.combatLogQuit", "%s &rleft during combat!");
		set("player.banned", "You have been banned: /n&r");
		set("player.wasTeleported", "You have been teleported to &5%s&r, &5%s&r, &5%s&r");
		set("player.inventory.full", "&cYour inventory is too full to recieve items!");
		set("player.inventory.recieveItem", "You have been given &3%s&r of &6%s&r");
		set("player.attacker", "You are now in combat for attacking &5%s&r.");
		set("player.attacked", "&5%s&r attacked you! You are now in combat.");
		set("player.outOfCombat", "You are no longer in combat!");
		set("player.afk", "");
		set("player.afkReason", "");
		set("player.notAFK", "&6&l* &7%s&6 is no longer AFK");
		set("player.isFrozen", "You are &bfrozen&r! That means you can't move");
		
		set("giant.spawn", "A giant has been spawned: /n&o%s:%s, %s, %s/nGo kill it for a prize");
		set("giant.death", "The giant has been slain!");
		
		set("slime.isInvincible", "Stop hitting the slime! it's obviously invincible");
		
		set("inventory.clear.successNoArmor", "Your inventory has been cleared, but we saved your armor and shield.");
		set("inventory.clear.successItemType", "Deleted all of &6%s&r from your inventory.");
		set("inventory.clear.successArmor", "Your armor has been removed.");
		set("inventory.clear.successEnderchest", "Your enderchest has been cleared!");
		
		set("title.noPermission", "&4&lNo Permission!");
		set("subtitle.noPermission", "&b%s");
		set("title.inventoryFull", "&b&l&m--&8&l&m[-<&f&lInventory Full&8&l&m>-]&b&l&m--");
		set("subtitle.inventoryFull", "&6&l>> >> >>&f&l Sell Your Items&6&l << << <<");
		
		set("gui.tokenshop", "&f&l&m---&8&l&m[--&9&l Token Shop &8&l&m--]&f&l&m---");
		set("gui.sellall", "Sell your items");
		set("gui.randomtp", "Select the TP distance...");
		
		set("scoreboard.default.title", "&aBlobcatraz");
		set("scoreboard.combat", "&4&ki&3&lCombat Log&4&ki&r");
		
		set("shop.invalidSign", "Invalid Shop Sign!");
		set("shop.invalidItem", "Invalid Item: &e%s");
		set("shop.notEnoughMoney", "You don't have enough money to buy that!");
		set("shop.missingItem", "You don't have enough of &6%s&r to sell");
		set("shop.successfulBuy", "You bought &e%s&r of &6%s&r for &2%s");
		set("shop.sign.sellall", "&5[&4Sell All&5]&r");
		
		set("enchant.cure.success", "&aCure the Villager!");
		set("enchant.cure.failure", "&4Failed to cure the Zombie!");
		set("enchant.explosion.cooldown.wait", "You must wait before using Explosion again!");
		set("enchant.explosion.cooldown.end", "You can now use Explosion");
		
		set("item.banknote.name", "&fBlobcatraz Bank Note");
		set("item.disco.name", "&1D&2I&3S&4C&5O");
		
		set("scoreboard.combatlog", "&1&ki&3Combat Log&1&ki&r");
		save();
	}
	
	public static void set(String key, String message)
	{
		if(messages.get(key) != null) return;
		messages.set(key, message);
	}
	
	public static void forceMessage(String key, String msg)
	{
		messages.set(key, msg);
		save();
	}
	
	public static String getMessage(String key, Object... format)
	{
		String msg = messages.getString(key);
		if(msg == null) return key;
		return (String.format(Util.format(msg), format));
	}
}