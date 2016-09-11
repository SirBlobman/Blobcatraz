package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.utility.BalanceComparator;
import com.SirBlobman.blobcatraz.utility.Util;
import com.google.common.collect.Maps;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ConfigDatabase
{
	private static final File dataFolder = new File(Blobcatraz.instance.getDataFolder(), "users");
	
	/**
	 * Saves the data for this player
	 * @param op OfflinePlayer to save
	 * @param fc Config to save
	 * @see OfflinePlayer
	 */
	public static void save(OfflinePlayer op, FileConfiguration fc)
	{
		if(op == null) return;
		UUID uuid = op.getUniqueId();
		if(uuid == null) return;
		File f = new File(dataFolder, uuid + ".yml");
		if(!dataFolder.exists()) dataFolder.mkdir();
		if(!f.exists()) writeDefaults(uuid);
		try
		{
			fc.save(f);
		} catch (Exception ex)
		{
			Util.print("Failed to save " + op.getName() + "'s data file! There may be errors!");
		}
	}
	
	/**
	 * Loads the database for this player
	 * @param op OfflinePlayer to load
	 * @return Config file for this player
	 * @see OfflinePlayer
	 * @see YamlConfiguration
	 */
	public static YamlConfiguration load(OfflinePlayer op)
	{
		if(op == null) return null;
		UUID uuid = op.getUniqueId();
		if(uuid == null) return null;
		File f = new File(dataFolder, uuid + ".yml");
		YamlConfiguration yc = YamlConfiguration.loadConfiguration(f);
		if(!f.exists()) save(op, yc);
		try
		{
			yc.load(f);
			if(yc.get("name") == null) writeDefaults(uuid);
			return yc;
		} catch(Exception ex)
		{
			Util.print("Failed to load " + op.getName() + "'s data file! There may be errors");
			return null;
		}
	}
	
	public static void reload()
	{
		for(OfflinePlayer op : Bukkit.getOfflinePlayers()) load(op);
	}
	
	/**
	 * Write the default values for a UUID of a player
	 * @param uuid UUID of a Player
	 * @see UUID
	 * @see OfflinePlayer
	 */
	public static void writeDefaults(UUID uuid)
	{
		if(uuid == null) return;
		OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
		if(op == null) return;
		File f = new File(dataFolder, uuid + ".yml");
		if(!f.exists())
		{
			try
			{
				f.createNewFile();
			} catch(Exception ex)
			{
				Util.print("Failed to create data for " + uuid + ": " + ex.getCause());
				Util.print("There may be errors");
				return;
			}
		}
		
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		fc.set("name", op.getName());
		fc.set("afk", false);
		fc.set("frozen", false);
		fc.set("banned.status", false);
		fc.set("banned.length", null);
		fc.set("banned.reason", null);
		fc.set("balance", 0.0D);
		fc.set("canSpy", false);
		fc.set("autopickup", true);
		fc.set("votes", 0);
		fc.set("homes", "");
		save(op, fc);
	}
	
	public static File[] getDataFiles()
	{
		if(dataFolder.listFiles() == null)
		{
			dataFolder.mkdir();
		}
		return dataFolder.listFiles();
	}
	
	public static boolean doesPlayerHaveAccount(OfflinePlayer op)
	{
		File f = new File(dataFolder, op.getUniqueId() + ".yml");
		return f.exists();
	}
	
	/**
	 * Reset the balance of every existing Player
	 * @see File#delete()
	 */
	public static void resetAllBalances()
	{
		File[] dataFiles = dataFolder.listFiles();
		for(File f : dataFiles)
		{
			String id = FilenameUtils.getBaseName(f.getName());
			UUID uuid = UUID.fromString(id);
			if(uuid == null) f.delete();
			OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
			if(op == null) f.delete();
			resetBalance(op);
		}
	}
	
	/**
	 * Get the balance of a Player
	 * @param op OfflinePlayer to get the balance
	 * @return double of the balance
	 * @see Double
	 * @see OfflinePlayer
	 */
	public static double getBalance(OfflinePlayer op)
	{
		if(op == null) return 0.0D;
		double balance = load(op).getDouble("balance");
		return balance;
	}
	
	/**
	 * Set the balance of a Player
	 * @param op OfflinePlayer to set the balance of
	 * @param amount Amount to set the balance to
	 * @return New balance
	 * @see Double
	 * @see OfflinePlayer
	 */
	public static double setBalance(OfflinePlayer op, double amount)
	{
		if(op == null || Double.isNaN(amount)) return Double.NaN;
		FileConfiguration fc = load(op);
		fc.set("balance", amount);
		save(op, fc);
		return getBalance(op);
	}
	
	/**
	 * Add money to a Player's bank
	 * @param op OfflinePlayer to add money to
	 * @param amount Money to to add
	 * @return New Balance
	 * @see Double
	 * @see OfflinePlayer
	 */
	public static double addMoney(OfflinePlayer op, double amount)
	{
		if(op == null) return Double.NaN;
		double balance = getBalance(op) + amount;
		setBalance(op, balance);
		return getBalance(op);
	}
	
	/**
	 * Take money from a Player
	 * @param op OfflinePlayer to remove money from
	 * @param amount Money to subtract
	 * @return New Balance
	 * @see Double
	 * @see OfflinePlayer
	 */
	public static double subtractMoney(OfflinePlayer op, double amount)
	{
		if(op == null) return Double.NaN;
		double balance = getBalance(op) - amount;
		setBalance(op, balance);
		return getBalance(op);
	}
	
	/**
	 * Reset the balance of a Player to $0.00
	 * @param op OfflinePlayer to reset
	 * @see OfflinePlayer
	 */
	public static void resetBalance(OfflinePlayer op)
	{
		if(op == null) return;
		setBalance(op, 0.0D);
	}
	
	/**
	 * Check if a player is banned
	 * @param op 
	 * @return <b>false</b> if the player is not banned or they are null<br> <b>true</b> if they are banned
	 * @see Boolean
	 * @see OfflinePlayer
	 */
	public static boolean isBanned(OfflinePlayer op)
	{
		if(op == null) return false;
		return load(op).getBoolean("banned.status");
	}
	
	/**
	 * Get the reason why someone is banned
	 * @param op Banned Player
	 * @return Reason
	 * @see String
	 * @see OfflinePlayer
	 */
	public static String getBanReason(OfflinePlayer op)
	{
		if(op == null) return null;
		String reason = load(op).getString("banned.reason");
		if(reason == null) return null;
		String realReason = Util.format(reason);
		return realReason;
	}
	
	/**
	 * Get the time when a Player will be unbanned
	 * @param op Banned Player
	 * @return Time when the Player's ban will end
	 * @see Long
	 * @see OfflinePlayer
	 */
	public static long getEndOfBan(OfflinePlayer op)
	{
		if(op == null) return 0;
		return load(op).getLong("banned.length");
	}
	
	/**
	 * Get the formatted time when a Player will be unbanned
	 * @param op Banned Player
	 * @return Time when the Player's ban will end. <br><b>Example: </b>1 day, 5 hours, 20 minutes, and 59 seconds
	 */
	public static String getEndOfBanFormatted(OfflinePlayer op)
	{
		if(op == null) return null;
		long current = System.currentTimeMillis();
		long end = getEndOfBan(op);
		long millis = end - current;
		
		int seconds = 0, minutes = 0, hours = 0, days = 0, weeks = 0, months = 0, years = 0, decades = 0, centuries = 0;
		while(millis > 1000L) {millis -= 1000L; seconds++;}
		while(seconds > 60) {seconds -= 60; minutes++;}
		while(minutes > 60) {minutes -= 60; hours++;}
		while(hours > 24) {hours -= 24; days++;}
		while(days > 7) {days -= 7; weeks++;}
		while(weeks > 4) {weeks -= 4; months++;}
		while(months > 12) {months -= 12; years++;}
		while(years > 10) {years -= 10; decades++;}
		while(decades > 10) {decades -= 10; centuries++;}
		
		String format = "0 Seconds";
		
		if(seconds > 0) format = seconds + " Seconds";
		if(minutes > 0) format = minutes + " Minutes, and " + seconds + " Seconds";
		if(hours > 0) format = hours + " Hours, " + minutes + " Minutes, and " + seconds + " Seconds";
		if(days > 0) format = days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + " Seconds";
		if(weeks > 0) format = weeks + " Weeks, " + days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + " Seconds";
		if(months > 0) format = months + " Months, " + weeks + " Weeks, " + days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + " Seconds";
		if(years > 0) format = years + " Years, " + months + " Months, " + weeks + " Weeks, " + days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + " Seconds";
		if(decades > 0) format = decades + " Decades, " + years + " Years, " + months + " Months, " + weeks + " Weeks, " + days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + " Seconds";
		if(centuries > 0) format = centuries + " Centuries, " + decades + " Decades, " + years + " Years, " + months + " Months, " + weeks + " Weeks, " + days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + " Seconds";
		
		return format;
	}
	
	public static void ban(String banner, OfflinePlayer op, String reason)
	{
		if(op == null) return;
		FileConfiguration fc = load(op);
		fc.set("banned.status", true);
		fc.set("banned.reason", reason);
		fc.set("banned.length", null);
		save(op, fc);
		
		if(op.isOnline())
		{
			Player p = (Player) op;
			p.kickPlayer(Util.banned + Util.format(reason));
		}
		Util.broadcast("§9" + banner + " §rbanned §c" + op.getName() + " \nBecause: " + Util.format(reason));
	}
	
	public static void tempban(String banner, OfflinePlayer op, long length, String reason)
	{
		if(op == null) return;
		long current = System.currentTimeMillis();
		long end = current + length;
		
		FileConfiguration fc = load(op);
		fc.set("banned.status", true);
		fc.set("banned.reason", Util.format(reason));
		fc.set("banned.length", end);
		save(op, fc);
		
		if(op.isOnline())
		{
			Player p = (Player) op;
			p.kickPlayer(Util.banned + Util.format(reason) + "\n§9" + getEndOfBanFormatted(p));
		}
		Util.broadcast("§9" + banner + " §rbanned §c" + op.getName() + "\n§rFor: " + getEndOfBanFormatted(op) + "\n§rBecause: " + Util.format(reason));
	}
	
	public static void unban(String unbanner, OfflinePlayer op)
	{
		if(op == null) return;
		FileConfiguration fc = load(op);
		fc.set("banned.status", false);
		fc.set("banned.reason", null);
		fc.set("banned.length", null);
		save(op, fc);
		
		Util.broadcast("§9" + unbanner + " §runbanned §b" + op.getName());
	}
	
	public static boolean isAFK(OfflinePlayer op)
	{
		if(op == null) return false;
		return load(op).getBoolean("afk");
	}
	
	public static void setAFK(OfflinePlayer op, boolean afk)
	{
		if(op == null) return;
		FileConfiguration fc = load(op);
		fc.set("afk", afk);
		save(op, fc);
	}
	
	public static void setFrozen(OfflinePlayer op, boolean freeze)
	{
		if(op == null) return;
		FileConfiguration fc = load(op);
		fc.set("frozen", freeze);
		save(op, fc);
	}
	
	public static boolean isFrozen(OfflinePlayer op)
	{
		if(op == null) return false;
		return load(op).getBoolean("frozen");
	}
	
	public static HashMap<UUID, Double> getBalances()
	{
		HashMap<UUID, Double> balances = Maps.newHashMap();
		File[] files = getDataFiles();
		if(files.length > 0)
		{
			for(File f : files)
			{
				String id = FilenameUtils.getBaseName(f.getName());
				UUID uuid = UUID.fromString(id);
				if(uuid == null) f.delete();
				OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
				if(op == null) f.delete();
				FileConfiguration fc = load(op);
				balances.put(uuid, fc.getDouble("balance"));
			}
		}
		return balances;
	}
	
	public static List<String> getBalanceTopTen()
	{
		List<Entry<UUID, Double>> sorted = new ArrayList<Entry<UUID, Double>>(getBalances().entrySet());
		Collections.sort(sorted, Collections.reverseOrder(new BalanceComparator()));
		List<Entry<UUID, Double>> t;
		t = sorted.subList(0, sorted.size());
		if(sorted.size() > 9) t = sorted.subList(0, 9);
		
		List<String> balTop = new ArrayList<String>();
		int i = 1;
		for(Entry<UUID, Double> e : t)
		{
			UUID uuid = e.getKey();
			if(uuid == null) break;
			OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
			if(op == null) break;
			String name = op.getName();
			if(name == null) break;
			if(e.getValue() == 0.0D) break;
			balTop.add(i + ". §6" + name + ": §e$" + e.getValue());
			i++;
		}
		return balTop;
	}
	
	public static boolean getCanSpy(OfflinePlayer op)
	{
		if(op == null) return false;
		return load(op).getBoolean("canSpy");
	}
	
	public static boolean toggleCanSpy(OfflinePlayer op)
	{
		if(op == null) return false;
		FileConfiguration fc = load(op);
		fc.set("canSpy", !getCanSpy(op));
		save(op, fc);
		return getCanSpy(op);
	}
	
	public static List<String> getHomes(OfflinePlayer op)
	{
		if(op == null) return null;
		List<String> homeList = new ArrayList<String>();
		FileConfiguration fc = load(op);
		ConfigurationSection cs = fc.getConfigurationSection("homes");
		if(cs == null) {writeDefaults(op.getUniqueId()); return homeList;}
		Set<String> keys = cs.getKeys(false);
		if(keys == null) return homeList;
		for(String home : keys) homeList.add(home);
		Collections.sort(homeList);
		return homeList;
	}
	
	public static Location getHome(String home, OfflinePlayer op)
	{
		if(op == null || home == null) return null;
		if(getHomes(op).contains(home))
		{
			Location l = new Location(null, 0, 0, 0);
			FileConfiguration fc = load(op);
			String path = "homes." + home;
			String worldName = fc.getString(path + ".world");
			World w = Bukkit.getWorld(worldName);
			double x = fc.getDouble(path + ".x");
			double y = fc.getDouble(path + ".y");
			double z = fc.getDouble(path + ".z");
			float yaw = (float) fc.getDouble(path + ".yaw");
			float pitch = (float) fc.getDouble(path + ".pitch");
			l.setWorld(w); l.setX(x); l.setY(y); l.setZ(z); l.setYaw(yaw); l.setPitch(pitch);
			return l;
		}
		return null;
	}
	
	public static void teleportHome(String home, Player p)
	{
		if(p == null || home == null) return;
		if(getHomes(p).contains(home))
		{
			Location l = getHome(home, p);
			p.teleport(l);
		}
	}
	
	public static void setHome(OfflinePlayer op, Location l, String home)
	{
		if(op == null || l == null || home == null) return;
		String path = "homes." + home;
		FileConfiguration fc = load(op);
		fc.set(path + ".world", l.getWorld().getName());
		fc.set(path + ".x", l.getX());
		fc.set(path + ".y", l.getY());
		fc.set(path + ".z", l.getZ());
		fc.set(path + ".pitch", l.getPitch());
		fc.set(path + ".yaw", l.getYaw());
		save(op, fc);
	}
	
	public static boolean canAutoPickup(OfflinePlayer op)
	{
		if(op == null) return false;
		FileConfiguration fc = load(op);
		if(fc.get("autopickup") == null) 
		{
			fc.set("autopickup", true);
			save(op, fc);
		}
		return load(op).getBoolean("autopickup");
	}
	
	public static boolean toggleAutoPickup(OfflinePlayer op)
	{
		if(op == null) return false;
		FileConfiguration fc = load(op);
		fc.set("autopickup", !canAutoPickup(op));
		save(op, fc);
		return canAutoPickup(op);
	}

	public static void addVote(OfflinePlayer op)
	{
		if(op == null) return;
		YamlConfiguration yc = load(op);
		int vote = yc.getInt("votes");
		yc.set("votes", vote + 1);
		save(op, yc);
	}
}