package com.SirBlobman.blobcatraz.utility;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
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

public class WorldGuardUtil 
{
	private static Server S = Bukkit.getServer();
	private static PluginManager PM = S.getPluginManager();
	
	/**
	 * Checks if <i>WorldGuard</i> is enabled
	 * @return 
	 * 	<b>true</b> if WorldGuard is enabled
	 * 	<br/><b>false</b> if WorldGuard isn't loaded or is null
	 */
	public static boolean isWGEnabled()
	{
		Plugin wg = PM.getPlugin("WorldGuard");
		if(wg == null || !(wg instanceof WorldGuardPlugin)) return false;
		return wg.isEnabled();
	}
	
	/**
	 * @return instance of WorldGuard
	 */
	public static WorldGuardPlugin getWG()
	{
		if(isWGEnabled()) return (WorldGuardPlugin) PM.getPlugin("WorldGuard");
		return null;
	}
	
	/**
	 * Where is the player standing?
	 * @param p Player
	 * @return regions the player is currently in
	 */
	public static ApplicableRegionSet getRegions(Player p)
	{
		return getRegions(p.getLocation());
	}
	
	public static ApplicableRegionSet getRegions(Location l)
	{
		World w = l.getWorld();
		RegionManager rgm = getWG().getRegionManager(w);
		ApplicableRegionSet ars = rgm.getApplicableRegions(l);
		return ars;
	}
	
	public static boolean canBreak(Player p, Block b)
	{
		if(isWGEnabled()) return true;
		if(p.isOp()) return true;
		boolean canBreak = getWG().canBuild(p, b);
		return canBreak;
	}
	
	public static boolean canPvP(Player p)
	{
		if(p == null) return false;
		Location l = p.getLocation();
		World w = l.getWorld();
		if(!isWGEnabled()) return w.getPVP();
		
		try
		{
			LocalPlayer lp = getWG().wrapPlayer(p);
			ApplicableRegionSet ars = getRegions(p);
			StateFlag pvp = DefaultFlag.PVP;
			boolean canPvP = ars.testState(lp, pvp);
			return canPvP;
		} catch(Exception | Error ex) {return w.getPVP();}
	}
}