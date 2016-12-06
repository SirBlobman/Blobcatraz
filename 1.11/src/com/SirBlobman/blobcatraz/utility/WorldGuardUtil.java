package com.SirBlobman.blobcatraz.utility;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class WorldGuardUtil extends Util
{
	public static boolean WGEnabled()
	{
		Plugin plugin = PM.getPlugin("WorldGuard");
		boolean b1 = (plugin == null);
		if(b1) return false;
		boolean b2 = !(plugin instanceof WorldGuardPlugin);
		if(b2) return false;
		return plugin.isEnabled();
	}
	
	public static WorldGuardPlugin wg()
	{
		if(WGEnabled())
		{
			Plugin plugin = PM.getPlugin("WorldGuard");
			WorldGuardPlugin wg = (WorldGuardPlugin) plugin;
			return wg;
		}
		return null;
	}
	
	public static ApplicableRegionSet regions(Player p)
	{
		Location l = p.getLocation();
		ApplicableRegionSet ars = regions(l);
		return ars;
	}
	
	public static ApplicableRegionSet regions(Location l)
	{
		World w = l.getWorld();
		WorldGuardPlugin wg = wg();
		RegionManager rm = wg.getRegionManager(w);
		ApplicableRegionSet ars = rm.getApplicableRegions(l);
		return ars;
	}
	
	public static boolean canBreak(Player p, Block b)
	{
		if(WGEnabled())
		{
			WorldGuardPlugin wg = wg();
			boolean b1 = wg.canBuild(p, b);
			return b1;
		}
		if(p.isOp()) return true;
		return true;
	}
	
	public static boolean canPVP(Player p)
	{
		if(WGEnabled())
		{
			try
			{
				WorldGuardPlugin wg = wg();
				LocalPlayer lp = wg.wrapPlayer(p);
				ApplicableRegionSet ars = regions(p);
				StateFlag pvp = DefaultFlag.PVP;
				boolean b1 = ars.testState(lp, pvp);
				return b1;
			} catch(Throwable ex) 
			{
				Location l = p.getLocation();
				World w = l.getWorld();
				return w.getPVP();
			}
		}
		
		Location l = p.getLocation();
		World w = l.getWorld();
		return w.getPVP();
	}
}