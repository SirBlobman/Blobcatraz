package com.SirBlobman.blobcatraz.listener;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class ListenWithdraw implements Listener
{
	@EventHandler
	public void with(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		Action a = e.getAction();
		Action r1 = Action.RIGHT_CLICK_AIR;
		Action r2 = Action.RIGHT_CLICK_BLOCK;
		if(a == r1 || a == r2)
		{
			ItemStack held = e.getItem();
			if(held == null) return;
			String name = ItemUtil.name(held);
			String check = Util.option("item.bank note.name");
			if(name.equals(check))
			{
				List<String> lore = ItemUtil.lore(held);
				if(lore == null) return;
				try
				{
					String money = lore.get(0);
					if(money.contains("$"))
					{
						money = Util.uncolor(money);
						money = money.replace("$", "");
						money = money.replace(",", "");
						double cash = Double.parseDouble(money);
						int amount = held.getAmount();
						int set = (amount - 1);
						held.setAmount(set);
						
						ConfigDatabase.deposit(p, cash);
						String deposit = Util.monetize(cash);
						p.sendMessage(Util.prefix + Util.option("economy.deposit", deposit));
						e.setCancelled(true);
					}
				} catch(Exception ex)
				{
					String msg = Util.prefix + Util.option("error.invalid bank note");
					p.sendMessage(msg);
					return;
				}
			}
		}
	}
}