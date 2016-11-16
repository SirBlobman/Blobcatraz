package com.SirBlobman.blobcatraz.utility.comparator;

import java.util.Comparator;
import java.util.UUID;
import java.util.Map.Entry;

public class BalanceComparator implements Comparator<Entry<UUID, Double>>
{
	@Override
	public int compare(Entry<UUID, Double> o1, Entry<UUID, Double> o2)
	{
		Double d1 = o1.getValue();
		Double d2 = o2.getValue();
		return d1.compareTo(d2);
	}
}