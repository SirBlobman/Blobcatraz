package com.SirBlobman.blobcatraz.command;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.SirBlobman.blobcatraz.Util;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class Heads implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args) 
	{
		if(!(cs instanceof Player))
		{
			cs.sendMessage(Util.notAPlayer);
		}
		
		Player p = (Player) cs;
		String sname = p.getName();
		
		if(label.equalsIgnoreCase("head") || label.equalsIgnoreCase("heads"))
		{
			if(args.length == 0)
			{
				p.sendMessage(Util.notEnoughArguments);
				return false;
			}
			if(args.length == 1)
			{
				List<String> styles = Arrays.asList("Wood", "Stone", "Cobblestone", "Social", "Player");
				Collections.sort(styles);
				String sstyles = String.join("§r, §6", styles);
				
				p.sendMessage(Util.blobcatraz + "Available head styles:");
				p.sendMessage("§6" + sstyles);
				return false;
			}
			if(args.length == 2 || args.length == 3)
			{
				if(args.length == 3)
				{
					sname = args[2];
					if(Bukkit.getPlayer(sname) == null)
					{
						p.sendMessage(Util.blobcatraz + "§1" + sname + " §ris not a Player");
						return true;
					}
				}
				
				String style = args[0];
				if(style.equalsIgnoreCase("wood"))
				{
					String letter = args[1];
					if(letter.equalsIgnoreCase("+"))
					{
						ItemStack is = getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkZDIwYmU5MzUyMDk0OWU2Y2U3ODlkYzRmNDNlZmFlYjI4YzcxN2VlNmJmY2JiZTAyNzgwMTQyZjcxNiJ9fX0=");
						Util.giveItem(p, is);
					}
				}
			}
		}
		
		return false;
	}
	
	public static ItemStack getSkull(String skinURL) 
	{
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);

		if(skinURL.isEmpty()) return head;

		ItemMeta headMeta = head.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", skinURL).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;

		try 
		{
			profileField = headMeta.getClass().getDeclaredField("profile");
		} 
		catch (NoSuchFieldException | SecurityException e) 
		{
			e.printStackTrace();
		}

		profileField.setAccessible(true);

		try 
		{
			profileField.set(headMeta, profile);
		} 
		catch (IllegalArgumentException | IllegalAccessException e) 
		{
			e.printStackTrace();
		}

		head.setItemMeta(headMeta);
		return head;
	}
}