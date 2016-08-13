package com.SirBlobman.blobcatraz.economy;

import java.text.NumberFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

@SuppressWarnings("deprecation")
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
		return hasAccount(getPlayer(playerName));
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) 
	{
		return ConfigDatabase.doesPlayerHaveAccount(player);
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) 
	{
		return hasAccount(playerName);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) 
	{
		return hasAccount(player);
	}

	@Override
	public double getBalance(String playerName)
	{
		return getBalance(getPlayer(playerName));
	}

	@Override
	public double getBalance(OfflinePlayer player)
	{
		return ConfigDatabase.getBalance(player);
	}

	@Override
	public double getBalance(String playerName, String world)
	{
		return getBalance(playerName);
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) 
	{
		return getBalance(player);
	}

	@Override
	public boolean has(String playerName, double amount)
	{
		return has(getPlayer(playerName), amount);
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) 
	{
		return ConfigDatabase.getBalance(player) >= amount;
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
		return withdrawPlayer(getPlayer(playerName), amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount)
	{
		double balance = ConfigDatabase.subtractMoney(player, amount);
		return new EconomyResponse(amount, balance, ResponseType.SUCCESS, null);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount)
	{
		return withdrawPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount)
	{
		return withdrawPlayer(player, amount);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount)
	{
		return depositPlayer(getPlayer(playerName), amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount)
	{
		double balance = ConfigDatabase.addMoney(player, amount);
		return new EconomyResponse(amount, balance, ResponseType.SUCCESS, null);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount)
	{
		return depositPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount)
	{
		return depositPlayer(player, amount);
	}

	@Override
	public EconomyResponse createBank(String name, String player) {return null;}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {return null;}

	@Override
	public EconomyResponse deleteBank(String name) {return null;}

	@Override
	public EconomyResponse bankBalance(String name) {return null;}

	@Override
	public EconomyResponse bankHas(String name, double amount) {return null;}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {return null;}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {return null;}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {return null;}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {return null;}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {return null;}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {return null;}

	@Override
	public List<String> getBanks() {return null;}

	@Override
	public boolean createPlayerAccount(String playerName) 
	{
		return createPlayerAccount(getPlayer(playerName));
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player)
	{
		ConfigDatabase.load(player);
		return ConfigDatabase.doesPlayerHaveAccount(player);
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName)
	{
		return createPlayerAccount(playerName);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName)
	{
		return createPlayerAccount(player);
	}
	
	public OfflinePlayer getPlayer(String name)
	{
		return Bukkit.getOfflinePlayer(name);
	}
}