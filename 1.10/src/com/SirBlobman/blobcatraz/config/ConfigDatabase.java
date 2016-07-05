package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;

public class ConfigDatabase 
{
	public static HashMap<UUID, String> name = new HashMap<UUID, String>();
	public static HashMap<UUID, Double> balance = new HashMap<UUID, Double>();
	public static HashMap<UUID, Boolean> afk = new HashMap<UUID, Boolean>();
	public static HashMap<UUID, Boolean> banned = new HashMap<UUID, Boolean>();
	public static HashMap<UUID, Long> bannedLength = new HashMap<UUID, Long>();
	public static HashMap<UUID, String> bannedReason = new HashMap<UUID, String>();
	
	private static final File databaseFile = new File(Blobcatraz.instance.getDataFolder(), "database.yml");
	public static FileConfiguration databaseConfig = YamlConfiguration.loadConfiguration(databaseFile);
	
	public static void saveDatabase()
	{
		if(!databaseFile.exists())
		{
			try {databaseFile.createNewFile();}
			catch (Exception ex)
			{
				Util.print("Could not create" + databaseFile);
				ex.printStackTrace();
				return;
			}
		}
		
		for(Entry<UUID, String> e : name.entrySet()) databaseConfig.set("players." + e.getKey() + ".name", e.getValue());
		for(Entry<UUID, Double> e : balance.entrySet()) databaseConfig.set("players." + e.getKey() + ".balance", e.getValue());
		for(Entry<UUID, Boolean> e : afk.entrySet()) databaseConfig.set("players." + e.getKey() + ".afk", e.getValue());
		for(Entry<UUID, Boolean> e : banned.entrySet()) databaseConfig.set("players." + e.getKey() + ".banned.status", e.getValue());
		for(Entry<UUID, Long> e : bannedLength.entrySet()) databaseConfig.set("players." + e.getKey() + ".banned.length", e.getValue());
		for(Entry<UUID, String> e : bannedReason.entrySet()) databaseConfig.set("players." + e.getKey() + ".banned.reason", e.getValue());
		
		try{databaseConfig.save(databaseFile);}
		catch(Exception ex)
		{
			Util.print("Failed to save " + databaseFile);
			ex.printStackTrace();
			return;
		}
	}
	
	public static void loadDatabase()
	{
		try{databaseConfig.load(databaseFile);}
		catch(Exception ex)
		{
			Util.print("Failed to load " + databaseFile + ", attempting to create");
			saveDatabase();
		}
		
		try
		{
			for(String key : databaseConfig.getConfigurationSection("players").getKeys(false))
			{
				UUID uuid = UUID.fromString(key);
				if(uuid == null) Util.print(key + " in " + databaseFile + " is null!");
				
				name.put(uuid, databaseConfig.getString("players." + key + ".name"));
				balance.put(uuid, databaseConfig.getDouble("players." + key + ".balance"));
				afk.put(uuid, databaseConfig.getBoolean("players." + key + ".afk"));
				banned.put(uuid, databaseConfig.getBoolean("players." + key + ".banned.status"));
				bannedLength.put(uuid, databaseConfig.getLong("players." + key + ".banned.length"));
				bannedReason.put(uuid, databaseConfig.getString("players." + key + ".banned.reason"));
			}
		}
		catch (Exception ex)
		{
			Util.print(databaseFile + " is empty or null! Attempting to fix");
			ex.printStackTrace();
			
			UUID uuid = UUID.fromString("5ba03c6c-ad4c-4475-8ec9-8bc8a15a9ebe");
			
			name.put(uuid, "SirBlobman");
			balance.put(uuid, 0.0);
			afk.put(uuid, false);
			banned.put(uuid, false);
			bannedLength.put(uuid, null);
			bannedReason.put(uuid, null);
			
			saveAndLoadDatabase();
		}
	}
	
	public static void saveAndLoadDatabase()
	{
		saveDatabase();
		loadDatabase();
	}
	
	public static void reloadDatabase()
	{
		loadDatabase();
		saveDatabase();
	}
	
	public static void writeDefaults(UUID uuid)
	{
		saveDatabase();
		if(uuid == null) return;
		Player p = Bukkit.getPlayer(uuid);
		if(p == null) return;
		String pname = p.getName();
		
		name.put(uuid, pname);
		banned.put(uuid, false);
		balance.put(uuid, 0.0);
		afk.put(uuid, false);
		bannedReason.put(uuid, null);
		bannedLength.put(uuid, null);
		
		saveDatabase();
	}
	
	public static void resetAllBalances()
	{
		loadDatabase();
		
		for(String key : databaseConfig.getConfigurationSection("players").getKeys(false)) databaseConfig.set("players." + key + ".balance", null);
		
		balance.clear();
		
		saveAndLoadDatabase();
	}
	
	public static double getBalance(OfflinePlayer p)
	{
		if(p == null) return -1.0;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return -1.0;
		loadDatabase();
		if(!balance.containsKey(uuid)) return -1.0;
		
		double bal = balance.get(uuid);
		return bal;
		
	}
	
