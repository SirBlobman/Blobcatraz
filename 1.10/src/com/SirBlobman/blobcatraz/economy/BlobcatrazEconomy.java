package com.SirBlobman.blobcatraz.economy;

import java.text.NumberFormat;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class BlobcatrazEconomy implements Economy
{
	@Override
	public boolean isEnabled()
	{
		if(Bukkit.getServicesManager().getRegistration(Economy.class).equals(BlobcatrazEconomy.class)) return true;
		
		return false;
	}

	@Override
	public String getName()
	{
		return "Blobcatraz";
	}

	@Override
	public boolean hasBankSupport()
	{
		return false;
	}

	@Override
	public int fractionalDigits()
	{
		return -1;
	}

	@Override
	public String format(double amount)
	{
		return NumberFormat.getCurrencyInstance().format(amount);
	}

	@Override
	public String currencyNamePlural()
	{
		return "$";
	}

	@Override
	public String currencyNameSingular()
	{
		return "$";
	}

	@Override
	public boolean hasAccount(String playerName)
	{
		return ConfigDatabase.balance.containsKey(Bukkit.getPlayer(playerName).getUniqueId());
	}

	@Override
	public boolean hasAccount(OfflinePlayer player)
	{
		return ConfigDatabase.balance.containsKey(player.getUniqueId());
	}

	@Override
	public boolean hasAccount(String playerName, String worldName)
	{
		return ConfigDatabase.balance.containsKey(Bukkit.getPlayer(playerName).getUniqueId());
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName)
	{
		return ConfigDatabase.balance.containsKey(player.getUniqueId());
	}

	@Override
	public double getBalance(String playerName)
	{
		return ConfigDatabase.getBalance(Bukkit.getPlayer(playerName));
	}

	@Override
	public double getBalance(OfflinePlayer player)
	{
		return ConfigDatabase.getBalance(player);
	}

	@Override
	public double getBalance(String playerName, String world)
	{
		return ConfigDatabase.getBalance(Bukkit.getPlayer(playerName));
	}

	@Override
	public double getBalance(OfflinePlayer player, String world)
	{
		return ConfigDatabase.getBalance(player);
	}

	@Override
	public boolean has(String playerName, double amount)
	{
		double balance = getBalance(playerName);
		if(balance >= amount) return true;
		return false;
	}

	@Override
	public boolean has(OfflinePlayer player, double amount)
	{
		double balance = getBalance(player);
		if(balance >= amount) return true;
		return false;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount)
	{
		return has(playerName, amount);
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount)
	{
		return has(player, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount)
	{
		Player p = Bukkit.getPlayer(playerName);
		ConfigDatabase.subtractFromBalance(p, amount);
		return new EconomyResponse(amount, ConfigDatabase.getBalance(p), ResponseType.SUCCESS, playerName);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount)
	{
		return withdrawPlayer(player.getName(), amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount)
	{
		return withdrawPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount)
	{
		return withdrawPlayer(player.getName(), amount);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount)
	{
		Player p = Bukkit.getPlayer(playerName);
		ConfigDatabase.addToBalance(p, amount);
		return new EconomyResponse(amount, ConfigDatabase.getBalance(p), ResponseType.SUCCESS, playerName);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount)
	{
		return depositPlayer(player.getName(), amount);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount)
	{
		return depositPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount)
	{
		return depositPlayer(player.getName(), amount);
	}

	@Override
	public EconomyResponse createBank(String name, String player)
	{
		return null;
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player)
	{
		return null;
	}

	@Override
	public EconomyResponse deleteBank(String name)
	{
		return null;
	}
	
	@Override
	public EconomyResponse bankBalance(String name) 
	{
		return null;
	}

	@Override
	public EconomyResponse bankHas(String name, double amount)
	{
		return null;
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount)
	{
		return null;
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) 
	{
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) 
	{
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) 
	{
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) 
	{
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) 
	{
		return null;
	}

	@Override
	public List<String> getBanks()
	{
		return null;
	}

	@Override
	public boolean createPlayerAccount(String playerName)
	{
		UUID uuid = Bukkit.getPlayer(playerName).getUniqueId();
		ConfigDatabase.balance.put(uuid, 0.0);
		return ConfigDatabase.balance.containsKey(uuid);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player)
	{
		return createPlayerAccount(player.getName());
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) 
	{
		return createPlayerAccount(playerName);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName)
	{
		return createPlayerAccount(player.getName());
	}
}