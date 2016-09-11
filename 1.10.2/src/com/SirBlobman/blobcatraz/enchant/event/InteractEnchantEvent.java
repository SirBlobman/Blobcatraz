package com.SirBlobman.blobcatraz.enchant.event;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class InteractEnchantEvent extends EnchantEvent
{
	private Player player;
	private Entity entity;
	
	public InteractEnchantEvent(List<String> lore, Entity e, Player p)
	{
		super(lore);
		this.player = p;
		this.entity = e;
	}
	
	public Player getPlayer() {return player;}
	public Entity getEntity() {return entity;}
}