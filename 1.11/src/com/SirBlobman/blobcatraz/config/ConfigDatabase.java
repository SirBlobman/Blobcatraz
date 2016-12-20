package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.compare.BalanceComparator;
import com.google.common.collect.Maps;

public class ConfigDatabase
{
	private static final File folder = new File(Blobcatraz.folder, "users");
	
	public static YamlConfiguration load(OfflinePlayer op)
	{
		if(op == null) return null;
		UUID uuid = op.getUniqueId();
		if(uuid == null) return null;
		String f = uuid + ".yml";
		File file = new File(folder, f);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if(!file.exists()) save(op, config);
		try
		{
			config.load(file);
			defaults(uuid);
			return config;
		} catch(Exception ex)
		{
			Util.print("Failed to load user: " + op.getName() + ": " + ex.getMessage());
			return null;
		}
	}
	
	public static void save(OfflinePlayer op, YamlConfiguration config)
	{
		if(op == null) return;
		UUID uuid = op.getUniqueId();
		if(uuid == null) return;
		String f = uuid + ".yml";
		File file = new File(folder, f);
		if(!file.exists())
		{
			try
			{
				folder.mkdirs();
				file.createNewFile();
				defaults(uuid);
			} catch(Exception ex) {Util.print("Failed to create " + file + ": " + ex.getMessage());}
		}
		
		try{config.save(file);}
		catch(Exception ex) {Util.print("Failed to save " + file + ": " + ex.getMessage());}
	}
	
	private static void defaults(UUID uuid)
	{
		if(uuid == null) return;
		OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
		if(op == null) return;
		
		String f = uuid + ".yml";
		File file = new File(folder, f);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		set(config, "name", op.getName(), false);
		set(config, "prefix", "[Default] &f", false);
		set(config, "nick", op.getName(), false);
		set(config, "afk", false, false);
		set(config, "frozen", false, false);
		set(config, "banned.status", false, false);
		set(config, "banned.length", null, false);
		set(config, "banned.reason", null, false);
		set(config, "can spy", false, false);
		set(config, "auto pickup", true, false);
		set(config, "votes", 0, false);
		set(config, "homes", Arrays.asList(), false);
		save(op, config);
	}
	
	private static void set(YamlConfiguration config, String path, Object value, boolean force)
	{
		boolean b1 = (config.get(path) == null);
		if(b1 || force) config.set(path, value);
	}
	
	public static File[] files()
	{
		if(folder.listFiles() == null) folder.mkdir();
		return folder.listFiles();
	}
	
	public static boolean hasAccount(OfflinePlayer op)
	{
		if(op == null) return false;
		UUID uuid = op.getUniqueId();
		if(uuid == null) return false;
		String f = uuid + ".yml";
		File file = new File(folder, f);
		return file.exists();
	}
	
	public static void resetAllBalances()
	{
		File[] files = files();
		for(File file : files)
		{
			String f = file.getName();
			String name = FilenameUtils.getBaseName(f);
			UUID uuid = UUID.fromString(name);
			if(uuid == null) file.delete();
			OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
			if(op == null) file.delete();
			resetBalance(op);
		}
	}
	
	public static double balance(OfflinePlayer op)
	{
		if(!hasAccount(op)) return 0.0D;
		YamlConfiguration config = load(op);
		double balance = config.getDouble("balance");
		return balance;
	}
	
	public static void setBalance(OfflinePlayer op, double amount)
	{
		if(op == null) return;
		YamlConfiguration config = load(op);
		set(config, "balance", amount, true);
		save(op, config);
	}
	
	public static void deposit(OfflinePlayer op, double amount)
	{
		if(op == null) return;
		double balance = balance(op) + amount;
		setBalance(op, balance);
	}
	
	public static void withdraw(OfflinePlayer op, double amount)
	{
		if(op == null) return;
		double balance = balance(op) - amount;
		setBalance(op, balance);
	}
	
	public static void resetBalance(OfflinePlayer op)
	{
		if(op == null) return;
		setBalance(op, 0.0D);
	}
	
	public static Map<UUID, Double> balances()
	{
		Map<UUID, Double> map = Maps.newHashMap();
		File[] files = files();
		if(files.length > 0)
		{
			for(File file : files)
			{
				String f = file.getName();
				String name = FilenameUtils.getBaseName(f);
				UUID uuid = UUID.fromString(name);
				if(uuid == null) file.delete();
				OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
				if(op == null) file.delete();
				double balance = balance(op);
				map.put(uuid, balance);
			}
		}
		return map;
	}
	