	public static void setBalance(Player p, double amount)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		
		loadDatabase();
		balance.put(uuid, amount);
		saveDatabase();
	}
	
	public static void addToBalance(Player p, double amount)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		loadDatabase();
		if(amount < 0) amount = 0;
		
		double newBalance = balance.get(uuid) + amount;
		
		setBalance(p, newBalance);
	}
	
	public static void subtractFromBalance(Player p, double amount)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		loadDatabase();
		if(amount < 0) amount = 0;
		
		double newBalance = balance.get(uuid) - amount;
		
		setBalance(p, newBalance);
	}
	
	public static void resetBalance(Player p)
	{
		if(p == null) return;
		setBalance(p, 0);
	}
	
	public static boolean isBanned(Player p)
	{
		if(p == null) return false;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return false;
		
		loadDatabase();
		if(!banned.containsKey(uuid)) return false;
		boolean ban = banned.get(uuid);
		
		return ban;
	}
	
	public static String getBanReason(Player p)
	{
		if(p == null) return "";
		UUID uuid = p.getUniqueId();
		if(uuid == null) return "";
		
		loadDatabase();
		if(!bannedReason.containsKey(uuid)) return "";
		String reason = bannedReason.get(uuid);
		if(reason == null) return "";
		
		return reason;
	}
	
	public static long getEndOfBan(OfflinePlayer p)
	{
		if(p == null) return 0;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return 0;
		
		loadDatabase();
		if(!bannedLength.containsKey(uuid)) return 0;
		long length = bannedLength.get(uuid);
		
		return length;
	}
	
	public static String getEndOfBanFormatted(OfflinePlayer p)
	{
		if(p == null) return "";
		UUID uuid = p.getUniqueId();
		if(uuid == null) return "";
		
		long current = System.currentTimeMillis();
		long end = getEndOfBan(p);
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
		if(hours > 0) format = hours + " Hours, " + minutes + " Minutes, and " + seconds + "Seconds";
		if(days > 0) format = days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + "Seconds";
		if(weeks > 0) format = weeks + " Weeks, " + days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + "Seconds";
		if(months > 0) format = months + " Months, " + weeks + " Weeks, " + days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + "Seconds";
		if(years > 0) format = years + " Years, " + months + " Months, " + weeks + " Weeks, " + days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + "Seconds";
		if(decades > 0) format = decades + " Decades, " + years + " Years, " + months + " Months, " + weeks + " Weeks, " + days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + "Seconds";
		if(centuries > 0) format = centuries + " Centuries, " + decades + " Decades, " + years + " Years, " + months + " Months, " + weeks + " Weeks, " + days + " Days, " + hours + " Hours, " + minutes + " Minutes, and " + seconds + "Seconds";
		
		return format;
	}
	
	public static void tempban(String banner, OfflinePlayer p, long length, String reason)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		
		loadDatabase();
		
		long current = System.currentTimeMillis();
		long end = current + length;
		
		banned.put(uuid, true);
		bannedReason.put(uuid, reason);
		bannedLength.put(uuid, end);
		
		saveDatabase();
		
		if(p.isOnline())
		{
			Player online = (Player) p;
			online.kickPlayer(Util.banned + Util.color(reason) + "\n§9" + getEndOfBanFormatted(p));
		}
		
		Util.broadcast("§9" + banner + " §rbanned §c" + p.getName() + " \n§rfor: " + getEndOfBanFormatted(p) + " \n§rbecause: " + Util.color(reason));
	}
	
	public static void ban(String banner, OfflinePlayer p, String reason)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		
		loadDatabase();
		banned.put(uuid, true);
		bannedReason.put(uuid, reason);
		saveDatabase();
		
		if(p.isOnline())
		{
			Player online = (Player) p;
			online.kickPlayer(Util.banned + Util.color(reason));
		}
		
		Util.broadcast("§9" + banner + " §rbanned §c" + p.getName() + " \n§rbecause: " + Util.color(reason));
	}
	
	public static void unban(String unbanner, OfflinePlayer p)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		
		loadDatabase();
		banned.put(uuid, false);
		bannedReason.put(uuid, null);
		bannedLength.put(uuid, null);
		saveDatabase();
		
		Util.broadcast("§9" + unbanner + " §runbanned §b" + p.getName());
	}
	
	public static boolean getAFKStatus(Player p)
	{
		if(p == null) return false;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return false;
		
		loadDatabase();
		if(!afk.containsKey(uuid)) return false;
		
		boolean afkStatus = afk.get(uuid);
		
		return afkStatus;
	}
	
	public static void setAFK(Player p)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		
		loadDatabase();
		afk.put(uuid, true);
		saveDatabase();
	}
	
	public static void setNotAFK(Player p)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		
		loadDatabase();
		afk.put(uuid, false);
		saveDatabase();
	}
}