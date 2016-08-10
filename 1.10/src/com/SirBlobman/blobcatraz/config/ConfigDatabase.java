package com.SirBlobman.blobcatraz.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.Blobcatraz;
import com.SirBlobman.blobcatraz.Util;
import com.google.common.collect.Maps;

public class ConfigDatabase 
{
	public static HashMap<UUID, String> name = Maps.newHashMap();
	public static HashMap<UUID, Double> balance = Maps.newHashMap();
	public static HashMap<UUID, Boolean> afk = Maps.newHashMap();
	public static HashMap<UUID, Boolean> frozen = Maps.newHashMap();
	public static HashMap<UUID, Boolean> banned = Maps.newHashMap();
	public static HashMap<UUID, Long> bannedLength = Maps.newHashMap();
	public static HashMap<UUID, String> bannedReason = Maps.newHashMap();
	public static HashMap<UUID, Boolean> spy = Maps.newHashMap();
	
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
		for(Entry<UUID, Boolean> e : frozen.entrySet()) databaseConfig.set("players." + e.getKey() + ".frozen", e.getValue());
		for(Entry<UUID, Boolean> e : banned.entrySet()) databaseConfig.set("players." + e.getKey() + ".banned.status", e.getValue());
		for(Entry<UUID, Long> e : bannedLength.entrySet()) databaseConfig.set("players." + e.getKey() + ".banned.length", e.getValue());
		for(Entry<UUID, String> e : bannedReason.entrySet()) databaseConfig.set("players." + e.getKey() + ".banned.reason", e.getValue());
		for(Entry<UUID, Boolean> e : spy.entrySet()) databaseConfig.set("players." + e.getKey() + ".canSpy", e.getValue());
		
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
				frozen.put(uuid, databaseConfig.getBoolean("players." + key + ".frozen"));
				banned.put(uuid, databaseConfig.getBoolean("players." + key + ".banned.status"));
				bannedLength.put(uuid, databaseConfig.getLong("players." + key + ".banned.length"));
				bannedReason.put(uuid, databaseConfig.getString("players." + key + ".banned.reason"));
				spy.put(uuid, databaseConfig.getBoolean("players." + key + ".canSpy"));
			}
		}
		catch (Exception ex)
		{
			Util.print(databaseFile + " is empty or null! Attempting to fix: " + ex.getMessage());
			
			UUID uuid = UUID.fromString("5ba03c6c-ad4c-4475-8ec9-8bc8a15a9ebe");
			
			name.put(uuid, "SirBlobman");
			balance.put(uuid, 0.0);
			afk.put(uuid, false);
			frozen.put(uuid, false);
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
	
	public static void setBalance(OfflinePlayer p, double amount)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		
		loadDatabase();
		balance.put(uuid, amount);
		saveDatabase();
	}
	
	public static void addToBalance(OfflinePlayer p, double amount)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		loadDatabase();
		if(amount < 0) amount = 0;
		
		double newBalance = balance.get(uuid) + amount;
		
		setBalance(p, newBalance);
	}
	
	public static void subtractFromBalance(OfflinePlayer p, double amount)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		loadDatabase();
		if(amount < 0) amount = 0;
		
		double newBalance = balance.get(uuid) - amount;
		
		setBalance(p, newBalance);
	}
	
	public static void resetBalance(OfflinePlayer p)
	{
		if(p == null) return;
		setBalance(p, 0);
	}
	
	public static boolean isBanned(OfflinePlayer p)
	{
		if(p == null) return false;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return false;
		
		loadDatabase();
		if(!banned.containsKey(uuid)) return false;
		boolean ban = banned.get(uuid);
		
		return ban;
	}
	
	public static String getBanReason(OfflinePlayer p)
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
		bannedLength.put(uuid, null);
		databaseConfig.set(uuid + ".banned.length", null);
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
	
	public static void freeze(Player p)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		
		loadDatabase();
		frozen.put(uuid, true);
		p.sendMessage(Util.blobcatraz + "You have been frozen!");
		saveDatabase();
	}
	
	public static void unFreeze(Player p)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		
		loadDatabase();
		frozen.put(uuid, false);
		p.sendMessage(Util.blobcatraz + "You have been UNfrozen!");
		saveDatabase();
	}
	
	public static boolean isFrozen(Player p)
	{
		if(p == null) return false;
		UUID uuid = p.getUniqueId();
		loadDatabase();
		
		if(!frozen.containsKey(uuid)) return false;
		
		return frozen.get(uuid);
	}
	
	public static List<String> getBalanceTopTen()
	{
		List<Entry<UUID, Double>> sorted = new ArrayList<Entry<UUID, Double>>(balance.entrySet());
		Collections.sort(sorted, Collections.reverseOrder(new Comparator<Entry<UUID, Double>>() 
		{
			@Override
			public int compare(Entry<UUID, Double> arg0, Entry<UUID, Double> arg1)
			{
				return arg0.getValue().compareTo(arg1.getValue());
			}
	    }));
		List<Entry<UUID, Double>> t;
		if(sorted.size() > 8) t = sorted.subList(0, 9);
		else t = sorted.subList(0, sorted.size());
		
		List<String> balTop = new ArrayList<String>();
		int i = 1;
		for(Entry<UUID, Double> e : t)
		{
			OfflinePlayer p = Bukkit.getOfflinePlayer(e.getKey());
			String name = p.getName();
			balTop.add(i + ". §6" + name + ": §e$" + e.getValue());
			i++;
		}
		
		return balTop;
	}
	
	public static boolean getCanSpy(Player p)
	{
		if(p == null) return false;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return false;
		loadDatabase();
		if(spy.containsKey(uuid)) return spy.get(uuid);
		return false;
	}
	
	public static void toggleCanSpy(Player p)
	{
		if(p == null) return;
		UUID uuid = p.getUniqueId();
		if(uuid == null) return;
		loadDatabase();
		if(!spy.containsKey(uuid)) 
		{
			spy.put(uuid, true);
			saveDatabase();
			return;
		}
		if(spy.containsKey(uuid))
		{
			if(spy.get(uuid)) {spy.put(uuid, false); saveDatabase(); return;}
			else {spy.put(uuid, true); saveDatabase(); return;}
		}
		saveDatabase();
	}
}