package com.SirBlobman.blobcatraz.item;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LightningRod implements Listener 
{
	public static ItemStack lightningRod()
	{
		ItemStack lrod = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = lrod.getItemMeta();
		
		meta.setDisplayName("§6§lLightning §rRod");
		meta.setLore(Arrays.asList("Creates lightning where you are looking", "Only works up to 200 blocks away"));
		lrod.setItemMeta(meta);
		lrod.setAmount(1);
		
		return lrod;
	}
	
	@EventHandler
	public void onPlayerInteractBlock(PlayerInteractEvent e) 
	{
		Player p = e.getPlayer();
		ItemStack lrod = lightningRod();
		Location strike = p.getTargetBlock((Set<Material>) null, 200).getLocation();
		
		if (p.getInventory().getItemInMainHand().equals(lrod) || p.getInventory().getItemInOffHand().equals(lrod)) 
		{
			p.getWorld().strikeLightning(strike);
		}
	}
}
