package com.SirBlobman.blobcatraz.compat.vault;

import java.text.NumberFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class BlobcatrazEconomy implements Economy
{
	private static final Server S = Bukkit.getServer();
	private static final ServicesManager SM = S.getServicesManager();
	
	private OfflinePlayer get(String name)
	{
		@SuppressWarnings("deprecation")
		OfflinePlayer op = Bukkit.getOfflinePlayer(name);
		return op;
	}
	
	@Override
	public boolean isEnabled()
	{
		RegisteredServiceProvider<Economy> rsp = SM.getRegistration(Economy.class);
		boolean b1 = rsp.equals(BlobcatrazEconomy.class);
		return b1;
	}
	
	public String getName() {return "Blobcatraz";}
	public boolean hasBankSupport() {return false;}
	public int fractionalDigits() {return -1;}
	
	public String format(double amount)
	{
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		String format = nf.format(amount);
		return format;
	}
	
	public String currencyNameSingular() {return "$";}
	public String currencyNamePlural() {return currencyNameSingular();}
	public boolean hasAccount(String name) {return hasAccount(get(name));}
	public boolean hasAccount(OfflinePlayer op) {return ConfigDatabase.hasAccount(op);}
	public boolean hasAccount(String name, String world) {return hasAccount(name);}
	public boolean hasAccount(OfflinePlayer op, String world) {return hasAccount(op);}
	
	public double getBalance(String name) {return getBalance(get(name));}
	public double getBalance(OfflinePlayer op) {return ConfigDatabase.balance(op);}
	public double getBalance(String name, String world) {return getBalance(name);}
	public double getBalance(OfflinePlayer op, String world) {return getBalance(op);}
	
	public boolean has(String name, double amount) {return has(get(name), amount);}
	public boolean has(String name, String world, double amount) {return has(name, amount);}
	public boolean has(OfflinePlayer op, String world, double amount) {return has(op, amount);}
	public boolean has(OfflinePlayer op, double amount)
	{
		double balance = getBalance(op);
		boolean b1 = (balance >= amount);
		return b1;
	}
	
	public EconomyResponse withdrawPlayer(String name, double amount) {return withdrawPlayer(get(name), amount);}
	public EconomyResponse withdrawPlayer(String name, String world, double amount) {return withdrawPlayer(name, amount);}
	public EconomyResponse withdrawPlayer(OfflinePlayer op, String world, double amount) {return withdrawPlayer(op, amount);}
	public EconomyResponse withdrawPlayer(OfflinePlayer op, double amount)
	{
		ConfigDatabase.withdraw(op, amount);
		double balance = getBalance(op);
		EconomyResponse er = new EconomyResponse(amount, balance, ResponseType.SUCCESS, null);
		return er;
	}
	
	public EconomyResponse depositPlayer(String name, double amount) {return depositPlayer(get(name), amount);}
	public EconomyResponse depositPlayer(String name, String world, double amount) {return depositPlayer(name, amount);}
	public EconomyResponse depositPlayer(OfflinePlayer op, String world, double amount) {return depositPlayer(op, amount);}
	public EconomyResponse depositPlayer(OfflinePlayer op, double amount)
	{
		ConfigDatabase.deposit(op, amount);
		double balance = getBalance(op);
		EconomyResponse er = new EconomyResponse(amount, balance, ResponseType.SUCCESS, null);
		return er;
	}

	public EconomyResponse createBank(String name, String op) {return null;}
	public EconomyResponse createBank(String name, OfflinePlayer op) {return null;}
	public EconomyResponse deleteBank(String name) {return null;}
	public EconomyResponse bankBalance(String name) {return null;}
	public EconomyResponse bankHas(String name, double amount) {return null;}
	public EconomyResponse bankWithdraw(String name, double amount) {return null;}
	public EconomyResponse bankDeposit(String name, double amount) {return null;}
	public EconomyResponse isBankOwner(String name, String name2) {return null;}
	public EconomyResponse isBankOwner(String name, OfflinePlayer op) {return null;}
	public EconomyResponse isBankMember(String name, String name2) {return null;}
	public EconomyResponse isBankMember(String name, OfflinePlayer op) {return null;}
	public List<String> getBanks() {return null;}
	
	public boolean createPlayerAccount(String name) {return createPlayerAccount(get(name));}
	public boolean createPlayerAccount(String name, String world) {return createPlayerAccount(name);}
	public boolean createPlayerAccount(OfflinePlayer op, String world) {return createPlayerAccount(op);}
	public boolean createPlayerAccount(OfflinePlayer op)
	{
		ConfigDatabase.load(op);
		return ConfigDatabase.hasAccount(op);
	}
}