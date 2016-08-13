package com.SirBlobman.blobcatraz;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class WorldGuardChecker 
{
	public static PluginManager pm = Bukkit.getServer().getPluginManager();
	
	public static boolean isWorldGuardEnabled()
	{
		Plugin worldGuard = pm.getPlugin("WorldGuard");
		if(worldGuard == null || !(worldGuard instanceof WorldGuardPlugin)) return false;
		return true;
	}
	
	public static WorldGuardPlugin getWorldGuard()
	{
		if(isWorldGuardEnabled()) return (WorldGuardPlugin) pm.getPlugin("WorldGuard");
		return null;
	}
	
	public static boolean canBreak(Player p, Block b)
	{
		if(!isWorldGuardEnabled()) return true;
		boolean canBreak = getWorldGuard().canBuild(p, b);
		return canBreak;
	}
	
	public static boolean canPlace(Player p, Block b)
	{
		if(!isWorldGuardEnabled()) return true;
		boolean canPlace = getWorldGuard().canBuild(p, b);
		return canPlace;
	}
}
