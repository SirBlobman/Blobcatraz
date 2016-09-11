package com.SirBlobman.blobcatraz.utility;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.config.ConfigWarps;

public class TeleportUtil
{
	/**
	 * Enum for teleportation
	 * <br>Defaults:<dl>
	 * <dd><b>Tiny: </b> 1000 blocks
	 * <dd><b>Normal:</b> 3000 blocks
	 * <dd><b>Far: </b> 6000 blocks
	 * </dl>
	 * @author meowk
	 */
	public enum Teleport
	{
		TINY, NORMAL, FAR;
	}
	
	private static Random r = new Random();
	
	/**
	 * Teleport a player randomly
	 * @param p Player to teleport
	 * @param distance Distance to teleport
	 * @see Teleport
	 * @see Player
	 * @see Player#teleport(Location)
	 */
	public static void randomTP(Player p, Teleport distance)
	{
		if(p == null || distance == null) return;
		Location l = p.getLocation();
		p.setFallDistance(-100.0F);
		int x = l.getBlockX(), y = 110, z = l.getBlockZ();
		YamlConfiguration config = ConfigBlobcatraz.load();
		int tiny = config.getInt("randomTP.max tiny distance");
		int normal = config.getInt("randomTP.max normal distance");
		int far = config.getInt("randomTP.max far distance");
		double chance = Math.random();
		switch(distance)
		{
		case TINY:
			if(chance > 0.5)
			{
				x = x + r.nextInt(tiny);
				z = z + r.nextInt(tiny);
			}
			else
			{
				x = x - r.nextInt(tiny);
				z = z - r.nextInt(tiny);
			}
		case NORMAL:
			if(chance > 0.5)
			{
				x = x + r.nextInt(normal);
				z = z + r.nextInt(normal);
			}
			else
			{
				x = x - r.nextInt(normal);
				z = z - r.nextInt(normal);
			}
		case FAR:
			if(chance > 0.5)
			{
				x = x + r.nextInt(far);
				z = z + r.nextInt(far);
			}
			else
			{
				x = x - r.nextInt(far);
				z = z - r.nextInt(far);
			}
		}
		Location tp = new Location(p.getWorld(), x, y, z);
		Chunk chunk = tp.getChunk();
		chunk.load(true);
		p.teleport(tp);
		p.setFallDistance(0.0F);
		p.sendMessage(Util.blobcatraz + Util.message("player.wasTeleported", x, y, z));
	}
	
	/**
	 * Get the coordinates that something will be teleported to
	 * @param original Original location
	 * @param sx X coordinate. can contain ~ for relative coordinates
	 * @param sy Y coordinate. can contain ~ for relative coordinates
	 * @param sz Z coordinate. can contain ~ for relative coordinates
	 * @return new, relative, Location
	 * @see String
	 * @see Location
	 * @see #getRelative(double, double)
	 */
	public static Location getTeleportCoords(Location original, String sx, String sy, String sz)
	{
		try
		{
			double ox = original.getX();
			double oy = original.getY();
			double oz = original.getZ();
			double x = 0.0D;
			double y = 0.0D;
			double z = 0.0D;

			try
			{
				x = Double.parseDouble(sx);
				y = Double.parseDouble(sy);
				z = Double.parseDouble(sz);
			} catch(Exception ex) {}

			if(sx.equalsIgnoreCase("~")) x = ox;
			if(sy.equalsIgnoreCase("~")) y = oy;
			if(sz.equalsIgnoreCase("~")) z = oz;

			if(sx.startsWith("~") && sx.length() != 1)
			{
				String sx2 = sx.replace("~", "");
				double add = Double.parseDouble(sx2);
				x = getRelative(ox, add);
			}
			if(sy.startsWith("~") && sy.length() != 1)
			{
				String sy2 = sy.replace("~", "");
				double add = Double.parseDouble(sy2);
				y = getRelative(oy, add);
			}
			if(sz.startsWith("~") && sz.length() != 1)
			{
				String sz2 = sz.replace("~", "");
				double add = Double.parseDouble(sz2);
				z = getRelative(oz, add);
			}
			return new Location(original.getWorld(), x, y, z, original.getYaw(), original.getPitch());
		} catch(Exception ex) {return null;}
	}
	
	/**
	 * Adds a coordinate to another
	 * @param original Original coordinate
	 * @param add number to add. If its negative it will be subtracted
	 * @return New relative number
	 * @see Double
	 */
	public static double getRelative(double original, double add)
	{
		return original + add;
	}
	
	/**
	 * Teleport an Entity to a warp
	 * @param e Entity to teleport
	 * @param warp Warp to use
	 * @see Entity#teleport(Location)
	 * @see ConfigWarps#getWarp(String)
	 */
	public static void warp(Entity e, String warp)
	{
		Location tp = ConfigWarps.getWarp(warp);
		if(tp != null) e.teleport(tp);
	}
}