	public static List<String> balTop()
	{
		Map<UUID, Double> map = balances();
		List<Entry<UUID, Double>> list = new ArrayList<Entry<UUID, Double>>(map.entrySet());
		Comparator<Entry<UUID, Double>> comparator = Collections.reverseOrder(new BalanceComparator());
		Collections.sort(list, comparator);
		
		List<Entry<UUID, Double>> sub;
		sub = list.subList(0, list.size());
		if(list.size() > 9) sub = list.subList(0, 9);
		
		List<String> top = new ArrayList<String>();
		int i = 1;
		for(Entry<UUID, Double> e : sub)
		{
			UUID uuid = e.getKey();
			if(uuid != null)
			{
				OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
				if(op != null)
				{
					String name = op.getName();
					double bala = e.getValue();
					if(name != null && bala != 0.0D)
					{
						String add = Util.format(i + ") &6" + name + ": &e" + Util.monetize(bala));
						top.add(add);
						i++;
					}
				}
			}
		}
		return top;
	}
	
	public static boolean banned(OfflinePlayer op)
	{
		if(op == null) return false;
		YamlConfiguration config = load(op);
		boolean b = config.getBoolean("banned.status");
		return b;
	}
	
	public static String banReason(OfflinePlayer op)
	{
		if(op == null) return "";
		YamlConfiguration config = load(op);
		String reason = config.getString("banned.reason");
		if(reason == null) return "";
		String real = Util.format(reason);
		return real;
	}
	
	public static long endOfBan(OfflinePlayer op)
	{
		if(op == null) return 0;
		YamlConfiguration config = load(op);
		long l = config.getLong("banned.length");
		return l;
	}
	
	public static String endOfBanFormatted(OfflinePlayer op)
	{
		if(op == null) return "";
		long current = System.currentTimeMillis();
		long end = endOfBan(op);
		long mil = end - current;
		
		int s = 0, m = 0, h = 0, d = 0, w = 0, mo = 0, y = 0;
		while(mil > 1000L) {mil -= 1000L; s++;}
		while(s > 60) {s -= 60; m++;}
		while(m > 60) {h -= 60; h++;}
		while(h > 24) {h -= 24; d++;}
		while(d > 7) {d -= 7; w++;}
		while(w > 4) {w -= 4; mo++;}
		while(mo > 12) {mo -=12; y++;}
		
		String format = "0 Seconds";
		if(s > 0) format = s + " Seconds";
		if(m > 0) format = m + " Minutes, and " + s + " Seconds";
		if(h > 0) format = h + " Hours, " + m + " Minutes, and " + s + " Seconds";
		if(d > 0) format = d + " Days, " + h + " Hours, " + m + " Minutes, and " + s + " Seconds";
		if(w > 0) format = w + " Weeks, " + d + " Days, " + h + " Hours, " + m + " Minutes, and " + s + " Seconds";
		if(mo > 0) format = mo + " Months, " + w + " Weeks, " + d + " Days, " + h + " Hours, " + m + " Minutes, and " + s + " Seconds";
		if(y > 0) format = y + " Years, " + mo + " Months, " + w + " Weeks, " + d + " Days, " + h + " Hours, " + m + " Minutes, and " + s + " Seconds";
		
		return format;
	}
	
	public static void ban(String banner, OfflinePlayer op, String reason)
	{
		if(op == null) return;
		if(op.equals(PlayerUtil.owner())) return;
		YamlConfiguration config = load(op);
		set(config, "banned.status", true, true);
		set(config, "banned.reason", reason, true);
		set(config, "banned.length", null, true);
		save(op, config);
		
		boolean online = op.isOnline();
		if(online)
		{
			Player p = (Player) op;
			String ban = Util.prefix + Util.option("player.banned") + Util.format(reason);
			p.kickPlayer(ban);
		}
		Util.broadcast(Util.format("&9" + banner + " &rbanned &c" + op.getName() + " \nReason: " + reason));
	}
	
	public static void tempban(String banner, OfflinePlayer op, long time, String reason)
	{
		if(op == null) return;
		if(op.equals(PlayerUtil.owner())) return;
		long cur = System.currentTimeMillis();
		long end = cur + time;
		
		YamlConfiguration config = load(op);
		set(config, "banned.status", true, true);
		set(config, "banned.reason", reason, true);
		set(config, "banned.length", end, true);
		save(op, config);
		
		String format = endOfBanFormatted(op);
		if(op.isOnline())
		{
			Player p = (Player) op;
			String ban = Util.prefix + Util.option("player.banned") + Util.format(reason) + "\n§9" + format;
			p.kickPlayer(ban);
		}
		Util.broadcast(Util.format("&9" + banner + " &rbanned &c" + op.getName() + "\n&r&lTime: &r" + format + "\n&r&lReason: &r" + reason));
	}
	
