package com.SirBlobman.blobcatraz.listeners;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LightningRod implements Listener 
{
	public static ItemStack lightning_rod = new ItemStack(Material.BLAZE_ROD);
	@EventHandler
	public void onPlayerInteractBlock(PlayerInteractEvent e) 
	{
		Player p = e.getPlayer();
		
		ItemMeta lrodmeta = lightning_rod.getItemMeta();
		lrodmeta.setDisplayName("§6§lLightning §rRod");
		lrodmeta.setLore(Arrays.asList("Creates lightning where you are looking", "Only works up to 200 blocks away"));
		lightning_rod.setItemMeta(lrodmeta);
		if (p.getInventory().getItemInMainHand().equals(lightning_rod) == true || p.getInventory().getItemInOffHand().equals(lightning_rod) == true) 
		{
			p.getWorld().strikeLightning(p.getTargetBlock((Set<Material>) null, 200).getLocation());
		}
	}
}
