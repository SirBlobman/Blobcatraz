package com.SirBlobman.blobcatraz.utility;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_11_R1.PlayerConnection;

public class PlayerUtil extends Util
{
	public static OfflinePlayer owner()
	{
		String name = CB.getString("owner");
		if(name == null) return null;
		UUID uuid = UUID.fromString(name);
		if(uuid == null) return null;
		return Bukkit.getOfflinePlayer(uuid);
	}
	
	public static boolean hasPermission(CommandSender cs, String permission)
	{
		ConsoleCommandSender ccs = S.getConsoleSender();
		if(cs != ccs)
		{
			boolean b1 = cs.isOp();
			boolean b2 = cs.hasPermission(permission);
			if(b1 || b2) return true;
			noPermission(cs, permission);
			return false;
		}
		return true;
	}
	
	public static void noPermission(CommandSender cs, String permission)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String title = option("command.no permission1");
			String suble = option("command.no permission2", permission);
			title(p, title, suble);
			return;
		}
		cs.sendMessage(prefix + option("command.no permission", permission));
		return;
	}
	
	public static void title(Player p, String title, String subtitle)
	{
		String t = json(title);
		String s = json(subtitle);
		IChatBaseComponent ititle = ChatSerializer.a(t);
		IChatBaseComponent isuble = ChatSerializer.a(s);
		PacketPlayOutTitle ppot1 = new PacketPlayOutTitle(EnumTitleAction.TITLE, ititle);
		PacketPlayOutTitle ppot2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, isuble);
		PlayerConnection pc = connection(p);
		pc.sendPacket(ppot1);
		pc.sendPacket(ppot2);
	}
	
	public static void action1_11(Player p, String msg)
	{
		String s = json(msg);
		IChatBaseComponent icbc = ChatSerializer.a(s);
		byte action = 2;
		PacketPlayOutChat ppoc = new PacketPlayOutChat(icbc, action);
		PlayerConnection pc = connection(p);
		pc.sendPacket(ppoc);
	}
	
	public static PlayerConnection connection(Player p)
	{
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		PlayerConnection pc = ep.playerConnection;
		return pc;
	}
	
	public static void sonic(Player p)
	{
		if(p == null) return;
		Location l = p.getLocation();
		String s = "sonic-screwdriver";
		float v = 100.0F;
		float pt = 1.0F;
		p.playSound(l, s, v, pt);
	}
	
	public static void ping(Player p)
	{
		if(p == null) return;
		Location l = p.getLocation();
		Sound s = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
		float v = 1000.0F;
		float pt = 1.0F;
		p.playSound(l, s, v, pt);
	}
	
	public static void heal(Player p)
	{
		p.setHealth(p.getMaxHealth());
		for(PotionEffect pe : p.getActivePotionEffects())
		{
			PotionEffectType pet = pe.getType();
			p.removePotionEffect(pet);
		}
		feed(p);
	}
	
	public static void feed(Player p)
	{
		p.setFoodLevel(20);
		p.setSaturation(20);
	}
	
	public static Block looking(Player p)
	{
		if(p == null) return null;
		Set<Material> n = null;
		int r = 200;
		Block b = p.getTargetBlock(n, r);
		return b;
	}
	
	public static Location lookingLocation(Player p)
	{
		if(p == null) return null;
		Block b = looking(p);
		Location l = b.getLocation();
		return l;
	}
	
	public static void closeInventories()
	{
		Collection<? extends Player> online = Bukkit.getOnlinePlayers();
		for(Player p : online) p.closeInventory();
	}
	
	public static void clear(Player p)
	{
		PlayerInventory pi = p.getInventory();
		ItemStack[] armor = pi.getArmorContents().clone();
		ItemStack off = pi.getItemInOffHand().clone();
		pi.clear();
		pi.setArmorContents(armor);
		pi.setItemInOffHand(off);
		p.sendMessage(prefix + option("command.clear inventory.success"));
		p.sendMessage(prefix + option("command.clear inventory.saved armor"));
	}
	
	public static void clearArmor(Player p)
	{
		PlayerInventory pi = p.getInventory();
		ItemStack[] armor = new ItemStack[] {AIR, AIR, AIR, AIR};
		pi.setArmorContents(armor);
		pi.setItemInOffHand(AIR);
		p.sendMessage(prefix + option("command.clear inventory.armor"));
	}
	
	public static void clearEnderChest(Player p)
	{
		Inventory chest = p.getEnderChest();
		chest.clear();
		p.sendMessage(prefix + option("command.clear inventory.ender chest"));
	}
}