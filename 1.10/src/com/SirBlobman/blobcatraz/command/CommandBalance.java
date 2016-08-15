package com.SirBlobman.blobcatraz.command;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SirBlobman.blobcatraz.Util;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

public class CommandBalance implements CommandExecutor,Listener
{
    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args)
    {
    	if(label.equalsIgnoreCase("balance") || label.equalsIgnoreCase("bal") || label.equalsIgnoreCase("money"))
    	{
    		if(args.length == 1 && cs.hasPermission("blobcatraz.economy.balance.others"))
    		{
    			OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
    			if(p == null)
    			{
    				cs.sendMessage(Util.blobcatraz + args[0] + " is not a Player");
    				return true;
    			}
    			
    			double amount = ConfigDatabase.getBalance(p);
    			String display = NumberFormat.getCurrencyInstance().format(amount);
    			
    			cs.sendMessage(Util.blobcatraz + "§2" + p.getName() + " §rhas §2" + display);
    			return true;
    		}
    		else
    		{
    			if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
    			Player p = (Player) cs;
    			
    			double amount = ConfigDatabase.getBalance(p);
    			String display = NumberFormat.getCurrencyInstance().format(amount);
    			p.sendMessage(Util.blobcatraz + "You have §2" + display);
    			return true;
    		}
    	}
    	if(label.equalsIgnoreCase("baltop") || label.equalsIgnoreCase("balancetop"))
    	{
    		cs.sendMessage(Util.blobcatraz + "Top Ten Players: ");
    		for(String t : ConfigDatabase.getBalanceTopTen())
    		{
    			cs.sendMessage(t);
    		}
    		return true;
    	}
    	if(label.equalsIgnoreCase("withdraw"))
    	{
    		if(!(cs instanceof Player)) {cs.sendMessage(Util.commandExecutorNonPlayer); return true;}
			Player p = (Player) cs;
    		if(args.length == 0) {p.sendMessage(Util.notEnoughArguments); return false;}
    		double amount = 0.0D;
    		try{amount = Double.parseDouble(args[0]);} catch(Exception ex) {p.sendMessage(Util.blobcatraz + "§5" + args[0] + " §ris not a Number");}
    		if(amount != 0.0D && ConfigDatabase.getBalance(p) >= amount)
    		{
    			ItemStack bankNote = new ItemStack(Material.DOUBLE_PLANT);
    			ItemMeta meta = bankNote.getItemMeta();
    			meta.setDisplayName("§fBlobcatraz Bank Note");
    			meta.setLore(Arrays.asList("$" + amount));
    			bankNote.setItemMeta(meta);
    			HashMap<Integer, ItemStack> i = p.getInventory().addItem(bankNote);
    			if(!i.keySet().isEmpty()) {p.sendMessage(Util.blobcatraz + "Make some space in your inventory"); return true;}
    			ConfigDatabase.subtractMoney(p, amount);
    		}
    		else
    		{
    			p.sendMessage(Util.blobcatraz + "You don't have enough money!");
    			return true;
    		}
    	}
        return false;
    }
    
    
    @EventHandler
    public void onDeposit(PlayerInteractEvent e)
    {
    	try
    	{
    		Player p = e.getPlayer();
    		ItemStack is = e.getItem();
    		ItemMeta meta = is.getItemMeta();
    		if(meta.getDisplayName().equalsIgnoreCase("§fBlobcatraz Bank Note") && is.getType() == Material.DOUBLE_PLANT)
    		{
    			double amount = Double.parseDouble(meta.getLore().get(1));
    			is.setType(Material.AIR);
    			ConfigDatabase.addMoney(p, amount);
    			p.sendMessage(Util.blobcatraz + "You turned in a ban note for §2$" + amount);
    		}
    	} catch(Exception ex) {}
    }
}