package com.SirBlobman.blobcatraz.enchant.event;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class RightClickEnchantEvent extends EnchantEvent
{
	Player player;
	Block block;
	
	public RightClickEnchantEvent(List<String> lore, Player p, Block b)
	{
		super(lore);
		player = p;
		block = b;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public Block getBlock()
	{
		return block;
	}
}