	public static void unban(String banner, OfflinePlayer op)
	{
		if(op == null) return;
		YamlConfiguration config = load(op);
		set(config, "banned.status", false, true);
		set(config, "banned.reason", null, true);
		set(config, "banned.length", null, true);
		save(op, config);
		
		Util.broadcast(Util.format("&9" + banner + " &runbanned &b" + op.getName()));
	}
	
	public static boolean afk(OfflinePlayer op)
	{
		if(op == null) return false;
		YamlConfiguration config = load(op);
		return config.getBoolean("afk");
	}
	
	public static void afk(OfflinePlayer op, boolean afk)
	{
		if(op == null) return;
		YamlConfiguration config = load(op);
		set(config, "afk", afk, true);
		save(op, config);
	}
	
	public static void frozen(OfflinePlayer op, boolean freeze)
	{
		if(op == null) return;
		YamlConfiguration config = load(op);
		set(config, "frozen", freeze, true);
		save(op, config);
	}
	
	public static boolean frozen(OfflinePlayer op)
	{
		if(op == null) return false;
		YamlConfiguration config = load(op);
		return config.getBoolean("frozen");
	}
	
	public static boolean canSpy(OfflinePlayer op)
	{
		if(op == null) return false;
		YamlConfiguration config = load(op);
		return config.getBoolean("can spy");
	}
	
	public static void canSpy(OfflinePlayer op, boolean spy)
	{
		if(op == null) return;
		YamlConfiguration config = load(op);
		set(config, "can spy", spy, true);
		save(op, config);
	}
	
	public static List<String> homes(OfflinePlayer op)
	{
		if(op == null) return null;
		List<String> list = new ArrayList<String>();
		YamlConfiguration config = load(op);
		ConfigurationSection section = config.getConfigurationSection("homes");
		if(section == null) return list;
		Set<String> keys = section.getKeys(false);
		if(keys == null) return list;
		for(String s : keys) list.add(s);
		Collections.sort(list);
		return list;
	}
	
	public static Location home(String name, OfflinePlayer op)
	{
		if(op == null || name == null) return null;
		List<String> homes = homes(op);
		if(homes.contains(name))
		{
			YamlConfiguration config = load(op);
			Location home = new Location(null, 0, 0, 0);
			String path = "homes." + name + ".";
			String world = config.getString(path + "world");
			World w = Bukkit.getWorld(world);
			double x = config.getDouble(path + "x");
			double y = config.getDouble(path + "y");
			double z = config.getDouble(path + "z");
			float yaw = (float) config.getDouble(path + "yaw");
			float pitch = (float) config.getDouble(path + "pitch");
			
			home.setWorld(w);
			home.setX(x);
			home.setY(y);
			home.setZ(z);
			home.setYaw(yaw);
			home.setPitch(pitch);
			return home;
		}
		return op.getBedSpawnLocation();
	}
	
	public static void tpHome(String name, Player p)
	{
		if(p == null || name == null) return;
		List<String> homes = homes(p);
		if(homes.contains(name))
		{
			Location home = home(name, p);
			p.teleport(home);
		}
	}
	
	public static void setHome(OfflinePlayer op, Location home, String name)
	{
		if(op == null || home == null || name == null) return;
		YamlConfiguration config = load(op);
		String path = "homes." + name + ".";
		set(config, path + "world", home.getWorld().getName(), true);
		set(config, path + "x", home.getX(), true);
		set(config, path + "y", home.getY(), true);
		set(config, path + "z", home.getZ(), true);
		set(config, path + "pitch", home.getPitch(), true);
		set(config, path + "yaw", home.getYaw(), true);
		save(op, config);
	}
	
	public static boolean autopickup(OfflinePlayer op)
	{
		if(op == null) return false;
		YamlConfiguration config = load(op);
		return config.getBoolean("auto pickup");
	}
	
	public static void autopickup(OfflinePlayer op, boolean auto)
	{
		if(op == null) return;
		YamlConfiguration config = load(op);
		set(config, "auto pickup", auto, true);
		save(op, config);
	}
	
	public static void vote(OfflinePlayer op)
	{
		if(op == null) return;
		YamlConfiguration config = load(op);
		int votes = config.getInt("votes");
		set(config, "votes", votes + 1, true);
		save(op, config);
	}
	
	public static int votes(OfflinePlayer op)
	{
		if(op == null) return 0;
		YamlConfiguration config = load(op);
		int votes = config.getInt("votes");
		return votes;
		
	}
}