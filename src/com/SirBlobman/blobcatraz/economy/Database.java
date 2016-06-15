package com.SirBlobman.blobcatraz.economy;

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

public class Database 
{
	static HashMap<UUID, String> names = new HashMap<UUID, String>();
	static HashMap<UUID, Double> balance = new HashMap<UUID, Double>();
	static HashMap<UUID, Boolean> banned = new HashMap<UUID, Boolean>();
	static HashMap<UUID, String> bannedReason = new HashMap<UUID, String>();
	
	private static final File databaseFile = new File(Blobcatraz.instance.getDataFolder(), "database.yml");
	public static FileConfiguration databaseConfig = YamlConfiguration.loadConfiguration(databaseFile);
	
	public static void saveDatabase()
	{
		if(!databaseFile.exists())
		{
			try
			{
				databaseFile.createNewFile();
			}
			catch (Exception ex)
			{
				Util.print("Could not create database.yml");
				ex.printStackTrace();
				return;
			}
		}
		
		for(Entry<UUID, String> e : names.entrySet())
		{
			databaseConfig.set("players." + e.getKey() + ".lastName", e.getValue());
		}
		for(Entry<UUID, Double> e : balance.entrySet())
		{
			databaseConfig.set("players." + e.getKey() + ".balance", e.getValue());
		}
		for(Entry<UUID, String> e : bannedReason.entrySet())
		{
			databaseConfig.set("players." + e.getKey() + ".bannedReason", e.getValue());
		}

		for(Entry<UUID, Boolean> e : banned.entrySet())
		{
			databaseConfig.set("players." + e.getKey() + ".banned", e.getValue());
		}
		
		try 
		{
			databaseConfig.save(databaseFile);
		} 
		catch (Exception ex) 
		{
			Util.print("Failed to save database.yml");
			ex.printStackTrace();
			return;
		}
	}
	
	public static void loadDatabase()
	{
		try
		{
			databaseConfig.load(databaseFile);
		}
		catch (Exception ex)
		{
			Util.print("Failed to load database.yml, attempting to create");
			saveDatabase();
		}
		
		try
		{
			for(String key : databaseConfig.getConfigurationSection("players").getKeys(false))
			{
				UUID uuid = UUID.fromString(key);
				
				names.put(uuid, databaseConfig.getString("players." + key + ".lastName"));
				balance.put(uuid, databaseConfig.getDouble("players." + key + ".balance"));
				banned.put(uuid, databaseConfig.getBoolean("players." + key + ".banned"));
				bannedReason.put(uuid, databaseConfig.getString("players." + key + ".bannedReason"));
			}
		}
		catch (Exception ex)
		{
			Util.print("database.yml is empty or null");
			ex.printStackTrace();
		}
	}

	public static void writeDefaults(UUID uuid)
	{
		names.put(uuid, Bukkit.getPlayer(uuid).getName());
		banned.put(uuid, false);
		bannedReason.put(uuid, null);
		balance.put(uuid, 0.0);
		saveDatabase();
	}
	
	public static void clearBalances()
	{
		for(String key : databaseConfig.getConfigurationSection("players").getKeys(false))
		{
			databaseConfig.set("players." + key + ".balance", null);
		}
		balance.clear();
		saveDatabase();
		loadDatabase();
	}
	
	public static double getPlayerBalance(UUID uuid)
	{
		loadDatabase();
		if(uuid == null || !balance.containsKey(uuid))
		{
			return 0;
		}
		
		double bal = balance.get(uuid);
		
		return bal;
	}
	
	
	public static void setPlayerBalance(UUID uuid, double amount)
	{
		loadDatabase();
		if(uuid == null)
		{
			return;
		}
		
		balance.put(uuid, amount);
		saveDatabase();
	}
	
	public static void addToPlayerBalance(UUID uuid, double amount)
	{
		loadDatabase();
		if(uuid == null) return;
		double newbalance = balance.get(uuid) + amount;
		setPlayerBalance(uuid, newbalance);
	}
	
	public static void subtractFromPlayerBalance(UUID uuid, double amount)
	{
		loadDatabase();
		if(uuid == null) return;
		double newbalance = balance.get(uuid) - amount;
		if(newbalance < 0) newbalance = 0;
		setPlayerBalance(uuid, newbalance);
	}
	
	public static void resetPlayerBalance(UUID uuid)
	{
		setPlayerBalance(uuid, 0);
	}

	public static Boolean isPlayerBanned(UUID uuid)
	{
		if(uuid == null) return null;
		
		loadDatabase();
		boolean ban = banned.get(uuid);
		
		return ban;
	}
	
	public static String getBanReason(UUID uuid)
	{
		if(uuid == null) return null;
		loadDatabase();
		String r = bannedReason.get(uuid);
		if(r != null) return r;
		
		return null;
	}
	
	public static void banPlayer(OfflinePlayer p, String reason)
	{
		if(p.isOnline())
		{
			Player onlinep = (Player) p;
			onlinep.kickPlayer(Util.blobcatraz + "§4You have been banned:\n§r" + reason);
		}
		
		
		bannedReason.put(p.getUniqueId(), reason);
		banned.put(p.getUniqueId(), true);
		saveDatabase();
	}
	
	public static void unbanPlayer(OfflinePlayer p)
	{
		banned.put(p.getUniqueId(), false);
		bannedReason.put(p.getUniqueId(), null);
		saveDatabase();
	}
}
