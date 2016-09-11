package com.SirBlobman.blobcatraz.enchant.event;

import java.util.List;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnchantEvent extends Event implements Cancellable
{
	private final static HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private List<String> lore;
	
	public EnchantEvent(List<String> lore)
	{
		this.lore = lore;
	}
	
	public List<String> getLore()
	{
		return lore;
	}
	
	@Override
	public boolean isCancelled() 
	{
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel)
	{
		cancelled = cancel;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
}