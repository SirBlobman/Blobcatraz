package com.SirBlobman.blobcatraz.command;

import java.util.HashMap;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.google.common.collect.Maps;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandDisco implements CommandExecutor,Listener,Runnable
{
	public enum DiscoType
	{
		GLASS(Material.STAINED_GLASS),
		PANE(Material.STAINED_GLASS_PANE),
		CARPET(Material.CARPET),
		BANNER(Material.BANNER),
		WOOL(Material.WOOL),
		CLAY(Material.STAINED_CLAY),
		ARMOR(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS);
		
		private Material[] material;
		
		DiscoType(Material... mat) {this.material = mat;}
		public Material[] getTypes() {return material;}
		public static DiscoType match(String s)
		{
			try
			{
				s = s.toUpperCase();
				return valueOf(s);
			} catch(Exception ex)
			{
				Util.print("Invalid Disco Type: " + s);
				Util.print(ex.getCause() + ": " + ex.getMessage());
				return null;
			}
		}
	}
	
	static HashMap<Player, DiscoType> disco = Maps.newHashMap();
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
	{
		if(cs instanceof Player)
		{
			Player p = (Player) cs;
			String command = c.getName().toLowerCase();
			if(command.equals("disco"))
			{
				if(args.length >= 1)
				{
					String type = args[0].toUpperCase();
					if(type.equals("OFF")) 
					{
						PlayerInventory pi = p.getInventory();
						ItemStack air = new ItemStack(Material.AIR);
						pi.setArmorContents(new ItemStack[] {air, air, air, air});
						p.sendMessage(Util.blobcatraz + "Your disco is now disabled");
						return true;
					}
					DiscoType discot = DiscoType.match(type);;
					String discom = "You now have disco ";
					if(discot != null)
					{
						disco.put(p, discot);
						switch(discot)
						{
						case ARMOR:
							p.sendMessage(Util.blobcatraz + discom + "armor!");
							break;
						case BANNER:
							p.sendMessage(Util.blobcatraz + discom + "banner!");
							break;
						case CARPET:
							p.sendMessage(Util.blobcatraz + discom + "carpet!");
							break;
						case CLAY:
							p.sendMessage(Util.blobcatraz + discom + "clay!");
							break;
						case GLASS:
							p.sendMessage(Util.blobcatraz + discom + "glass!");
							break;
						case PANE:
							p.sendMessage(Util.blobcatraz + discom + "glass pane!");
							break;
						case WOOL:
							p.sendMessage(Util.blobcatraz + discom + "wool!");
							break;
						default:
							break;
						}
						return true;
					}
					p.sendMessage(Util.IA);
					return false;
				}
				p.sendMessage(Util.NEA);
				return false;
			}
		}
		cs.sendMessage(Util.csNotPlayer);
		return true;
	}
	
	@Override
	public void run()
	{
		for(Player p : disco.keySet())
		{
			DiscoType type = disco.get(p);
			PlayerInventory pi = p.getInventory();
			ItemStack air = new ItemStack(Material.AIR);
			switch(type)
			{
			case ARMOR:
				ItemStack[] armor = ItemUtil.getLeatherArmor256(true);
				pi.setArmorContents(armor);
				break;
			case BANNER:
				ItemStack banner = new ItemStack(Material.BANNER);
				ItemStack helmet = ItemUtil.color16(banner, true);
				ItemStack[] armor1 = new ItemStack[] {air, air, air, helmet};
				pi.setArmorContents(armor1);
				break;
			case CARPET:
				ItemStack carpet = new ItemStack(Material.CARPET);
				ItemStack helmet1 = ItemUtil.color16(carpet, true);
				ItemStack[] armor2 = new ItemStack[] {air, air, air, helmet1};
				pi.setArmorContents(armor2);
				break;
			case CLAY:
				ItemStack clay = new ItemStack(Material.STAINED_CLAY);
				ItemStack helmet2 = ItemUtil.color16(clay, true);
				ItemStack[] armor3 = new ItemStack[] {air, air, air, helmet2};
				pi.setArmorContents(armor3);
				break;
			case GLASS:
				ItemStack glass = new ItemStack(Material.STAINED_GLASS);
				ItemStack helmet3 = ItemUtil.color16(glass, true);
				ItemStack[] armor4 = new ItemStack[] {air, air, air, helmet3};
				pi.setArmorContents(armor4);
				break;
			case PANE:
				ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE);
				ItemStack helmet4 = ItemUtil.color16(pane, true);
				ItemStack[] armor5 = new ItemStack[] {air, air, air, helmet4};
				pi.setArmorContents(armor5);
				break;
			case WOOL:
				ItemStack wool = new ItemStack(Material.WOOL);
				ItemStack helmet5 = ItemUtil.color16(wool, true);
				ItemStack[] armor6 = new ItemStack[] {air, air, air, helmet5};
				pi.setArmorContents(armor6);
				break;
			default:
				break;
			}
		}
	}
	
	@EventHandler
	public void click(InventoryClickEvent e)
	{
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player)
		{
			SlotType type = e.getSlotType();
			SlotType armor = SlotType.ARMOR;
			ItemStack is = e.getCurrentItem();
			String name = ItemUtil.getName(is);
			String display = Util.message("item.disco.name");
			if(name.equals(display) && type == armor) e.setCancelled(true);
		}
	}
}