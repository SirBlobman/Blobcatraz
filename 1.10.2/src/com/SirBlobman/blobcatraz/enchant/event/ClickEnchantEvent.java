package com.SirBlobman.blobcatraz.enchant.event;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ClickEnchantEvent extends EnchantEvent
{
	public enum ClickType
	{
		RIGHT, LEFT;
	}
	
	private Player player;
	private Block block;
	private ClickType type;
	
	public ClickEnchantEvent(List<String> lore, Player p, Block b, ClickType type)
	{
		super(lore);
		this.player = p;
		this.block = b;
		this.type = type;
	}
	
	public Player getPlayer() {return player;}
	public Block getBlock() {return block;}
	public ClickType getClick() {return type;}
}