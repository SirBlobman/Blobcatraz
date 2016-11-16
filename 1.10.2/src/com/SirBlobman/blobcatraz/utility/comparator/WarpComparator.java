package com.SirBlobman.blobcatraz.utility.comparator;

import java.util.Comparator;

import com.SirBlobman.blobcatraz.config.Warp;

public class WarpComparator implements Comparator<Warp>
{
	@Override
	public int compare(Warp w1, Warp w2)
	{
		String n1 = w1.getName();
		String n2 = w2.getName();
		int ret = n1.compareToIgnoreCase(n2);
		return ret;
	}
}