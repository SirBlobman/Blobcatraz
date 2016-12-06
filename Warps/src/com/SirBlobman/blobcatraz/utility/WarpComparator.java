package com.SirBlobman.blobcatraz.utility;

import java.util.Comparator;

import com.SirBlobman.blobcatraz.config.Warp;

public class WarpComparator implements Comparator<Warp>
{
	@Override
	public int compare(Warp w1, Warp w2)
	{
		String n1 = w1.name().toLowerCase();
		String n2 = w2.name().toLowerCase();
		int i = n1.compareTo(n2);
		return i;
	}
}