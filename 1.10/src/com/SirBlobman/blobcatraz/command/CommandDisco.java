package com.SirBlobman.blobcatraz.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.Util;
import com.google.common.collect.Maps;

public class CommandDisco implements CommandExecutor,Runnable,Listener
{
	Random r = new Random();
	static HashMap<Player, String> disco = Maps.newHashMap();
	ItemStack air = new ItemStack(Material.AIR);
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
		Player p = (Player) cs;
		List<String> validTypes = Arrays.asList("off", "glass", "armor", "wool", "clay", "banner", "carpet");
		
		if(label.equalsIgnoreCase("disco"))
		{
			if(args.length < 1) {p.sendMessage(Util.notEnoughArguments); return false;}
			String type = args[0].toLowerCase();
			if(validTypes.contains(type) && p.hasPermission("disco." + type))
			{
				if(type.equals("wool"))
				{
					disco.put(p, "wool");
					p.sendMessage(Util.blobcatraz + "You now have disco wool");
				}
				if(type.equals("clay"))
				{
					disco.put(p, "clay");
					p.sendMessage(Util.blobcatraz + "You now have disco clay");
				}
				if(type.equals("armor"))
				{
					disco.put(p, "armor");
					p.sendMessage(Util.blobcatraz + "You now have disco armor");
				}
				if(type.equals("glass"))
				{
					disco.put(p, "glass");
					p.sendMessage(Util.blobcatraz + "You now have disco glass");
				}
				if(type.equals("carpet"))
				{
					disco.put(p, "carpet");
					p.sendMessage(Util.blobcatraz + "You now have disco carpet");
				}
				if(type.equals("banner"))
				{
					disco.put(p, "banner");
					p.sendMessage(Util.blobcatraz + "You now have disco banner");
				}
				if(type.equals("off"))
				{
					disco.remove(p);
					p.getInventory().setArmorContents(new ItemStack[] {air, air, air, air});
					p.sendMessage(Util.blobcatraz + "You don't have disco anymore");
				}
				return true;
			}
			else
			{
				p.sendMessage(Util.blobcatraz + "Invalid disco type or no permission");
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void run()
	{
		for(Player p : disco.keySet())
		{
			String type = disco.get(p);
			if(type.equalsIgnoreCase("glass"))
			{
				PlayerInventory pi = p.getInventory();
				ItemStack helmet = Util.colorRandom16(new ItemStack(Material.STAINED_GLASS));
				ItemStack[] newArmor = new ItemStack[] {air, air, air, helmet};
				pi.setArmorContents(newArmor);
			}
			if(type.equalsIgnoreCase("carpet"))
			{
				PlayerInventory pi = p.getInventory();
				ItemStack helmet = Util.colorRandom16(new ItemStack(Material.CARPET));
				ItemStack[] newArmor = new ItemStack[] {air, air, air, helmet};
				pi.setArmorContents(newArmor);
			}
			if(type.equalsIgnoreCase("banner"))
			{
				PlayerInventory pi = p.getInventory();
				ItemStack helmet = Util.colorRandom16(new ItemStack(Material.BANNER));
				ItemStack[] newArmor = new ItemStack[] {air, air, air, helmet};
				pi.setArmorContents(newArmor);
			}
			if(type.equalsIgnoreCase("wool"))
			{
				PlayerInventory pi = p.getInventory();
				ItemStack helmet = Util.colorRandom16(new ItemStack(Material.WOOL));
				ItemStack[] newArmor = new ItemStack[] {air, air, air, helmet};
				pi.setArmorContents(newArmor);
			}
			if(type.equalsIgnoreCase("clay"))
			{
				PlayerInventory pi = p.getInventory();
				ItemStack helmet = Util.colorRandom16(new ItemStack(Material.STAINED_CLAY));
				ItemStack[] newArmor = new ItemStack[] {air, air, air, helmet};
				pi.setArmorContents(newArmor);
			}
			if(type.equalsIgnoreCase("armor"))
			{
				PlayerInventory pi = p.getInventory();
				pi.setArmorContents(Util.getLeatherArmorRandom());
			}
		}
	}
	
	@EventHandler
	public void onPlayerMoveArmor(InventoryClickEvent e)
	{
		if(e.getWhoClicked() instanceof Player)
		{
			Player p = (Player) e.getWhoClicked();
			if(disco.containsKey(p))
			{
				ItemStack is = e.getCurrentItem();
				if(is == null) return;
				Material m = is.getType();
				if(m == null) return;
				List<Material> discoTypes = Arrays.asList(Material.STAINED_GLASS, Material.STAINED_GLASS_PANE, Material.STAINED_CLAY, Material.CARPET, Material.BANNER, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.WOOL);
				if(discoTypes.contains(m))
				{
					if(e.getSlotType() == SlotType.ARMOR) e.setCancelled(true);
				}
			}
		}
	}
}