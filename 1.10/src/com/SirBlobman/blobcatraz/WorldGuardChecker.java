package com.SirBlobman.blobcatraz;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

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
	
	public static ApplicableRegionSet getRegions(Player p)
	{
		return getRegions(p.getLocation());
	}
	
	public static ApplicableRegionSet getRegions(Location l)
	{
		World w = l.getWorld();
		RegionManager rgm = getWorldGuard().getRegionManager(w);
		return rgm.getApplicableRegions(l);
	}
	
	public static boolean canBreak(Player p, Block b)
	{
		if(!isWorldGuardEnabled()) return true;
		if(p.isOp()) return true;
		boolean canBreak = getWorldGuard().canBuild(p, b);
		return canBreak;
	}
	
	public static boolean canPlace(Player p, Block b)
	{
		if(!isWorldGuardEnabled()) return true;
		if(p.isOp()) return true;
		boolean canPlace = getWorldGuard().canBuild(p, b);
		return canPlace;
	}
	
	public static boolean canPvP(Player p)
	{
		if(p == null) return false;
		Location l = p.getLocation();
		World w = l.getWorld();
		if(!isWorldGuardEnabled()) return w.getPVP();
		LocalPlayer lp = getWorldGuard().wrapPlayer(p);
		ApplicableRegionSet regions = getRegions(p);
		StateFlag pvp = DefaultFlag.PVP;
		boolean canPvP = regions.testState(lp, pvp);
		return canPvP;
	}
}