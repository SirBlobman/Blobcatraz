package com.SirBlobman.blobcatraz.task;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.Comparator;

public class BalanceComparator implements Comparator<Entry<UUID, Double>>
{
	@Override
	public int compare(Entry<UUID, Double> e0, Entry<UUID, Double> e1)
	{
		return e0.getValue().compareTo(e1.getValue());
	}
}