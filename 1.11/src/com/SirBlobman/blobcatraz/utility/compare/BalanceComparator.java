package com.SirBlobman.blobcatraz.utility.compare;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.UUID;

public class BalanceComparator implements Comparator<Entry<UUID, Double>>
{
	@Override
	public int compare(Entry<UUID, Double> e1, Entry<UUID, Double> e2)
	{
		Double d1 = e1.getValue();
		Double d2 = e2.getValue();
		return d1.compareTo(d2);
	}
}