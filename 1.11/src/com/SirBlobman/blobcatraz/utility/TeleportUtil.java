package com.SirBlobman.blobcatraz.utility;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TeleportUtil extends Util
{
	public enum Teleport {TINY, NORMAL, FAR;}
	
	public static void randomTP(Player p, Teleport t)
	{
		if(p == null || t == null) return;
		Location l = p.getLocation();
		p.setFallDistance(-100.0F);
		int x = l.getBlockX();
		int y = 110;
		int z = l.getBlockZ();
		int tiny = CB.getInt("random tp.max distance.tiny");
		int norm = CB.getInt("random tp.max distance.normal");
		int fard = CB.getInt("random tp.max distance.far");
		double chance = Math.random();
		switch(t)
		{
		case TINY:
			if(chance > 0.5)
			{
				x += R.nextInt(tiny);
				z += R.nextInt(tiny);
			}
			else
			{
				x -= R.nextInt(tiny);
				z -= R.nextInt(tiny);
			}
		case NORMAL:
			if(chance > 0.5)
			{
				x += R.nextInt(norm);
				z += R.nextInt(norm);
			}
			else
			{
				x -= R.nextInt(norm);
				z -= R.nextInt(norm);
			}
		case FAR:
			if(chance > 0.5)
			{
				x += R.nextInt(fard);
				z += R.nextInt(fard);
			}
			else
			{
				x -= R.nextInt(fard);
				z -= R.nextInt(fard);
			}
		}
		World w = p.getWorld();
		Location tp = new Location(w, x, y, z);
		p.teleport(tp);
		p.setFallDistance(0.0F);
		p.sendMessage(prefix + option("command.random tp", x, y, z));
	}
	
	public static Location coords(Location o, String sx, String sy, String sz)
	{
		try
		{
			double ox = o.getX();
			double oy = o.getY();
			double oz = o.getZ();
			
			double x = relative(ox, sx);
			double y = relative(oy, sy);
			double z = relative(oz, sz);
			
			World w = o.getWorld();
			float pi = o.getPitch();
			float ya = o.getYaw();
			Location l = new Location(w, x, y, z, ya, pi);
			return l;
		} catch(Exception ex)
		{
			String error = "Location Error:\n" + ex.getMessage();
			Util.print(error);
			return o;
		}
	}
	
	public static double relative(double o, String s)
	{
		if(s.equals("~")) return o;
		if(s.startsWith("~"))
		{
			String s2 = s.replace("~", "");
			double add = Double.parseDouble(s2);
			double ret = o + add;
			return ret;
		}
		return Double.parseDouble(s);
	}
}