package com.SirBlobman.blobcatraz.command;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
    		if(amount > 0.0D && ConfigDatabase.getBalance(p) >= amount)
    		{
    			ItemStack bankNote = new ItemStack(Material.DOUBLE_PLANT);
    			ItemMeta meta = bankNote.getItemMeta();
    			meta.setDisplayName("§fBlobcatraz Bank Note");
    			meta.setLore(Arrays.asList("§2" + NumberFormat.getCurrencyInstance().format(amount)));
    			bankNote.setItemMeta(meta);
    			HashMap<Integer, ItemStack> i = p.getInventory().addItem(bankNote);
    			if(!i.keySet().isEmpty()) {p.sendMessage(Util.blobcatraz + "Make some space in your inventory"); return true;}
    			ConfigDatabase.subtractMoney(p, amount);
    			return true;
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
    	Player p = e.getPlayer();
    	Action a = e.getAction();
    	if(a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK))
    	{
    		PlayerInventory pi = p.getInventory();
    		ItemStack is = pi.getItemInMainHand();
    		if(is == null) return;
    		Material mat = is.getType();
    		if(mat != Material.DOUBLE_PLANT);
    		ItemMeta meta = is.getItemMeta();
    		if(meta == null) return;
    		String displayName = meta.getDisplayName();
    		if(displayName == null) return;
    		if(!displayName.equalsIgnoreCase("§fBlobcatraz Bank Note")) return;
    		List<String> lore = meta.getLore();
    		if(lore == null) return;
    		String money = lore.get(0);
    		if(!money.contains("$")) return;
    		double amount = 0.0D;
    		try{amount = Double.parseDouble(Util.uncolor(money.replace("$", "").replace(",", "")));} catch(Exception ex){p.sendMessage(Util.blobcatraz + "Invalid Bank Note"); return;}
    		int item = is.getAmount();
    		if(item == 1) pi.setItemInMainHand(new ItemStack(Material.AIR));
    		else{int newAmount = item - 1; is.setAmount(newAmount);}
    		p.sendMessage(Util.blobcatraz + "You deposited §2" + NumberFormat.getCurrencyInstance().format(amount));
    		e.setCancelled(true);
    	}
    }
}