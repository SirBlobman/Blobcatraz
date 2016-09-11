package com.SirBlobman.blobcatraz.enchant.event;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class InteractEntityEnchantEvent extends EnchantEvent 
{
	private Player player;
	private Entity entity;
	
	public InteractEntityEnchantEvent(List<String> lore, Entity e, Player p)
	{
		super(lore);
		player = p;
		entity = e;
	}
	
	public Entity getEntity()
	{
		return entity;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}